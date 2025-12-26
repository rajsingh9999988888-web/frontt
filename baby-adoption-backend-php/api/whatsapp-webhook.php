<?php
/**
 * WhatsApp Webhook Handler
 * Receives WhatsApp messages and sends automatic replies based on city
 * 
 * Setup: Configure this URL in WhatsApp Business API webhook settings
 * Access: POST /api/whatsapp-webhook.php
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

// WhatsApp Business API credentials (configure these)
$WHATSAPP_API_URL = getenv('WHATSAPP_API_URL') ?: 'https://graph.facebook.com/v18.0';
$WHATSAPP_PHONE_NUMBER_ID = getenv('WHATSAPP_PHONE_NUMBER_ID') ?: '';
$WHATSAPP_ACCESS_TOKEN = getenv('WHATSAPP_ACCESS_TOKEN') ?: '';

$method = $_SERVER['REQUEST_METHOD'];

// Handle webhook verification (GET request from WhatsApp)
if ($method === 'GET') {
    $mode = $_GET['hub_mode'] ?? '';
    $token = $_GET['hub_verify_token'] ?? '';
    $challenge = $_GET['hub_challenge'] ?? '';
    
    // Verify token (set this in WhatsApp Business API settings)
    $verifyToken = getenv('WHATSAPP_VERIFY_TOKEN') ?: 'your_verify_token_here';
    
    if ($mode === 'subscribe' && $token === $verifyToken) {
        http_response_code(200);
        echo $challenge;
        exit;
    } else {
        http_response_code(403);
        echo json_encode(['error' => 'Verification failed']);
        exit;
    }
}

// Handle incoming messages (POST request from WhatsApp)
if ($method === 'POST') {
    $input = json_decode(file_get_contents('php://input'), true);
    
    if (!$input) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid JSON']);
        exit;
    }
    
    try {
        // Process webhook data
        if (isset($input['entry'][0]['changes'][0]['value']['messages'][0])) {
            $message = $input['entry'][0]['changes'][0]['value']['messages'][0];
            $from = $message['from'] ?? '';
            $messageText = $message['text']['body'] ?? '';
            $messageId = $message['id'] ?? '';
            
            // Get user's city from phone number or message
            $userCity = detectCityFromPhoneNumber($from);
            
            // If city not found in phone, try to extract from message
            if (!$userCity && $messageText) {
                $userCity = extractCityFromMessage($messageText);
            }
            
            // Store the incoming message
            storeIncomingMessage($from, $messageText, $userCity);
            
            // Send automatic welcome reply
            $replySent = sendAutomaticReply($from, $userCity, $messageText);
            
            echo json_encode([
                'success' => true,
                'message' => 'Webhook processed',
                'from' => $from,
                'city' => $userCity,
                'reply_sent' => $replySent
            ]);
        } else {
            // Webhook verification or status update
            echo json_encode(['success' => true, 'message' => 'Webhook received']);
        }
    } catch (Exception $e) {
        error_log("WhatsApp webhook error: " . $e->getMessage());
        http_response_code(500);
        echo json_encode(['error' => $e->getMessage()]);
    }
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Method not allowed']);
}

/**
 * Detect city from phone number
 * Uses phone number prefix or database lookup
 */
