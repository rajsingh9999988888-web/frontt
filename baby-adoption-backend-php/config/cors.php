<?php
/**
 * CORS Configuration
 * Shared hosting ke liye CORS headers
 * FIXED: Always allows localhost for development
 */

// Prevent any output before headers
if (ob_get_level()) {
    ob_clean();
}

function handleCORS() {
    // Get origin from request - check multiple sources
    $origin = '';
    if (isset($_SERVER['HTTP_ORIGIN']) && !empty($_SERVER['HTTP_ORIGIN'])) {
        $origin = $_SERVER['HTTP_ORIGIN'];
    } elseif (isset($_SERVER['HTTP_REFERER']) && !empty($_SERVER['HTTP_REFERER'])) {
        $parsed = parse_url($_SERVER['HTTP_REFERER']);
        if ($parsed) {
            $origin = $parsed['scheme'] . '://' . $parsed['host'];
            if (isset($parsed['port'])) {
                $origin .= ':' . $parsed['port'];
            }
        }
    }
    
    // Allowed origins list
    $allowedOrigins = [
        'https://nightsaathi.com',
        'https://www.nightsaathi.com',
        'http://localhost:5173',
        'http://localhost:3000',
        'http://127.0.0.1:5173',
        'http://localhost:5174'
    ];
    
    // Determine allowed origin
    $allowedOrigin = null;
    
    // Check if origin is localhost (development) - always allow
    $isLocalhost = false;
    if ($origin) {
        $isLocalhost = (
            strpos($origin, 'localhost') !== false ||
            strpos($origin, '127.0.0.1') !== false ||
            strpos($origin, '0.0.0.0') !== false
        );
    }
    
    if ($origin && in_array($origin, $allowedOrigins)) {
        // Exact match in allowed list
        $allowedOrigin = $origin;
    } elseif ($isLocalhost) {
        // Always allow localhost for development
        $allowedOrigin = $origin;
    } elseif ($origin && (strpos($origin, 'nightsaathi.com') !== false)) {
        // Production domain - allow it
        $allowedOrigin = $origin;
    } else {
        // Fallback: allow the origin if provided, otherwise use first allowed
        $allowedOrigin = $origin ?: $allowedOrigins[0];
    }
    
    // Set CORS headers - MUST be before any output
    // Always set headers, even if origin is empty (for same-origin requests)
    if ($allowedOrigin && $allowedOrigin !== '*') {
        header("Access-Control-Allow-Origin: $allowedOrigin", true);
    } else {
        // For same-origin or when origin is not set
        header("Access-Control-Allow-Origin: *", true);
    }
    
    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH", true);
    header("Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With, Accept, Origin", true);
    header("Access-Control-Allow-Credentials: true", true);
    header("Access-Control-Max-Age: 3600", true);
    header("Access-Control-Expose-Headers: Content-Length, X-JSON", true);
    
    // Handle OPTIONS preflight request - MUST exit here
    if (isset($_SERVER['REQUEST_METHOD']) && $_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
        http_response_code(200);
        header('Content-Length: 0', true);
        exit(0);
    }
}

// Call CORS handler immediately
handleCORS();

