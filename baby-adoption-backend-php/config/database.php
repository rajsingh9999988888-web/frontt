<?php
/**
 * Database Configuration
 * Shared hosting ke liye MySQL connection
 */

class Database {
    private $host;
    private $db_name;
    private $username;
    private $password;
    private $conn;

    public function __construct() {
        // Hostinger database configuration - nightsaathi.com
        // Database: u834279997_fun
        // Username: u834279997_rajsingh999998
        $this->host = getenv('MYSQL_HOST') ?: getenv('DB_HOST') ?: 'localhost';
        $this->db_name = getenv('MYSQL_DATABASE') ?: getenv('DB_NAME') ?: 'u834279997_fun';
        $this->username = getenv('MYSQL_USER') ?: getenv('DB_USER') ?: 'u834279997_rajsingh999998';
        $this->password = getenv('MYSQL_PASSWORD') ?: getenv('DB_PASSWORD') ?: '*z>h/\fZ)F:MnV7';
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
                    PDO::ATTR_EMULATE_PREPARES => false
                )
            );
        } catch(PDOException $e) {
            error_log("Database connection error: " . $e->getMessage());
            throw new Exception("Database connection failed");
        }

        return $this->conn;
    }
}

