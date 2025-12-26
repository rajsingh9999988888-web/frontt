<?php
/**
 * Simple WhatsApp Auto-Reply System
 * Alternative approach: Manual trigger or scheduled task
 * 
 * This can be called manually or via cron job to check for new messages
 * and send automatic replies
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

/**
 * Process pending messages and send auto-replies
 * Call this via: GET /api/whatsapp-auto-reply-simple.php?process=1
 */
function processPendingMessages() {
    try {
        $database = new Database();
        $db = $database->getConnection();
        
        // Get unprocessed messages (last 24 hours, not replied)
        $sql = "
            SELECT * FROM whatsapp_messages 
            WHERE replied = FALSE 
            AND received_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
            ORDER BY received_at ASC
            LIMIT 10
        ";
        
        $stmt = $db->prepare($sql);
        $stmt->execute();
        $messages = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        $processed = 0;
        
        foreach ($messages as $message) {
            $phoneNumber = $message['phone_number'];
            $city = $message['city'];
            $messageText = $message['message_text'];
            
            // Generate reply message
            if ($city) {
                $replyMessage = "Welcome! {$city} call girl service available. How can I help you?";
            } else {
                $replyMessage = "Welcome! Call girl service available in all cities. Please mention your city for better service.";
            }
            
            // Send reply (you'll need to integrate with your WhatsApp sending method)
            $replySent = sendWhatsAppMessage($phoneNumber, $replyMessage);
            
            if ($replySent) {
                // Mark as replied
                $updateStmt = $db->prepare("UPDATE whatsapp_messages SET replied = TRUE WHERE id = ?");
                $updateStmt->execute([$message['id']]);
                $processed++;
            }
        }
        
        return [
            'success' => true,
            'processed' => $processed,
            'total' => count($messages)
        ];
    } catch (Exception $e) {
        return [
            'success' => false,
            'error' => $e->getMessage()
        ];
    }
}

/**
 * Send WhatsApp message (implement based on your WhatsApp service)
 * Options: Twilio, MessageBird, WhatsApp Business API, etc.
 */
function sendWhatsAppMessage($to, $message) {
    // TODO: Implement your WhatsApp sending method here
    // Example with WhatsApp Business API:
    
    $WHATSAPP_API_URL = getenv('WHATSAPP_API_URL') ?: 'https://graph.facebook.com/v18.0';
    $WHATSAPP_PHONE_NUMBER_ID = getenv('WHATSAPP_PHONE_NUMBER_ID') ?: '';
    $WHATSAPP_ACCESS_TOKEN = getenv('WHATSAPP_ACCESS_TOKEN') ?: '';
    
    if (!$WHATSAPP_PHONE_NUMBER_ID || !$WHATSAPP_ACCESS_TOKEN) {
        error_log("WhatsApp API credentials not configured");
        return false;
    }
    
    $messageData = [
        'messaging_product' => 'whatsapp',
        'to' => $to,
        'type' => 'text',
        'text' => ['body' => $message]
    ];
    
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
    
    return $httpCode === 200;
}

// Handle request
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['process'])) {
    $result = processPendingMessages();
    echo json_encode($result, JSON_PRETTY_PRINT);
} else {
    echo json_encode([
        'message' => 'WhatsApp Auto-Reply System',
        'usage' => 'Call with ?process=1 to process pending messages',
        'note' => 'Configure WhatsApp Business API credentials in environment variables'
    ], JSON_PRETTY_PRINT);
}

