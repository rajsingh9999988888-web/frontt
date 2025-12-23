<?php
/**
 * Main Entry Point - PHP Backend
 * Shared hosting ke liye
 */

// Error reporting (production mein off karo)
error_reporting(E_ALL);
ini_set('display_errors', 0);
ini_set('log_errors', 1);

// Autoloader (agar needed ho)
spl_autoload_register(function ($class) {
    $file = __DIR__ . '/config/' . str_replace('\\', '/', $class) . '.php';
    if (file_exists($file)) {
        require_once $file;
    }
});

// Request routing
$requestUri = $_SERVER['REQUEST_URI'];
$requestMethod = $_SERVER['REQUEST_METHOD'];

// Remove query string
$path = parse_url($requestUri, PHP_URL_PATH);
$pathParts = explode('/', trim($path, '/'));

// Route to appropriate controller
if (count($pathParts) >= 2 && $pathParts[0] === 'api') {
    $controller = $pathParts[1];
    
    switch ($controller) {
        case 'users':
            require_once __DIR__ . '/api/users.php';
            break;
        case 'babies':
            require_once __DIR__ . '/api/babies.php';
            break;
        case 'search':
            require_once __DIR__ . '/api/search.php';
            break;
        case 'health':
            require_once __DIR__ . '/api/health.php';
            break;
        default:
            http_response_code(404);
            header('Content-Type: application/json');
            echo json_encode(['error' => 'API endpoint not found']);
            break;
    }
} else {
    http_response_code(404);
    header('Content-Type: application/json');
    echo json_encode(['error' => 'Invalid API path']);
}

