<?php
/**
 * User Controller - PHP
 * Handles: /api/users/*
 */

require_once __DIR__ . '/config/database.php';
require_once __DIR__ . '/config/jwt.php';
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
    
    // Route handling - .htaccess se /users/signup aayega (without /api)
    if ($method === 'POST' && isset($pathParts[0]) && $pathParts[0] === 'users' && isset($pathParts[1]) && $pathParts[1] === 'signup') {
        // POST /api/users/signup
        signup($db);
    } elseif ($method === 'POST' && isset($pathParts[0]) && $pathParts[0] === 'users' && isset($pathParts[1]) && $pathParts[1] === 'login') {
        // POST /api/users/login
        login($db);
    } elseif ($method === 'GET' && isset($pathParts[0]) && $pathParts[0] === 'users' && !isset($pathParts[1])) {
        // GET /api/users
        getUsers($db);
    } elseif ($method === 'PUT' && isset($pathParts[0]) && $pathParts[0] === 'users' && isset($pathParts[1]) && is_numeric($pathParts[1]) && isset($pathParts[2]) && $pathParts[2] === 'role') {
        // PUT /api/users/{id}/role
        $userId = intval($pathParts[1]);
        updateRole($db, $userId);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Endpoint not found']);
    }
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

function signup($db) {
    $data = json_decode(file_get_contents('php://input'), true);
    
    if (!isset($data['email']) || !isset($data['password'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Email and password are required']);
        return;
    }
    
    $email = $data['email'];
    $password = $data['password'];
    $role = isset($data['role']) ? strtoupper($data['role']) : 'NORMAL';
    
    // Valid roles check
    if (!in_array($role, ['NORMAL', 'EMPLOYEE', 'ADMIN'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid role']);
        return;
    }
    
    // Check if email already exists
    $stmt = $db->prepare("SELECT id FROM users WHERE email = ?");
    $stmt->execute([$email]);
    if ($stmt->fetch()) {
        http_response_code(400);
        echo json_encode(['error' => 'Email already exists']);
        return;
    }
    
    // Insert new user
    $stmt = $db->prepare("
        INSERT INTO users (email, password, role, mobile, full_name, primary_skill, experience, company_name, recruiter_name, address)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ");
    
    $mobile = $data['mobile'] ?? null;
    $fullName = $data['fullName'] ?? null;
    $primarySkill = $data['primarySkill'] ?? null;
    $experience = $data['experience'] ?? null;
    $companyName = $data['companyName'] ?? null;
    $recruiterName = $data['recruiterName'] ?? null;
    $address = $data['address'] ?? null;
    
    $stmt->execute([
        $email, $password, $role, $mobile, $fullName, 
        $primarySkill, $experience, $companyName, $recruiterName, $address
    ]);
    
    $userId = $db->lastInsertId();
    
    // Generate token
    $token = JWT::generateToken($email, $role, $userId);
    
    echo json_encode([
        'token' => $token,
        'role' => $role
    ]);
}

function login($db) {
    // Get raw input
    $rawInput = file_get_contents('php://input');
    $data = json_decode($rawInput, true);
    
    // Check if JSON parsing failed
    if (json_last_error() !== JSON_ERROR_NONE) {
        http_response_code(400);
        echo json_encode([
            'error' => 'Invalid JSON format',
            'details' => json_last_error_msg()
        ]);
        return;
    }
    
    // Validate required fields
    if (!isset($data['email']) || empty($data['email'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Email is required']);
        return;
    }
    
    if (!isset($data['password']) || empty($data['password'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Password is required']);
        return;
    }
    
    $email = trim($data['email']);
    $password = trim($data['password']);
    
    // Validate email format
    if (!filter_var($email, FILTER_VALIDATE_EMAIL) && strpos($email, '@') === false) {
        // Allow admin@134 format (not standard email but used in system)
        if (strpos($email, '@') === false) {
            http_response_code(400);
            echo json_encode(['error' => 'Invalid email format']);
            return;
        }
    }
    
    try {
        $stmt = $db->prepare("SELECT id, email, password, role FROM users WHERE email = ?");
        $stmt->execute([$email]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$user) {
            http_response_code(400);
            echo json_encode([
                'error' => 'User not found',
                'message' => 'No account found with email: ' . $email . '. Please check your email or create an account.'
            ]);
            return;
        }
        
        // Compare passwords (plain text comparison as per current system)
        if ($user['password'] !== $password) {
            http_response_code(400);
            echo json_encode([
                'error' => 'Invalid password',
                'message' => 'The password you entered is incorrect. Please try again.'
            ]);
            return;
        }
        
        // Generate token
        $token = JWT::generateToken($user['email'], $user['role'], $user['id']);
        
        if (!$token) {
            http_response_code(500);
            echo json_encode(['error' => 'Failed to generate authentication token']);
            return;
        }
        
        // Return success response
        http_response_code(200);
        echo json_encode([
            'token' => $token,
            'role' => $user['role'],
            'email' => $user['email'],
            'userId' => $user['id']
        ]);
        
    } catch (Exception $e) {
        http_response_code(500);
        echo json_encode([
            'error' => 'Database error',
            'message' => 'An error occurred during login. Please try again later.'
        ]);
        error_log('Login error: ' . $e->getMessage());
    }
}

function getUsers($db) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied']);
        return;
    }
    
    $stmt = $db->query("SELECT id, email, role, mobile, full_name, primary_skill, experience, company_name, recruiter_name, address FROM users");
    $users = $stmt->fetchAll();
    
    // Convert snake_case to camelCase
    $result = array_map(function($user) {
        return [
            'id' => $user['id'],
            'email' => $user['email'],
            'role' => $user['role'],
            'mobile' => $user['mobile'],
            'fullName' => $user['full_name'],
            'primarySkill' => $user['primary_skill'],
            'experience' => $user['experience'],
            'companyName' => $user['company_name'],
            'recruiterName' => $user['recruiter_name'],
            'address' => $user['address']
        ];
    }, $users);
    
    echo json_encode($result);
}

function updateRole($db, $userId) {
    $headers = getallheaders();
    $token = $headers['Authorization'] ?? '';
    
    if (empty($token)) {
        http_response_code(401);
        echo json_encode(['error' => 'Missing Authorization header']);
        return;
    }
    
    if (!JWT::isAdmin($token)) {
        http_response_code(403);
        echo json_encode(['error' => 'Access denied']);
        return;
    }
    
    $data = json_decode(file_get_contents('php://input'), true);
    
    if (!isset($data['role'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Role is required']);
        return;
    }
    
    $role = strtoupper($data['role']);
    if (!in_array($role, ['NORMAL', 'EMPLOYEE', 'ADMIN'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid role']);
        return;
    }
    
    // Check if user exists
    $stmt = $db->prepare("SELECT id FROM users WHERE id = ?");
    $stmt->execute([$userId]);
    if (!$stmt->fetch()) {
        http_response_code(404);
        echo json_encode(['error' => 'User not found']);
        return;
    }
    
    // Update role
    $stmt = $db->prepare("UPDATE users SET role = ? WHERE id = ?");
    $stmt->execute([$role, $userId]);
    
    echo json_encode(['message' => 'Role updated']);
}

