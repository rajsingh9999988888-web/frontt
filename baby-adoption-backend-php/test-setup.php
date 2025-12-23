<?php
/**
 * Setup Test File
 * Ye file run karke check karo ki sab kuch sahi setup hai ya nahi
 * Browser mein: http://localhost/baby-adoption-backend-php/test-setup.php
 */

header('Content-Type: text/html; charset=utf-8');
?>
<!DOCTYPE html>
<html>
<head>
    <title>PHP Backend Setup Test</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
        h1 { color: #333; }
        .test-item { margin: 15px 0; padding: 10px; border-left: 4px solid #ccc; background: #f9f9f9; }
        .success { border-left-color: #4CAF50; background: #e8f5e9; }
        .error { border-left-color: #f44336; background: #ffebee; }
        .warning { border-left-color: #ff9800; background: #fff3e0; }
        .info { border-left-color: #2196F3; background: #e3f2fd; }
        code { background: #f5f5f5; padding: 2px 6px; border-radius: 3px; }
        pre { background: #f5f5f5; padding: 10px; border-radius: 4px; overflow-x: auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔧 PHP Backend Setup Test</h1>
        
        <?php
        $allTestsPassed = true;
        
        // Test 1: PHP Version
        echo '<div class="test-item ' . (version_compare(PHP_VERSION, '7.4.0', '>=') ? 'success' : 'error') . '">';
        echo '<strong>Test 1: PHP Version</strong><br>';
        echo 'Current: <code>' . PHP_VERSION . '</code><br>';
        if (version_compare(PHP_VERSION, '7.4.0', '>=')) {
            echo '✅ PHP version is compatible';
        } else {
            echo '❌ PHP 7.4+ required';
            $allTestsPassed = false;
        }
        echo '</div>';
        
        // Test 2: Required Extensions
        $requiredExtensions = ['pdo', 'pdo_mysql', 'json', 'mbstring'];
        echo '<div class="test-item">';
        echo '<strong>Test 2: PHP Extensions</strong><br>';
        $missingExtensions = [];
        foreach ($requiredExtensions as $ext) {
            if (extension_loaded($ext)) {
                echo '✅ <code>' . $ext . '</code> loaded<br>';
            } else {
                echo '❌ <code>' . $ext . '</code> missing<br>';
                $missingExtensions[] = $ext;
                $allTestsPassed = false;
            }
        }
        echo '</div>';
        
        // Test 3: Database Connection
        echo '<div class="test-item">';
        echo '<strong>Test 3: Database Connection</strong><br>';
        try {
            require_once __DIR__ . '/config/database.php';
            $database = new Database();
            $conn = $database->getConnection();
            
            if ($conn) {
                echo '✅ Database connection successful<br>';
                
                // Test tables
                $tables = ['users', 'baby_posts'];
                foreach ($tables as $table) {
                    $stmt = $conn->query("SHOW TABLES LIKE '$table'");
                    if ($stmt->rowCount() > 0) {
                        echo '✅ Table <code>' . $table . '</code> exists<br>';
                    } else {
                        echo '❌ Table <code>' . $table . '</code> missing<br>';
                        $allTestsPassed = false;
                    }
                }
            } else {
                echo '❌ Database connection failed<br>';
                $allTestsPassed = false;
            }
        } catch (Exception $e) {
            echo '❌ Database error: ' . htmlspecialchars($e->getMessage()) . '<br>';
            $allTestsPassed = false;
        }
        echo '</div>';
        
        // Test 4: File Permissions
        echo '<div class="test-item">';
        echo '<strong>Test 4: File Permissions</strong><br>';
        $uploadsDir = __DIR__ . '/uploads';
        if (file_exists($uploadsDir)) {
            if (is_writable($uploadsDir)) {
                echo '✅ <code>uploads/</code> directory exists and is writable<br>';
            } else {
                echo '⚠️ <code>uploads/</code> directory exists but not writable<br>';
                echo 'Fix: chmod 755 uploads/<br>';
            }
        } else {
            echo '⚠️ <code>uploads/</code> directory missing<br>';
            echo 'Fix: Create uploads/ directory<br>';
            if (mkdir($uploadsDir, 0755, true)) {
                echo '✅ Created uploads/ directory<br>';
            } else {
                echo '❌ Failed to create uploads/ directory<br>';
                $allTestsPassed = false;
            }
        }
        echo '</div>';
        
        // Test 5: Configuration Files
        echo '<div class="test-item">';
        echo '<strong>Test 5: Configuration Files</strong><br>';
        $configFiles = [
            'config/database.php',
            'config/jwt.php',
            'config/cors.php',
            'config/location-data.php'
        ];
        foreach ($configFiles as $file) {
            $filePath = __DIR__ . '/' . $file;
            if (file_exists($filePath)) {
                echo '✅ <code>' . $file . '</code> exists<br>';
            } else {
                echo '❌ <code>' . $file . '</code> missing<br>';
                $allTestsPassed = false;
            }
        }
        echo '</div>';
        
        // Test 6: API Files
        echo '<div class="test-item">';
        echo '<strong>Test 6: API Files</strong><br>';
        $apiFiles = [
            'api/users.php',
            'api/babies.php',
            'api/search.php',
            'api/health.php',
            'index.php'
        ];
        foreach ($apiFiles as $file) {
            $filePath = __DIR__ . '/' . $file;
            if (file_exists($filePath)) {
                echo '✅ <code>' . $file . '</code> exists<br>';
            } else {
                echo '❌ <code>' . $file . '</code> missing<br>';
                $allTestsPassed = false;
            }
        }
        echo '</div>';
        
        // Test 7: .htaccess
        echo '<div class="test-item">';
        echo '<strong>Test 7: .htaccess File</strong><br>';
        if (file_exists(__DIR__ . '/.htaccess')) {
            echo '✅ <code>.htaccess</code> exists<br>';
        } else {
            echo '⚠️ <code>.htaccess</code> missing (may work without it)<br>';
        }
        echo '</div>';
        
        // Test 8: Location Data
        echo '<div class="test-item">';
        echo '<strong>Test 8: Location Data</strong><br>';
        try {
            require_once __DIR__ . '/config/location-data.php';
            $states = getStatesList();
            if (count($states) > 0) {
                echo '✅ States loaded: ' . count($states) . ' states<br>';
                
                $districts = getDistrictsMap();
                $totalDistricts = 0;
                foreach ($districts as $state => $stateDistricts) {
                    $totalDistricts += count($stateDistricts);
                }
                echo '✅ Districts loaded: ' . $totalDistricts . ' districts<br>';
                
                $cities = getCitiesMap();
                $totalCities = count($cities);
                echo '✅ Cities loaded: ' . $totalCities . ' city entries<br>';
            } else {
                echo '❌ No states found<br>';
                $allTestsPassed = false;
            }
        } catch (Exception $e) {
            echo '❌ Error loading location data: ' . htmlspecialchars($e->getMessage()) . '<br>';
            $allTestsPassed = false;
        }
        echo '</div>';
        
        // Final Result
        echo '<div class="test-item ' . ($allTestsPassed ? 'success' : 'error') . '">';
        echo '<strong>Final Result:</strong><br>';
        if ($allTestsPassed) {
            echo '✅ <strong>All tests passed! Backend is ready.</strong><br>';
            echo '<br>Next steps:<br>';
            echo '1. Test health endpoint: <a href="api/health.php" target="_blank">api/health.php</a><br>';
            echo '2. Test states endpoint: <a href="api/babies.php?path=states" target="_blank">api/babies.php?path=states</a><br>';
            echo '3. Connect your frontend using the API documentation<br>';
        } else {
            echo '❌ <strong>Some tests failed. Please fix the issues above.</strong><br>';
            echo '<br>Check <code>COMPLETE_SETUP_GUIDE.md</code> for detailed setup instructions.';
        }
        echo '</div>';
        
        // Quick Links
        echo '<div class="test-item info">';
        echo '<strong>Quick Test Links:</strong><br>';
        echo '<a href="api/health.php" target="_blank">Health Check</a> | ';
        echo '<a href="api/babies.php" target="_blank">Get Babies</a> | ';
        echo '<a href="api/babies.php?path=states" target="_blank">Get States</a><br>';
        echo '</div>';
        ?>
    </div>
</body>
</html>

