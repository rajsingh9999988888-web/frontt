<?php
/**
 * Baby Controller - PHP (Simple Version for Debugging)
 * Handles: /api/babies/*
 */

// Error reporting enable karo (temporary)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// CORS headers
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header('Content-Type: application/json');

// Handle OPTIONS
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'] ?? '';

// Remove /api prefix if present
$path = preg_replace('#^/api#', '', $path);
$pathParts = explode('/', trim($path, '/'));

// Simple states endpoint (no database required)
if ($method === 'GET' && isset($pathParts[0]) && $pathParts[0] === 'babies' && isset($pathParts[1]) && $pathParts[1] === 'states') {
    $states = [
        "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
        "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh",
        "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
        "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
        "Uttarakhand", "West Bengal", "Jammu&Kashmir"
    ];
    echo json_encode($states);
    exit;
}

// Try to load config files
try {
    if (!file_exists(__DIR__ . '/config/database.php')) {
        throw new Exception('database.php not found at: ' . __DIR__ . '/config/database.php');
    }
    require_once __DIR__ . '/config/database.php';
    
    if (!file_exists(__DIR__ . '/config/location-data.php')) {
        throw new Exception('location-data.php not found');
    }
    require_once __DIR__ . '/config/location-data.php';
    
    if (!function_exists('getStatesList')) {
        throw new Exception('getStatesList() function not found in location-data.php');
    }
    
    // Database connection
    $database = new Database();
    $db = $database->getConnection();
    
    // Route handling
    if ($method === 'GET' && isset($pathParts[0]) && $pathParts[0] === 'babies' && !isset($pathParts[1])) {
        // GET /api/babies
        // Simple response for now
        echo json_encode(['message' => 'Babies endpoint - database connected']);
    } elseif ($method === 'GET' && isset($pathParts[0]) && $pathParts[0] === 'babies' && isset($pathParts[1]) && $pathParts[1] === 'states') {
        // GET /api/babies/states
        $states = getStatesList();
        echo json_encode($states);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found', 'path' => $path, 'parts' => $pathParts]);
    }
    
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'error' => $e->getMessage(),
        'file' => $e->getFile(),
        'line' => $e->getLine(),
        'trace' => $e->getTraceAsString()
    ]);
}

?>

