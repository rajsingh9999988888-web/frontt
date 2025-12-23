<?php
/**
 * Test File - Debug babies.php issues
 */

error_reporting(E_ALL);
ini_set('display_errors', 1);

header('Content-Type: application/json');

echo json_encode([
    'step' => '1',
    'message' => 'PHP is working'
]);

// Test config files
try {
    echo "\n\nStep 2: Testing config files...\n";
    
    if (file_exists(__DIR__ . '/config/database.php')) {
        echo "✓ database.php exists\n";
        require_once __DIR__ . '/config/database.php';
    } else {
        echo "✗ database.php NOT found at: " . __DIR__ . '/config/database.php' . "\n";
    }
    
    if (file_exists(__DIR__ . '/config/location-data.php')) {
        echo "✓ location-data.php exists\n";
        require_once __DIR__ . '/config/location-data.php';
    } else {
        echo "✗ location-data.php NOT found\n";
    }
    
    echo "\nStep 3: Testing database connection...\n";
    $database = new Database();
    $db = $database->getConnection();
    echo "✓ Database connected\n";
    
    echo "\nStep 4: Testing getStatesList function...\n";
    if (function_exists('getStatesList')) {
        $states = getStatesList();
        echo "✓ getStatesList() works, found " . count($states) . " states\n";
    } else {
        echo "✗ getStatesList() function NOT found\n";
    }
    
} catch (Exception $e) {
    echo "\n✗ ERROR: " . $e->getMessage() . "\n";
    echo "File: " . $e->getFile() . "\n";
    echo "Line: " . $e->getLine() . "\n";
}

?>

