<?php
/**
 * Frontend Configuration
 * Frontend connection ke liye settings
 */

// Frontend URLs - environment variable se lo ya default
$frontendUrls = [
    'http://localhost:5173',           // Local development (Vite)
    'http://localhost:3000',           // Local development (React)
    'http://localhost:5174',           // Alternative local port
    'https://your-frontend-domain.com' // Production frontend URL - yahan apna domain add karo
];

// Backend base URL
$backendBaseUrl = getenv('BACKEND_BASE_URL') ?: 'http://localhost';

// CORS allowed origins
$corsAllowedOrigins = getenv('CORS_ALLOWED_ORIGINS') ?: implode(',', $frontendUrls);

// API base path
$apiBasePath = '/api';

// Return configuration
return [
    'frontendUrls' => $frontendUrls,
    'backendBaseUrl' => $backendBaseUrl,
    'corsAllowedOrigins' => $corsAllowedOrigins,
    'apiBasePath' => $apiBasePath
];

