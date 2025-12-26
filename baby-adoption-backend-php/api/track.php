<?php
/**
 * Tracking Controller - PHP
 * Handles: /api/track/*
 * Tracks user actions like WhatsApp clicks with city/district information
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'] ?? '';

// Remove query string first
$path = strtok($path, '?');

// Remove /api prefix if present
$path = preg_replace('#^/api#', '', $path);
$pathParts = explode('/', trim($path, '/'));

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // POST /api/track/whatsapp-click
    if ($method === 'POST' && isset($pathParts[0]) && $pathParts[0] === 'track' && isset($pathParts[1]) && $pathParts[1] === 'whatsapp-click') {
        trackWhatsAppClick($db);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found']);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

/**
 * Track WhatsApp click with city/district information
 * Stores: post_id, city, district, state, user_ip, timestamp
 */
function trackWhatsAppClick($db) {
    // Get JSON data from request body
    $input = json_decode(file_get_contents('php://input'), true);
    
    if (!$input) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid JSON data']);
        return;
    }
    
    $postId = isset($input['postId']) ? intval($input['postId']) : null;
    $city = isset($input['city']) ? trim($input['city']) : null;
    $district = isset($input['district']) ? trim($input['district']) : null;
    $state = isset($input['state']) ? trim($input['state']) : null;
    $whatsappNumber = isset($input['whatsappNumber']) ? trim($input['whatsappNumber']) : null;
    $pageUrl = isset($input['pageUrl']) ? trim($input['pageUrl']) : null;
    
    // Get user IP address
    $userIp = $_SERVER['REMOTE_ADDR'] ?? null;
    if (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) {
        $userIp = $_SERVER['HTTP_X_FORWARDED_FOR'];
    }
    
    // Get user agent
    $userAgent = $_SERVER['HTTP_USER_AGENT'] ?? null;
    
    try {
        // Create tracking table if it doesn't exist
        $createTableSql = "
            CREATE TABLE IF NOT EXISTS whatsapp_clicks (
                id INT AUTO_INCREMENT PRIMARY KEY,
                post_id INT,
                city VARCHAR(100),
                district VARCHAR(100),
                state VARCHAR(100),
                whatsapp_number VARCHAR(20),
                page_url VARCHAR(500),
                user_ip VARCHAR(45),
                user_agent TEXT,
                clicked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                INDEX idx_post_id (post_id),
                INDEX idx_city (city),
                INDEX idx_district (district),
                INDEX idx_state (state),
                INDEX idx_clicked_at (clicked_at)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ";
        $db->exec($createTableSql);
        
        // Insert tracking data
        $stmt = $db->prepare("
            INSERT INTO whatsapp_clicks 
            (post_id, city, district, state, whatsapp_number, page_url, user_ip, user_agent) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ");
        
        $stmt->execute([
            $postId,
            $city,
            $district,
            $state,
            $whatsappNumber,
            $pageUrl,
            $userIp,
            $userAgent
        ]);
        
        http_response_code(200);
        echo json_encode([
            'success' => true,
            'message' => 'WhatsApp click tracked successfully',
            'data' => [
                'postId' => $postId,
                'city' => $city,
                'district' => $district,
                'state' => $state
            ]
        ]);
    } catch (PDOException $e) {
        http_response_code(500);
        error_log("WhatsApp tracking error: " . $e->getMessage());
        echo json_encode(['error' => 'Failed to track click: ' . $e->getMessage()]);
    }
}

