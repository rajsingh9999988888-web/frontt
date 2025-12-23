<?php
/**
 * Search Controller - PHP
 * Handles: /api/search/*
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'] ?? '';

// Remove query string first
$path = strtok($path, '?');

// Remove /api prefix if present (for .htaccess routing)
$path = preg_replace('#^/api#', '', $path);
$pathParts = explode('/', trim($path, '/'));

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // GET /api/search/{city}/{category}
    // .htaccess se /search/{city}/{category} aayega (without /api)
    if ($method === 'GET' && isset($pathParts[0]) && $pathParts[0] === 'search' && isset($pathParts[1]) && isset($pathParts[2])) {
        $city = $pathParts[1];
        $category = $pathParts[2];
        searchByCityAndCategory($db, $city, $category);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found']);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

function searchByCityAndCategory($db, $city, $category) {
    // Category normalize karo (slug se actual name)
    $normalizedCategory = normalizeCategory($category);
    
    // Case-insensitive search
    $stmt = $db->prepare("
        SELECT * FROM baby_posts 
        WHERE LOWER(city) = LOWER(?) 
        AND LOWER(category) = LOWER(?)
    ");
    $stmt->execute([$city, $normalizedCategory]);
    $posts = $stmt->fetchAll();
    
    // Production mode check
    $isLocalDev = getenv('APP_ENV') === 'local' || getenv('APP_ENV') === 'development';
    
    // Filter approved posts in production
    if (!$isLocalDev) {
        $posts = array_filter($posts, function($post) {
            return $post['status'] === 'APPROVED';
        });
    }
    
    // Convert to camelCase
    $result = array_map('convertPostToCamelCase', array_values($posts));
    
    header('X-Search-City: ' . $city);
    header('X-Search-Category: ' . $normalizedCategory);
    
    echo json_encode($result);
}

function normalizeCategory($categorySlug) {
    if (empty($categorySlug)) {
        return $categorySlug;
    }
    
    // Slug se readable format
    $normalized = str_replace(['-', '_'], ' ', strtolower($categorySlug));
    
    // Capitalize first letter of each word
    $words = explode(' ', $normalized);
    $result = '';
    foreach ($words as $word) {
        if (!empty($word)) {
            $result .= ucfirst($word) . ' ';
        }
    }
    $result = trim($result);
    
    // Common variations handle karo
    if (stripos($result, 'Call Girl') !== false) {
        return 'Call Girls';
    }
    if (stripos($result, 'Male Escort') !== false) {
        return 'Male Escorts';
    }
    
    return $result;
}

function convertPostToCamelCase($post) {
    return [
        'id' => $post['id'],
        'name' => $post['name'],
        'description' => $post['description'],
        'phone' => $post['phone'],
        'whatsapp' => $post['whatsapp'],
        'imageUrl' => $post['image_url'],
        'state' => $post['state'],
        'district' => $post['district'],
        'city' => $post['city'],
        'category' => $post['category'],
        'address' => $post['address'],
        'postalcode' => $post['postalcode'],
        'age' => $post['age'],
        'nickname' => $post['nickname'],
        'title' => $post['title'],
        'text' => $post['text'],
        'ethnicity' => $post['ethnicity'],
        'nationality' => $post['nationality'],
        'bodytype' => $post['bodytype'],
        'services' => $post['services'],
        'place' => $post['place'],
        'createdAt' => $post['created_at'],
        'status' => $post['status'],
        'userId' => $post['user_id']
    ];
}

