<?php
/**
 * Database Configuration - Hostinger Production
 * Hostinger shared hosting ke liye optimized
 */

class Database {
    private $host;
    private $db_name;
    private $username;
    private $password;
    private $conn;

    public function __construct() {
        // Hostinger database configuration
        // hPanel → Databases → MySQL Databases se ye details lo
        
        // Option 1: Environment variables (recommended for production)
        $this->host = getenv('MYSQL_HOST') ?: getenv('DB_HOST') ?: 'localhost';
        $this->db_name = getenv('MYSQL_DATABASE') ?: getenv('DB_NAME');
        $this->username = getenv('MYSQL_USER') ?: getenv('DB_USER');
        $this->password = getenv('MYSQL_PASSWORD') ?: getenv('DB_PASSWORD');
        
        // Option 2: Direct configuration (Hostinger format)
        // Hostinger database format: u{userid}_{dbname}
        // Example: u123456789_fun
        if (empty($this->db_name)) {
            $this->host = 'localhost'; // Hostinger mein usually localhost hota hai
            $this->db_name = 'u123456789_fun'; // Apna database name yahan set karo
            $this->username = 'u123456789_admin'; // Apna username yahan set karo
            $this->password = 'your-strong-password'; // Apna password yahan set karo
        }
    }

    public function getConnection() {
        $this->conn = null;

        try {
            $this->conn = new PDO(
                "mysql:host=" . $this->host . ";dbname=" . $this->db_name . ";charset=utf8mb4",
                $this->username,
                $this->password,
                array(
                    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                    PDO::ATTR_EMULATE_PREPARES => false,
                    PDO::ATTR_PERSISTENT => false // Hostinger ke liye persistent connection off
                )
            );
        } catch(PDOException $e) {
            // Production mein error details log karo, user ko nahi dikhao
            error_log("Database connection error: " . $e->getMessage());
            throw new Exception("Database connection failed. Please contact administrator.");
        }

        return $this->conn;
    }
}
?>

