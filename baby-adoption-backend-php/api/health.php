<?php
/**
 * Health Check Controller - PHP
 * Handles: /api/health
 */

require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'];

if ($method === 'GET' && (strpos($path, '/api/health') !== false || strpos($path, '/health') !== false)) {
    $response = [
        'status' => 'UP',
        'timestamp' => date('c')
    ];
    echo json_encode($response);
} else {
    http_response_code(404);
    echo json_encode(['error' => 'Endpoint not found']);
}

