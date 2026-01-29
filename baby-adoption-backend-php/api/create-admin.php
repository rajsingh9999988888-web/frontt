<?php
/**
 * Script to create an admin user or update existing user to admin
 * Run this once to create admin account
 * Usage: php create-admin.php
 */

require_once __DIR__ . '/config/database.php';

try {
    $database = new Database();
    $db = $database->getConnection();
    
    $adminEmail = 'admin@134';
    $adminPassword = 'admin123'; // Change this password!
    
    // Check if admin already exists
    $stmt = $db->prepare("SELECT id, email, role FROM users WHERE email = ?");
    $stmt->execute([$adminEmail]);
    $existingUser = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if ($existingUser) {
        // Update existing user to admin
        $stmt = $db->prepare("UPDATE users SET role = 'ADMIN', password = ? WHERE email = ?");
        $stmt->execute([$adminPassword, $adminEmail]);
        echo "✅ Updated user '{$adminEmail}' to ADMIN role\n";
        echo "Password reset to: {$adminPassword}\n";
    } else {
        // Create new admin user
        $stmt = $db->prepare("
            INSERT INTO users (email, password, role, full_name)
            VALUES (?, ?, 'ADMIN', 'Admin User')
        ");
        $stmt->execute([$adminEmail, $adminPassword]);
        echo "✅ Created admin user '{$adminEmail}'\n";
        echo "Password: {$adminPassword}\n";
    }
    
    echo "\nYou can now login with:\n";
    echo "Email: {$adminEmail}\n";
    echo "Password: {$adminPassword}\n";
    echo "\n⚠️  IMPORTANT: Change the password after first login!\n";
    
} catch (Exception $e) {
    echo "❌ Error: " . $e->getMessage() . "\n";
}
