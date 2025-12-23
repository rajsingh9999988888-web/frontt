<?php
/**
 * JWT Token Handler
 * Simple JWT implementation for PHP
 */

class JWT {
    // Secret key - production mein environment variable se lo
    private static $secretKey;
    
    public static function init() {
        // Environment variable se secret key lo, nahi to default
        self::$secretKey = getenv('JWT_SECRET_KEY') ?: 'your-secret-key-change-this-in-production-very-important';
    }
    
    public static function generateToken($email, $role, $userId) {
        self::init();
        
        $header = json_encode(['typ' => 'JWT', 'alg' => 'HS256']);
        $payload = json_encode([
            'sub' => $email,
            'role' => $role,
            'userId' => $userId,
            'iat' => time(),
            'exp' => time() + 86400 // 1 day
        ]);
        
        $base64UrlHeader = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($header));
        $base64UrlPayload = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($payload));
        
        $signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, self::$secretKey, true);
        $base64UrlSignature = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));
        
        return $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;
    }
    
    public static function verifyToken($token) {
        self::init();
        
        if (empty($token)) {
            return null;
        }
        
        // Bearer token se "Bearer " remove karo
        $token = str_replace('Bearer ', '', $token);
        
        $parts = explode('.', $token);
        if (count($parts) !== 3) {
            return null;
        }
        
        list($base64UrlHeader, $base64UrlPayload, $base64UrlSignature) = $parts;
        
        // Signature verify karo
        $signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, self::$secretKey, true);
        $base64UrlSignatureCheck = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));
        
        if ($base64UrlSignature !== $base64UrlSignatureCheck) {
            return null;
        }
        
        // Payload decode karo
        $payload = json_decode(base64_decode(str_replace(['-', '_'], ['+', '/'], $base64UrlPayload)), true);
        
        // Expiration check karo
        if (isset($payload['exp']) && $payload['exp'] < time()) {
            return null;
        }
        
        return $payload;
    }
    
    public static function getUserIdFromToken($token) {
        $payload = self::verifyToken($token);
        return $payload ? ($payload['userId'] ?? -1) : -1;
    }
    
    public static function isAdmin($token) {
        $payload = self::verifyToken($token);
        return $payload && isset($payload['role']) && $payload['role'] === 'ADMIN';
    }
    
    public static function canAddPost($token) {
        $payload = self::verifyToken($token);
        if (!$payload || !isset($payload['role'])) {
            return false;
        }
        $role = $payload['role'];
        return $role === 'EMPLOYEE' || $role === 'ADMIN';
    }
}

