<?php
/**
 * Automatic Database Tables Setup
 * Ye file phpMyAdmin se run karo ya browser se
 * Automatically tables create kar dega
 */

// Database configuration
$host = 'localhost';
$dbname = 'fun';
$username = 'root';
$password = ''; // XAMPP default - empty password

try {
    // Database connection
    $conn = new PDO("mysql:host=$host;charset=utf8mb4", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    // Database create karo (agar nahi hai)
    $conn->exec("CREATE DATABASE IF NOT EXISTS `$dbname` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
    $conn->exec("USE `$dbname`");
    
    echo "<h2>✅ Database '$dbname' ready!</h2>";
    
    // Users table
    $sql = "CREATE TABLE IF NOT EXISTS `users` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `email` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      `role` enum('NORMAL','EMPLOYEE','ADMIN') DEFAULT 'NORMAL',
      `mobile` varchar(20) DEFAULT NULL,
      `full_name` varchar(255) DEFAULT NULL,
      `primary_skill` varchar(255) DEFAULT NULL,
      `experience` varchar(255) DEFAULT NULL,
      `company_name` varchar(255) DEFAULT NULL,
      `recruiter_name` varchar(255) DEFAULT NULL,
      `address` text DEFAULT NULL,
      `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`),
      UNIQUE KEY `email` (`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    
    $conn->exec($sql);
    echo "<p>✅ Users table created!</p>";
    
    // Baby posts table
    $sql = "CREATE TABLE IF NOT EXISTS `baby_posts` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(255) DEFAULT NULL,
      `description` text DEFAULT NULL,
      `phone` varchar(20) DEFAULT NULL,
      `whatsapp` varchar(20) DEFAULT NULL,
      `image_url` varchar(500) DEFAULT NULL,
      `state` varchar(100) DEFAULT NULL,
      `district` varchar(100) DEFAULT NULL,
      `city` varchar(100) DEFAULT NULL,
      `category` varchar(100) DEFAULT NULL,
      `address` text DEFAULT NULL,
      `postalcode` varchar(10) DEFAULT NULL,
      `age` int(11) DEFAULT 0,
      `nickname` varchar(255) DEFAULT NULL,
      `title` varchar(255) DEFAULT NULL,
      `text` text DEFAULT NULL,
      `ethnicity` varchar(100) DEFAULT NULL,
      `nationality` varchar(100) DEFAULT NULL,
      `bodytype` varchar(100) DEFAULT NULL,
      `services` text DEFAULT NULL,
      `place` varchar(255) DEFAULT NULL,
      `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `status` enum('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
      `user_id` int(11) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `user_id` (`user_id`),
      KEY `status` (`status`),
      KEY `state` (`state`),
      KEY `district` (`district`),
      KEY `city` (`city`),
      KEY `category` (`category`),
      KEY `created_at` (`created_at`),
      CONSTRAINT `fk_baby_posts_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    
    $conn->exec($sql);
    echo "<p>✅ Baby posts table created!</p>";
    
    // Test admin user
    $sql = "INSERT INTO `users` (`email`, `password`, `role`, `full_name`, `mobile`) 
            VALUES ('admin@example.com', 'admin123', 'ADMIN', 'Admin User', '1234567890')
            ON DUPLICATE KEY UPDATE `email`=`email`";
    $conn->exec($sql);
    echo "<p>✅ Admin user created! (admin@example.com / admin123)</p>";
    
    // Test employee user
    $sql = "INSERT INTO `users` (`email`, `password`, `role`, `company_name`, `recruiter_name`, `mobile`, `address`) 
            VALUES ('employee@example.com', 'employee123', 'EMPLOYEE', 'Test Company', 'Test Recruiter', '1234567890', 'Test Address')
            ON DUPLICATE KEY UPDATE `email`=`email`";
    $conn->exec($sql);
    echo "<p>✅ Employee user created! (employee@example.com / employee123)</p>";
    
    echo "<h2>🎉 Setup Complete!</h2>";
    echo "<p><strong>Next Steps:</strong></p>";
    echo "<ol>";
    echo "<li>PHP files copy karo: <code>C:\\xampp\\htdocs\\baby-adoption-backend-php</code></li>";
    echo "<li>uploads folder create karo</li>";
    echo "<li>Test karo: <a href='test-setup.php'>test-setup.php</a></li>";
    echo "</ol>";
    
} catch(PDOException $e) {
    echo "<h2>❌ Error:</h2>";
    echo "<p>" . htmlspecialchars($e->getMessage()) . "</p>";
    echo "<p><strong>Solution:</strong></p>";
    echo "<ul>";
    echo "<li>MySQL running hai ya nahi check karo</li>";
    echo "<li>Password sahi hai ya nahi check karo (XAMPP default: empty)</li>";
    echo "<li>Database 'fun' already exists hai ya nahi</li>";
    echo "</ul>";
}

?>

