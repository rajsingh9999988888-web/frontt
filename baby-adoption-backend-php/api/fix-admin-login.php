<?php
/**
 * Fix Admin Login - Ensure Admin Account Exists
 * Ye file run karo agar admin login nahi ho raha
 * URL: https://nightsaathi.com/api/fix-admin-login.php
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // Admin Credentials
    $adminEmail = 'admin@134';
    $adminPassword = '9382019794';
    $adminRole = 'ADMIN';
    $adminMobile = '9382019794';
    $adminFullName = 'Admin User';
    
    // Check if admin exists
    $stmt = $db->prepare("SELECT id, email, password, role FROM users WHERE email = ?");
    $stmt->execute([$adminEmail]);
    $existingAdmin = $stmt->fetch();
    
    if ($existingAdmin) {
        // Update password if different (ensure it's correct)
        if ($existingAdmin['password'] !== $adminPassword) {
            $updateStmt = $db->prepare("UPDATE users SET password = ?, role = ?, mobile = ?, full_name = ? WHERE email = ?");
            $updateStmt->execute([$adminPassword, $adminRole, $adminMobile, $adminFullName, $adminEmail]);
            
            echo json_encode([
                'status' => 'updated',
                'message' => 'Admin password and details updated',
                'email' => $adminEmail,
                'password' => $adminPassword,
                'role' => $adminRole,
                'note' => 'Try logging in now with these credentials'
            ]);
        } else {
            // Test login
            $testStmt = $db->prepare("SELECT id, email, password, role FROM users WHERE email = ? AND password = ?");
            $testStmt->execute([$adminEmail, $adminPassword]);
            $testUser = $testStmt->fetch();
            
            if ($testUser) {
                echo json_encode([
                    'status' => 'ok',
                    'message' => 'Admin account exists and password is correct',
                    'email' => $adminEmail,
                    'password' => $adminPassword,
                    'role' => $adminRole,
                    'userId' => $testUser['id'],
                    'note' => 'Login should work. If not, check Authorization header format.'
                ]);
            } else {
                echo json_encode([
                    'status' => 'error',
                    'message' => 'Admin exists but password mismatch',
                    'email' => $adminEmail,
                    'note' => 'Password will be reset'
                ]);
                
                // Reset password
                $resetStmt = $db->prepare("UPDATE users SET password = ? WHERE email = ?");
                $resetStmt->execute([$adminPassword, $adminEmail]);
                
                echo json_encode([
                    'status' => 'password_reset',
                    'message' => 'Password has been reset',
                    'email' => $adminEmail,
                    'password' => $adminPassword
                ]);
            }
        }
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
            $adminFullName
        ]);
        
        $userId = $db->lastInsertId();
        
        echo json_encode([
            'status' => 'created',
            'message' => 'Admin user created successfully',
            'id' => $userId,
            'email' => $adminEmail,
            'password' => $adminPassword,
            'role' => $adminRole,
            'note' => 'You can now login with these credentials'
        ]);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'status' => 'error',
        'message' => $e->getMessage(),
        'trace' => $e->getTraceAsString()
    ]);
}
?>

