<?php
/**
 * Create WhatsApp Tracking Table
 * Run this once to create the table manually
 * 
 * Access: /api/create-whatsapp-table.php
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // Create tracking table
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
    
    // Verify table was created
    $checkTable = $db->query("SHOW TABLES LIKE 'whatsapp_clicks'");
    $tableExists = $checkTable->rowCount() > 0;
    
    if ($tableExists) {
        // Get table structure
        $structure = $db->query("DESCRIBE whatsapp_clicks")->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode([
            'success' => true,
            'message' => 'Table created successfully',
            'table' => 'whatsapp_clicks',
            'structure' => $structure
        ], JSON_PRETTY_PRINT);
    } else {
        echo json_encode([
            'success' => false,
            'error' => 'Table creation failed'
        ], JSON_PRETTY_PRINT);
    }
    
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'success' => false,
        'error' => $e->getMessage()
    ], JSON_PRETTY_PRINT);
}