function detectCityFromPhoneNumber($phoneNumber) {
    try {
        $database = new Database();
        $db = $database->getConnection();
        
        // Remove + and spaces from phone number
        $cleanPhone = preg_replace('/[^0-9]/', '', $phoneNumber);
        
        // Check if we have this number in our tracking database
        $stmt = $db->prepare("
            SELECT city 
            FROM whatsapp_clicks 
            WHERE whatsapp_number LIKE ? 
            ORDER BY clicked_at DESC 
            LIMIT 1
        ");
        $stmt->execute(['%' . substr($cleanPhone, -10) . '%']);
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($result && $result['city']) {
            return $result['city'];
        }
        
        // Check from baby_posts if user has posted
        $stmt = $db->prepare("
            SELECT city 
            FROM baby_posts 
            WHERE phone LIKE ? OR whatsapp LIKE ?
            ORDER BY created_at DESC 
            LIMIT 1
        ");
        $stmt->execute(['%' . substr($cleanPhone, -10) . '%', '%' . substr($cleanPhone, -10) . '%']);
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($result && $result['city']) {
            return $result['city'];
        }
        
        return null;
    } catch (Exception $e) {
        error_log("City detection error: " . $e->getMessage());
        return null;
    }
}

/**
 * Extract city name from message text
 */
function extractCityFromMessage($messageText) {
    $commonCities = [
        'Mumbai', 'Delhi', 'Bangalore', 'Hyderabad', 'Chennai', 'Kolkata', 'Pune',
        'Jaipur', 'Bhopal', 'Ranchi', 'Indore', 'Ahmedabad', 'Surat', 'Lucknow',
        'Kanpur', 'Nagpur', 'Patna', 'Vadodara', 'Ghaziabad', 'Ludhiana'
    ];
    
    $messageLower = strtolower($messageText);
    
    foreach ($commonCities as $city) {
        if (stripos($messageText, $city) !== false || stripos($messageText, strtolower($city)) !== false) {
            return $city;
        }
    }
    
    return null;
}

/**
 * Store incoming WhatsApp message
 */
function storeIncomingMessage($from, $messageText, $city) {
    try {
        $database = new Database();
        $db = $database->getConnection();
        
        // Create table if not exists
        $createTableSql = "
            CREATE TABLE IF NOT EXISTS whatsapp_messages (
                id INT AUTO_INCREMENT PRIMARY KEY,
                phone_number VARCHAR(20),
                message_text TEXT,
                city VARCHAR(100),
                received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                replied BOOLEAN DEFAULT FALSE,
                INDEX idx_phone (phone_number),
                INDEX idx_city (city),
                INDEX idx_received_at (received_at)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ";
        $db->exec($createTableSql);
        
        $stmt = $db->prepare("
            INSERT INTO whatsapp_messages (phone_number, message_text, city) 
            VALUES (?, ?, ?)
        ");
        $stmt->execute([$from, $messageText, $city]);
        
        return true;
    } catch (Exception $e) {
        error_log("Store message error: " . $e->getMessage());
        return false;
    }
}

/**
 * Send automatic welcome reply based on city
 */
function sendAutomaticReply($to, $city, $originalMessage) {
    global $WHATSAPP_API_URL, $WHATSAPP_PHONE_NUMBER_ID, $WHATSAPP_ACCESS_TOKEN;
    
    if (!$WHATSAPP_PHONE_NUMBER_ID || !$WHATSAPP_ACCESS_TOKEN) {
        error_log("WhatsApp API credentials not configured");
        return false;
    }
    
    // Generate welcome message based on city
    if ($city) {
        $welcomeMessage = "Welcome! {$city} call girl service available. How can I help you?";
    } else {
        $welcomeMessage = "Welcome! Call girl service available in all cities. Please mention your city for better service.";
    }
    
    // Prepare message payload for WhatsApp Business API
    $messageData = [
        'messaging_product' => 'whatsapp',
        'to' => $to,
        'type' => 'text',
        'text' => [
            'body' => $welcomeMessage
        ]
    ];
    
    // Send message via WhatsApp Business API
    $url = "{$WHATSAPP_API_URL}/{$WHATSAPP_PHONE_NUMBER_ID}/messages";
    
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($messageData));
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        'Content-Type: application/json',
        "Authorization: Bearer {$WHATSAPP_ACCESS_TOKEN}"
    ]);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    
    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);
    
    if ($httpCode === 200) {
        // Mark message as replied
        try {
            $database = new Database();
            $db = $database->getConnection();
            $stmt = $db->prepare("
                UPDATE whatsapp_messages 
                SET replied = TRUE 
                WHERE phone_number = ? 
                ORDER BY received_at DESC 
                LIMIT 1
            ");
            $stmt->execute([$to]);
        } catch (Exception $e) {
            error_log("Update replied status error: " . $e->getMessage());
        }
        
        return true;
    } else {
        error_log("WhatsApp API error: HTTP {$httpCode} - {$response}");
        return false;
    }
}

