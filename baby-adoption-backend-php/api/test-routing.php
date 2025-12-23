<?php
/**
 * Test routing - Debug file
 * This will show what path is being received
 */

error_reporting(E_ALL);
ini_set('display_errors', 1);

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'] ?? '';

// Remove query string first
$path = strtok($path, '?');

// Remove /api prefix if present (for .htaccess routing)
$path = preg_replace('#^/api#', '', $path);
$pathParts = explode('/', trim($path, '/'));

$debug = [
    'method' => $method,
    'original_request_uri' => $_SERVER['REQUEST_URI'] ?? 'NOT SET',
    'path_after_query_remove' => strtok($_SERVER['REQUEST_URI'] ?? '', '?'),
    'path_after_api_remove' => $path,
    'path_parts' => $pathParts,
    'path_parts_count' => count($pathParts),
    'path_parts_0' => $pathParts[0] ?? 'NOT SET',
    'path_parts_1' => $pathParts[1] ?? 'NOT SET',
    'script_name' => $_SERVER['SCRIPT_NAME'] ?? 'NOT SET',
    'php_self' => $_SERVER['PHP_SELF'] ?? 'NOT SET',
    'server' => [
        'REQUEST_METHOD' => $_SERVER['REQUEST_METHOD'] ?? 'NOT SET',
        'REQUEST_URI' => $_SERVER['REQUEST_URI'] ?? 'NOT SET',
        'SCRIPT_NAME' => $_SERVER['SCRIPT_NAME'] ?? 'NOT SET',
        'PHP_SELF' => $_SERVER['PHP_SELF'] ?? 'NOT SET',
    ],
    'routing_check' => [
        'is_post' => $method === 'POST',
        'path_parts_0_is_users' => isset($pathParts[0]) && $pathParts[0] === 'users',
        'path_parts_1_is_signup' => isset($pathParts[1]) && $pathParts[1] === 'signup',
        'should_match_signup' => $method === 'POST' && isset($pathParts[0]) && $pathParts[0] === 'users' && isset($pathParts[1]) && $pathParts[1] === 'signup',
    ]
];

echo json_encode($debug, JSON_PRETTY_PRINT);

