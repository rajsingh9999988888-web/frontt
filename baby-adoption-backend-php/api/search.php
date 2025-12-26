<?php
/**
 * Search Controller - PHP
 * Handles: /api/search/*
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/cors.php';

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];

// Get path from REQUEST_URI or PATH_INFO (PATH_INFO is more reliable after .htaccess rewrite)
$path = '';
if (isset($_SERVER['PATH_INFO']) && $_SERVER['PATH_INFO'] !== '') {
    // PATH_INFO contains the path after the script name (more reliable for rewrites)
    $path = $_SERVER['PATH_INFO'];
} else {
    // Fallback to REQUEST_URI - extract path after /api/search/
    $path = $_SERVER['REQUEST_URI'] ?? '';
    // Remove query string
    $path = strtok($path, '?');
    // Remove /api/search prefix if present
    $path = preg_replace('#^/api/search/?#', '', $path);
    // Remove /search.php if present
    $path = preg_replace('#^/search\.php/?#', '', $path);
}

// Clean up the path
$path = ltrim($path, '/');
$pathParts = explode('/', $path);
// Remove empty parts and re-index
$pathParts = array_values(array_filter($pathParts, function($part) { return $part !== '' && $part !== null; }));

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // GET /api/search/{city}/{category}
    // .htaccess se /search/{city}/{category} aayega (without /api)
    // Path parts: [city, category] after .htaccess rewrite
    if ($method === 'GET' && isset($pathParts[0]) && isset($pathParts[1])) {
        $city = urldecode($pathParts[0]); // Decode URL-encoded city name
        $category = urldecode($pathParts[1]); // Decode URL-encoded category
        searchByCityAndCategory($db, $city, $category);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found. Expected: /api/search/{city}/{category}']);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

/**
 * Search by city and category - Case-insensitive search
 * Uses existing database columns: city, category
 * Only returns APPROVED posts in production
 */
function searchByCityAndCategory($db, $city, $category) {
    // Category normalize karo (slug se actual name)
    // Handles: "call-girls", "call girls", "Call Girls" -> "Call Girls"
    $normalizedCategory = normalizeCategory($category);
    
    // Case-insensitive search using existing columns
    // This is the repository method equivalent: findByCityIgnoreCaseAndCategoryIgnoreCase
    $sql = "SELECT bp.*, COALESCE(u.role, 'NORMAL') as user_role 
            FROM baby_posts bp 
            LEFT JOIN users u ON bp.user_id = u.id 
            WHERE LOWER(bp.city) = LOWER(?) 
            AND LOWER(bp.category) = LOWER(?)
            AND bp.status = 'APPROVED'
            ORDER BY 
                CASE WHEN COALESCE(u.role, 'NORMAL') = 'ADMIN' THEN 0 ELSE 1 END,
                bp.created_at DESC";
    
    $stmt = $db->prepare($sql);
    $stmt->execute([$city, $normalizedCategory]);
    $posts = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    // Convert to camelCase
    $result = array_map('convertPostToCamelCase', array_values($posts));
    
    // Add response headers as required
    header('X-Search-City: ' . $city);
    header('X-Search-Category: ' . $normalizedCategory);
    
    echo json_encode($result);
}

/**
 * Normalize category slug to database format
 * Handles: "call-girls", "call girls", "Call Girls" -> "Call Girls"
 * Supports all categories dynamically
 */
function normalizeCategory($categorySlug) {
    if (empty($categorySlug)) {
        return $categorySlug;
    }
    
    // Slug se readable format (replace hyphens/underscores with spaces)
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
    
    // Common variations handle karo (normalize to database format)
    // "call girls" or "call-girls" -> "Call Girls"
    if (stripos($result, 'call girl') !== false) {
        return 'Call Girls';
    }
    // "male escort" or "male-escorts" -> "Male Escorts"
    if (stripos($result, 'male escort') !== false) {
        return 'Male Escorts';
    }
    // "massage" -> "Massage"
    if (stripos($result, 'massage') !== false) {
        return 'Massage';
    }
    
    // For any other category, return title case
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

