<?php
/**
 * Create Default Admin User
 * Ye file ek baar run karo admin user create karne ke liye
 * URL: https://nightsaathi.com/api/create-admin.php
 */

require_once __DIR__ . '/config/database.php';

header('Content-Type: application/json');

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // Default Admin Credentials (Java backend se same)
    $adminEmail = 'admin@134';
    $adminPassword = '9382019794';
    $adminRole = 'ADMIN';
    $adminMobile = '9382019794';
    
    // Check if admin already exists
    $stmt = $db->prepare("SELECT id FROM users WHERE email = ?");
    $stmt->execute([$adminEmail]);
    $existingAdmin = $stmt->fetch();
    
    if ($existingAdmin) {
        echo json_encode([
            'status' => 'exists',
            'message' => 'Admin user already exists',
            'email' => $adminEmail,
            'password' => $adminPassword,
            'role' => $adminRole
        ]);
    } else {
        // Create admin user
        $stmt = $db->prepare("
            INSERT INTO users (email, password, role, mobile, full_name)
            VALUES (?, ?, ?, ?, ?)
        ");
        
        $stmt->execute([
            $adminEmail,
            $adminPassword,
            $adminRole,
            $adminMobile,
            'Admin User'
        ]);
        
        $userId = $db->lastInsertId();
        
        echo json_encode([
            'status' => 'created',
            'message' => 'Admin user created successfully',
            'id' => $userId,
            'email' => $adminEmail,
            'password' => $adminPassword,
            'role' => $adminRole,
            'note' => 'Please delete this file after creating admin for security'
        ]);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'status' => 'error',
        'message' => $e->getMessage()
    ]);
}
?>

