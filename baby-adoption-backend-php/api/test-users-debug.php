<?php
/**
 * Debug file for users.php routing
 */

error_reporting(E_ALL);
ini_set('display_errors', 1);

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'] ?? '';

echo json_encode([
    'method' => $method,
    'original_path' => $path,
    'path_after_query_remove' => strtok($path, '?'),
    'path_after_api_remove' => preg_replace('#^/api#', '', strtok($path, '?')),
    'path_parts' => explode('/', trim(preg_replace('#^/api#', '', strtok($path, '?')), '/')),
    'server' => [
        'REQUEST_URI' => $_SERVER['REQUEST_URI'] ?? 'NOT SET',
        'REQUEST_METHOD' => $_SERVER['REQUEST_METHOD'] ?? 'NOT SET',
        'SCRIPT_NAME' => $_SERVER['SCRIPT_NAME'] ?? 'NOT SET',
        'PHP_SELF' => $_SERVER['PHP_SELF'] ?? 'NOT SET',
    ]
], JSON_PRETTY_PRINT);

