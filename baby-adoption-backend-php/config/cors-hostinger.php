<?php
/**
 * CORS Configuration - Hostinger Production
 * Production frontend domain ke liye optimized
 */

function handleCORS() {
    // Production frontend domains
    $allowedOrigins = [
        'https://yourdomain.com',           // Main domain
        'https://www.yourdomain.com',       // www subdomain
        'http://localhost:5173',            // Local development (remove in production)
        'http://localhost:3000'             // Local development (remove in production)
    ];
    
    // Environment variable se bhi le sakte hain
    $envOrigins = getenv('CORS_ALLOWED_ORIGINS');
    if ($envOrigins) {
        $allowedOrigins = array_merge($allowedOrigins, explode(',', $envOrigins));
    }
    
    $origin = $_SERVER['HTTP_ORIGIN'] ?? '';
    
    // Origin check karo
    if (in_array($origin, $allowedOrigins)) {
        header("Access-Control-Allow-Origin: $origin");
    } else if (!empty($allowedOrigins)) {
        // Default: first origin (production)
        header("Access-Control-Allow-Origin: " . $allowedOrigins[0]);
    } else {
        // Fallback: allow all (development only - production mein remove karo)
        header("Access-Control-Allow-Origin: *");
    }
    
    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
    header("Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With");
    header("Access-Control-Allow-Credentials: true");
    header("Access-Control-Max-Age: 3600");
    
    // Preflight request handle karo
    if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
        http_response_code(200);
        exit();
    }
}

// CORS headers set karo
handleCORS();
?>

