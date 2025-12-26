<?php
/**
 * View WhatsApp Tracking Data
 * Admin tool to view which cities/districts users are clicking WhatsApp from
 * 
 * Access: /api/view-whatsapp-tracking.php
 * Or add authentication if needed
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // Create table if it doesn't exist (same as track.php)
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
    
    // Get query parameters
    $city = $_GET['city'] ?? null;
    $district = $_GET['district'] ?? null;
    $state = $_GET['state'] ?? null;
    $limit = isset($_GET['limit']) ? intval($_GET['limit']) : 100;
    $offset = isset($_GET['offset']) ? intval($_GET['offset']) : 0;
    
    // Build query
    $sql = "SELECT 
                id,
                post_id,
                city,
                district,
                state,
                whatsapp_number,
                page_url,
                user_ip,
                clicked_at
            FROM whatsapp_clicks
            WHERE 1=1";
    
    $params = [];
    
    if ($city) {
        $sql .= " AND city = ?";
        $params[] = $city;
    }
    
    if ($district) {
        $sql .= " AND district = ?";
        $params[] = $district;
    }
    
    if ($state) {
        $sql .= " AND state = ?";
        $params[] = $state;
    }
    
    $sql .= " ORDER BY clicked_at DESC LIMIT ? OFFSET ?";
    $params[] = $limit;
    $params[] = $offset;
    
    $stmt = $db->prepare($sql);
    $stmt->execute($params);
    $clicks = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Get summary statistics
    $summarySql = "SELECT 
                        city,
                        district,
                        state,
                        COUNT(*) as click_count
                   FROM whatsapp_clicks
                   GROUP BY city, district, state
                   ORDER BY click_count DESC
                   LIMIT 50";
    $summaryStmt = $db->prepare($summarySql);
    $summaryStmt->execute();
    $summary = $summaryStmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Get total count
    $countSql = "SELECT COUNT(*) as total FROM whatsapp_clicks";
    $countStmt = $db->prepare($countSql);
    $countStmt->execute();
    $totalCount = $countStmt->fetch(PDO::FETCH_ASSOC)['total'];
    
    // Build filters object (only include non-null values)
    $filters = [];
    if ($city) $filters['city'] = $city;
    if ($district) $filters['district'] = $district;
    if ($state) $filters['state'] = $state;
    
    echo json_encode([
        'success' => true,
        'total_clicks' => intval($totalCount),
        'summary' => $summary,
        'clicks' => $clicks,
        'filters' => $filters,
        'message' => $totalCount > 0 
            ? "Found {$totalCount} WhatsApp clicks" 
            : "No WhatsApp clicks tracked yet. Clicks will appear here when users click WhatsApp buttons."
    ], JSON_PRETTY_PRINT);
    
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'success' => false,
        'error' => $e->getMessage()
    ]);
}

