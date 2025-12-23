<?php
/**
 * API Root Index - PHP
 * Handles: /api/ (root API access)
 */

// CORS headers
if (file_exists(__DIR__ . '/config/cors.php')) {
    require_once __DIR__ . '/config/cors.php';
} elseif (file_exists(__DIR__ . '/../config/cors.php')) {
    require_once __DIR__ . '/../config/cors.php';
} else {
    // Fallback: Simple CORS headers
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
    header("Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With");
    header("Access-Control-Allow-Credentials: true");
}

// Handle OPTIONS preflight request
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}

header('Content-Type: application/json');

// Return API info
$response = [
    'status' => 'UP',
    'message' => 'Nightsaathi API',
    'version' => '1.0.0',
    'endpoints' => [
        '/api/health' => 'Health check',
        '/api/users/login' => 'User login',
        '/api/users/signup' => 'User signup',
        '/api/babies/states' => 'Get states',
        '/api/babies/districts' => 'Get districts',
        '/api/babies/cities' => 'Get cities',
        '/api/babies' => 'Get baby posts',
        '/api/search/:city/:category' => 'Search by city and category'
    ],
    'timestamp' => date('c')
];

echo json_encode($response, JSON_PRETTY_PRINT);
?>

