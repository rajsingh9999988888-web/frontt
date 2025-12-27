<?php
/**
 * Baby Controller - PHP
 * Handles: /api/babies/*
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/jwt.php';
require_once __DIR__ . '/config/cors.php';
require_once __DIR__ . '/config/location-data.php';

header('Content-Type: application/json');

$method = $_SERVER['REQUEST_METHOD'];

// Get path from REQUEST_URI or PATH_INFO (PATH_INFO is more reliable after .htaccess rewrite)
$path = '';
if (isset($_SERVER['PATH_INFO']) && $_SERVER['PATH_INFO'] !== '') {
    // PATH_INFO contains the path after the script name (more reliable for rewrites)
    $path = $_SERVER['PATH_INFO'];
} else {
    // Fallback to REQUEST_URI - extract path after /api/babies/
    $path = $_SERVER['REQUEST_URI'] ?? '';
    // Remove query string
    $path = strtok($path, '?');
    // Remove /api/babies prefix if present
    $path = preg_replace('#^/api/babies/?#', '', $path);
    // Remove /babies.php if present
    $path = preg_replace('#^/babies\.php/?#', '', $path);
}

// Clean up the path - remove leading/trailing slashes
$path = trim($path, '/');
$pathParts = explode('/', $path);
// Remove empty parts and re-index
$pathParts = array_values(array_filter($pathParts, function($part) { 
    return $part !== '' && $part !== null && $part !== false; 
}));

// Debug logging (temporarily enabled for production debugging)
error_log("Method: $method, REQUEST_URI: " . ($_SERVER['REQUEST_URI'] ?? 'N/A') . ", PATH_INFO: " . ($_SERVER['PATH_INFO'] ?? 'N/A') . ", Path: $path, PathParts: " . json_encode($pathParts));

// Backend base URL - production
$backendBaseUrl = getenv('BACKEND_BASE_URL') ?: 'https://nightsaathi.com';
$uploadDir = __DIR__ . '/upload';

// Uploads directory create karo agar nahi hai
if (!file_exists($uploadDir)) {
    mkdir($uploadDir, 0755, true);
}

try {
    $database = new Database();
    $db = $database->getConnection();
    
    // Normalize pathParts - remove 'babies' prefix if present (since .htaccess already routes /api/babies/* to babies.php)
    $normalizedParts = $pathParts;
    if (isset($pathParts[0]) && $pathParts[0] === 'babies') {
        $normalizedParts = array_slice($pathParts, 1);
    }
    
    // Route handling - now works with or without 'babies' prefix
    if ($method === 'GET' && count($normalizedParts) === 0) {
        // GET /api/babies?state=&district=&city=
        getBabies($db);
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && is_numeric($normalizedParts[0])) {
        // GET /api/babies/{id}
        $id = intval($normalizedParts[0]);
        getBabyById($db, $id);
    } elseif ($method === 'POST' && count($normalizedParts) === 0) {
        // POST /api/babies
        addBabyPost($db, $uploadDir, $backendBaseUrl);
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && $normalizedParts[0] === 'states') {
        // GET /api/babies/states
        getStates();
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && $normalizedParts[0] === 'districts') {
        // GET /api/babies/districts?state=
        getDistricts();
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && $normalizedParts[0] === 'cities') {
        // GET /api/babies/cities?district=
        getCities();
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && $normalizedParts[0] === 'admin' && isset($normalizedParts[1]) && $normalizedParts[1] === 'posts') {
        // GET /api/babies/admin/posts?status=
        getAdminPosts($db);
    } elseif ($method === 'PUT' && isset($normalizedParts[0]) && $normalizedParts[0] === 'admin') {
        if (isset($normalizedParts[1]) && $normalizedParts[1] === 'posts' && isset($normalizedParts[2]) && is_numeric($normalizedParts[2])) {
            $id = intval($normalizedParts[2]);
            if (isset($normalizedParts[3]) && $normalizedParts[3] === 'approve') {
                // PUT /api/babies/admin/posts/{id}/approve
                approvePost($db, $id);
            } elseif (isset($normalizedParts[3]) && $normalizedParts[3] === 'reject') {
                // PUT /api/babies/admin/posts/{id}/reject
                rejectPost($db, $id, $uploadDir);
            } else {
                http_response_code(404);
                echo json_encode(['error' => 'Invalid action. Use /approve or /reject', 'debug' => ['normalizedParts' => $normalizedParts]]);
            }
        } elseif (isset($normalizedParts[1]) && $normalizedParts[1] === 'fix-image-urls') {
            // PUT /api/babies/admin/fix-image-urls
            fixImageUrls($db, $backendBaseUrl);
        } else {
            http_response_code(404);
            echo json_encode(['error' => 'Invalid admin endpoint', 'debug' => ['normalizedParts' => $normalizedParts]]);
        }
    } elseif ($method === 'DELETE' && isset($normalizedParts[0]) && $normalizedParts[0] === 'admin' && isset($normalizedParts[1]) && $normalizedParts[1] === 'posts' && isset($normalizedParts[2]) && is_numeric($normalizedParts[2])) {
        // DELETE /api/babies/admin/posts/{id}/delete or /api/babies/admin/posts/{id}
        $id = intval($normalizedParts[2]);
        // Check if pathParts[3] is 'delete' or doesn't exist (both should work)
        if (!isset($normalizedParts[3]) || $normalizedParts[3] === 'delete' || $normalizedParts[3] === '') {
            deletePost($db, $id, $uploadDir);
        } else {
            http_response_code(404);
            echo json_encode(['error' => 'Invalid delete endpoint', 'debug' => ['normalizedParts' => $normalizedParts]]);
        }
    } elseif ($method === 'GET' && isset($normalizedParts[0]) && $normalizedParts[0] === 'images' && isset($normalizedParts[1])) {
        // GET /api/babies/images/{filename}
        getImage($normalizedParts[1], $uploadDir);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found', 'debug' => ['method' => $method, 'pathParts' => $pathParts, 'normalizedParts' => $normalizedParts]]);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

function getBabies($db) {
    // Get filter parameters and normalize (trim empty strings)
    $state = isset($_GET['state']) && $_GET['state'] !== '' ? trim($_GET['state']) : null;
    $district = isset($_GET['district']) && $_GET['district'] !== '' ? trim($_GET['district']) : null;
    $city = isset($_GET['city']) && $_GET['city'] !== '' ? trim($_GET['city']) : null;
    
    // Join with users table to check admin status and order admin posts first
    // Only show APPROVED posts in babylist
    // Show ALL approved posts by default (no date restriction)
    // When filters are applied, show filtered posts
    
    // Build SQL query with location filters
    $sql = "SELECT bp.*, COALESCE(u.role, 'NORMAL') as user_role 
            FROM baby_posts bp 
            LEFT JOIN users u ON bp.user_id = u.id 
            WHERE bp.status = 'APPROVED'";
    $params = [];
    
    // Add location filters to SQL only if filters are provided
    if ($state !== null && $state !== '') {
        $sql .= " AND bp.state = ?";
        $params[] = $state;
    }
    if ($district !== null && $district !== '') {
        $sql .= " AND bp.district = ?";
        $params[] = $district;
    }
    if ($city !== null && $city !== '') {
        $sql .= " AND bp.city = ?";
        $params[] = $city;
    }
    
    $sql .= " ORDER BY 
                CASE WHEN COALESCE(u.role, 'NORMAL') = 'ADMIN' THEN 0 ELSE 1 END,
                bp.created_at DESC";
    
    try {
        $stmt = $db->prepare($sql);
        $stmt->execute($params);
        $posts = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        // Convert to camelCase
        $result = array_map('convertPostToCamelCase', array_values($posts));
        echo json_encode($result);
    } catch (Exception $e) {
        http_response_code(500);
        error_log("getBabies error: " . $e->getMessage());
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function getBabyById($db, $id) {
    $stmt = $db->prepare("
        SELECT bp.*, u.role as user_role, u.email as user_email 
        FROM baby_posts bp 
        LEFT JOIN users u ON bp.user_id = u.id 
        WHERE bp.id = ?
    ");
    $stmt->execute([$id]);
    $post = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if ($post) {
        echo json_encode(convertPostToCamelCase($post));
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Post not found']);
    }
}

function addBabyPost($db, $uploadDir, $backendBaseUrl) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    $userId = JWT::getUserIdFromToken($token);
    if ($userId === -1) {
        http_response_code(401);
        echo json_encode(['error' => 'Invalid token']);
        return;
    }
    
    // Allow any logged-in user to add posts (removed employee/admin restriction)
    // Check if user is admin - admin posts should be auto-approved
    $isAdmin = JWT::isAdmin($token);
    $postStatus = $isAdmin ? 'APPROVED' : 'PENDING';
    
    // Form data se values lo
    $name = $_POST['name'] ?? '';
    $description = $_POST['description'] ?? '';
    $phone = $_POST['phone'] ?? '';
    $whatsapp = $_POST['whatsapp'] ?? '';
    $state = $_POST['state'] ?? '';
    $district = $_POST['district'] ?? '';
    $city = $_POST['city'] ?? '';
    $category = $_POST['category'] ?? '';
    $address = $_POST['address'] ?? '';
    $postalcode = $_POST['postalcode'] ?? '';
    $age = isset($_POST['age']) && $_POST['age'] !== '' ? intval($_POST['age']) : 0;
    $nickname = $_POST['nickname'] ?? '';
    $title = $_POST['title'] ?? '';
    $text = $_POST['text'] ?? '';
    $ethnicity = $_POST['ethnicity'] ?? '';
    $nationality = $_POST['nationality'] ?? '';
    $bodytype = $_POST['bodytype'] ?? '';
    $services = $_POST['services'] ?? '';
    $place = $_POST['place'] ?? '';
    
    // Image upload handle karo - multiple images support
    $imageUrl = '';
    if (isset($_FILES['images'])) {
        // Check if it's a single file or array of files
        if (is_array($_FILES['images']['error'])) {
            // Multiple files - process first valid image
            foreach ($_FILES['images']['error'] as $key => $error) {
                if ($error === UPLOAD_ERR_OK) {
                    $originalName = $_FILES['images']['name'][$key];
                    $tmpName = $_FILES['images']['tmp_name'][$key];
                    
                    // Validate file type
                    $allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
                    $fileType = $_FILES['images']['type'][$key];
                    if (!in_array($fileType, $allowedTypes)) {
                        continue; // Skip invalid file types
                    }
                    
                    $extension = strtolower(pathinfo($originalName, PATHINFO_EXTENSION));
                    // Generate unique filename - simpler format for better compatibility
                    $uniqueFilename = uniqid('img_' . time() . '_') . '_' . bin2hex(random_bytes(8)) . '.' . $extension;
                    $filePath = $uploadDir . '/' . $uniqueFilename;
                    
                    if (move_uploaded_file($tmpName, $filePath)) {
                        // Verify file was actually moved
                        if (file_exists($filePath)) {
                            $imageUrl = $backendBaseUrl . '/api/babies/images/' . $uniqueFilename;
                            break; // Use first successfully uploaded image
                        }
                    }
                }
            }
        } else {
            // Single file upload
            if ($_FILES['images']['error'] === UPLOAD_ERR_OK) {
                $originalName = $_FILES['images']['name'];
                $tmpName = $_FILES['images']['tmp_name'];
                
                // Validate file type
                $allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
                $fileType = $_FILES['images']['type'];
                if (in_array($fileType, $allowedTypes)) {
                    $extension = strtolower(pathinfo($originalName, PATHINFO_EXTENSION));
                    // Generate unique filename - simpler format for better compatibility
                    $uniqueFilename = uniqid('img_' . time() . '_') . '_' . bin2hex(random_bytes(8)) . '.' . $extension;
                    $filePath = $uploadDir . '/' . $uniqueFilename;
                    
                    if (move_uploaded_file($tmpName, $filePath)) {
                        // Verify file was actually moved
                        if (file_exists($filePath)) {
                            $imageUrl = $backendBaseUrl . '/api/babies/images/' . $uniqueFilename;
                        }
                    }
                }
            }
        }
    }
    
    // Database mein insert karo
    try {
        $stmt = $db->prepare("
            INSERT INTO baby_posts (
                name, description, phone, whatsapp, state, district, city, category,
                address, postalcode, age, nickname, title, text, ethnicity, nationality,
                bodytype, services, place, image_url, created_at, status, user_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)
        ");
        
        $result = $stmt->execute([
            $name, $description, $phone, $whatsapp, $state, $district, $city, $category,
            $address, $postalcode, $age, $nickname, $title, $text, $ethnicity, $nationality,
            $bodytype, $services, $place, $imageUrl, $postStatus, $userId
        ]);
        
        if (!$result) {
            $errorInfo = $stmt->errorInfo();
            http_response_code(500);
            echo json_encode(['error' => 'Failed to insert post: ' . ($errorInfo[2] ?? 'Unknown error')]);
            return;
        }
        
        $postId = $db->lastInsertId();
        
        if (!$postId) {
            http_response_code(500);
            echo json_encode(['error' => 'Failed to get post ID']);
            return;
        }
        
        // Created post fetch karo with user info
        $stmt = $db->prepare("
            SELECT bp.*, u.role as user_role, u.email as user_email 
            FROM baby_posts bp 
            LEFT JOIN users u ON bp.user_id = u.id 
            WHERE bp.id = ?
        ");
        $stmt->execute([$postId]);
        $post = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$post) {
            http_response_code(500);
            echo json_encode(['error' => 'Post created but could not be retrieved']);
            return;
        }
        
        http_response_code(201);
        echo json_encode(convertPostToCamelCase($post));
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function getStates() {
    $states = getStatesList();
    echo json_encode($states);
}

function getDistricts() {
    $state = $_GET['state'] ?? '';
    $districts = getDistrictsForState($state);
    echo json_encode($districts);
}

function getCities() {
    $district = $_GET['district'] ?? '';
    $cities = getCitiesForDistrict($district);
    echo json_encode($cities);
}

function getAdminPosts($db) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied - Admin role required']);
        return;
    }
    
    $status = $_GET['status'] ?? null;
    
    try {
        // Always return all posts for admin, ordered by created_at DESC (newest first)
        // Join with users table to get user role information
        if ($status) {
            $sql = "SELECT bp.*, u.role as user_role, u.email as user_email 
                    FROM baby_posts bp 
                    LEFT JOIN users u ON bp.user_id = u.id 
                    WHERE bp.status = ? 
                    ORDER BY bp.created_at DESC";
            $stmt = $db->prepare($sql);
            $stmt->execute([strtoupper($status)]);
        } else {
            // Return all posts regardless of status - ensure we get ALL posts from ALL users
            $sql = "SELECT bp.*, u.role as user_role, u.email as user_email 
                    FROM baby_posts bp 
                    LEFT JOIN users u ON bp.user_id = u.id 
                    ORDER BY 
                        CASE WHEN bp.status = 'PENDING' THEN 0 ELSE 1 END,
                        CASE WHEN COALESCE(u.role, '') = 'ADMIN' THEN 0 ELSE 1 END,
                        bp.created_at DESC";
            $stmt = $db->prepare($sql);
            $stmt->execute();
        }
        
        $posts = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $result = array_map('convertPostToCamelCase', $posts);
        echo json_encode($result);
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function approvePost($db, $id) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied - Admin role required']);
        return;
    }
    
    try {
        // First check if post exists
        $stmt = $db->prepare("SELECT id FROM baby_posts WHERE id = ?");
        $stmt->execute([$id]);
        $post = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$post) {
            http_response_code(404);
            echo json_encode(['error' => 'Post not found']);
            return;
        }
        
        $stmt = $db->prepare("UPDATE baby_posts SET status = 'APPROVED' WHERE id = ?");
        $result = $stmt->execute([$id]);
        
        if (!$result) {
            $errorInfo = $stmt->errorInfo();
            http_response_code(500);
            echo json_encode(['error' => 'Failed to approve post: ' . ($errorInfo[2] ?? 'Unknown error')]);
            return;
        }
        
        $rowsAffected = $stmt->rowCount();
        if ($rowsAffected === 0) {
            http_response_code(404);
            echo json_encode(['error' => 'Post not found or already approved']);
            return;
        }
        
        http_response_code(200);
        echo json_encode(['message' => 'Post approved', 'id' => $id]);
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function rejectPost($db, $id, $uploadDir) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied - Admin role required']);
        return;
    }
    
    try {
        // First check if post exists
        $stmt = $db->prepare("SELECT id, image_url FROM baby_posts WHERE id = ?");
        $stmt->execute([$id]);
        $post = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$post) {
            http_response_code(404);
            echo json_encode(['error' => 'Post not found']);
            return;
        }
        
        // Image delete karo agar hai
        if ($post && isset($post['image_url']) && !empty($post['image_url'])) {
            $filename = basename($post['image_url']);
            if ($filename) {
                $filePath = $uploadDir . '/' . $filename;
                if (file_exists($filePath) && is_file($filePath)) {
                    @unlink($filePath);
                }
            }
        }
        
        // Post delete karo
        $stmt = $db->prepare("DELETE FROM baby_posts WHERE id = ?");
        $result = $stmt->execute([$id]);
        
        if (!$result) {
            $errorInfo = $stmt->errorInfo();
            http_response_code(500);
            echo json_encode(['error' => 'Failed to reject post: ' . ($errorInfo[2] ?? 'Unknown error')]);
            return;
        }
        
        $rowsAffected = $stmt->rowCount();
        if ($rowsAffected === 0) {
            http_response_code(404);
            echo json_encode(['error' => 'Post not found or already deleted']);
            return;
        }
        
        http_response_code(200);
        echo json_encode(['message' => 'Post rejected and deleted', 'id' => $id]);
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function deletePost($db, $id, $uploadDir) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied - Admin role required']);
        return;
    }
    
    try {
        // First check if post exists
        $stmt = $db->prepare("SELECT id, image_url FROM baby_posts WHERE id = ?");
        $stmt->execute([$id]);
        $post = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$post) {
            http_response_code(404);
            echo json_encode(['error' => 'Post not found']);
            return;
        }
        
        // Image delete karo if exists
        if ($post && isset($post['image_url']) && !empty($post['image_url'])) {
            $filename = basename($post['image_url']);
            if ($filename) {
                $filePath = $uploadDir . '/' . $filename;
                if (file_exists($filePath) && is_file($filePath)) {
                    @unlink($filePath); // Suppress errors if file doesn't exist
                }
            }
        }
        
        // Post delete karo
        $stmt = $db->prepare("DELETE FROM baby_posts WHERE id = ?");
        $result = $stmt->execute([$id]);
        
        if (!$result) {
            $errorInfo = $stmt->errorInfo();
            http_response_code(500);
            echo json_encode(['error' => 'Failed to delete post: ' . ($errorInfo[2] ?? 'Unknown error')]);
            return;
        }
        
        // Verify deletion by checking if post still exists
        $verifyStmt = $db->prepare("SELECT id FROM baby_posts WHERE id = ?");
        $verifyStmt->execute([$id]);
        $stillExists = $verifyStmt->fetch(PDO::FETCH_ASSOC);
        
        if ($stillExists) {
            // Post still exists, deletion failed
            http_response_code(500);
            echo json_encode(['error' => 'Failed to delete post: Post still exists after delete operation']);
            return;
        }
        
        // Post successfully deleted - return success response
        http_response_code(200);
        echo json_encode([
            'message' => 'Post deleted successfully', 
            'id' => $id,
            'deleted' => true
        ]);
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
    }
}

function fixImageUrls($db, $backendBaseUrl) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token) || !JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied']);
        return;
    }
    
    $stmt = $db->query("SELECT id, image_url FROM baby_posts WHERE image_url IS NOT NULL AND image_url != ''");
    $posts = $stmt->fetchAll();
    
    $fixed = 0;
    foreach ($posts as $post) {
        $imageUrl = $post['image_url'];
        $newUrl = $imageUrl;
        
        if (strpos($imageUrl, 'localhost:8082') !== false) {
            $newUrl = str_replace('http://localhost:8082', $backendBaseUrl, $imageUrl);
            $fixed++;
        } elseif (strpos($imageUrl, 'http://') === 0 && strpos($imageUrl, 'localhost') === false) {
            $newUrl = str_replace('http://', 'https://', $imageUrl);
            $fixed++;
        }
        
        if ($newUrl !== $imageUrl) {
            $updateStmt = $db->prepare("UPDATE baby_posts SET image_url = ? WHERE id = ?");
            $updateStmt->execute([$newUrl, $post['id']]);
        }
    }
    
    echo json_encode([
        'message' => "Fixed $fixed image URLs",
        'totalPosts' => count($posts),
        'fixed' => $fixed
    ]);
}

function getImage($filename, $uploadDir) {
    // Security: directory traversal prevent karo
    if (strpos($filename, '..') !== false || strpos($filename, '/') !== false || strpos($filename, '\\') !== false) {
        http_response_code(400);
        return;
    }
    
    $filePath = $uploadDir . '/' . $filename;
    
    if (file_exists($filePath) && is_file($filePath)) {
        $extension = strtolower(pathinfo($filename, PATHINFO_EXTENSION));
        $contentType = 'image/jpeg';
        
        if ($extension === 'png') $contentType = 'image/png';
        elseif ($extension === 'gif') $contentType = 'image/gif';
        elseif ($extension === 'webp') $contentType = 'image/webp';
        
        header('Content-Type: ' . $contentType);
        header('Content-Disposition: inline; filename="' . $filename . '"');
        readfile($filePath);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Image not found']);
    }
}

function convertPostToCamelCase($post) {
    // Ensure image_url is always a string, not null
    $imageUrl = isset($post['image_url']) && !empty($post['image_url']) ? $post['image_url'] : '';
    
    return [
        'id' => $post['id'] ?? null,
        'name' => $post['name'] ?? '',
        'description' => $post['description'] ?? '',
        'phone' => $post['phone'] ?? '',
        'whatsapp' => $post['whatsapp'] ?? '',
        'imageUrl' => $imageUrl,
        'state' => $post['state'] ?? '',
        'district' => $post['district'] ?? '',
        'city' => $post['city'] ?? '',
        'category' => $post['category'] ?? '',
        'address' => $post['address'] ?? '',
        'postalcode' => $post['postalcode'] ?? '',
        'age' => $post['age'] ?? 0,
        'nickname' => $post['nickname'] ?? '',
        'title' => $post['title'] ?? '',
        'text' => $post['text'] ?? '',
        'ethnicity' => $post['ethnicity'] ?? '',
        'nationality' => $post['nationality'] ?? '',
        'bodytype' => $post['bodytype'] ?? '',
        'services' => $post['services'] ?? '',
        'place' => $post['place'] ?? '',
        'createdAt' => $post['created_at'] ?? null,
        'status' => $post['status'] ?? 'PENDING',
        'userId' => $post['user_id'] ?? null
    ];
}
