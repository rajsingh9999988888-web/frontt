# ============================================
# FINAL BACKEND STARTUP SCRIPT
# Run this to start backend and see all errors
# ============================================

Clear-Host
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Baby Adoption Backend - Local Start" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop existing processes
Write-Host "🛑 Stopping existing Java processes..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL
Write-Host "🔍 Checking MySQL connection..." -ForegroundColor Yellow
$mysql3306 = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
$mysql3307 = Test-NetConnection -ComputerName localhost -Port 3307 -InformationLevel Quiet -WarningAction SilentlyContinue

if ($mysql3306) {
    Write-Host "✅ MySQL is running on port 3306" -ForegroundColor Green
    $mysqlPort = 3306
} elseif ($mysql3307) {
    Write-Host "✅ MySQL is running on port 3307 (XAMPP)" -ForegroundColor Green
    $mysqlPort = 3307
} else {
    Write-Host "❌ MySQL is NOT running!" -ForegroundColor Red
    Write-Host "   Please start MySQL first:" -ForegroundColor Yellow
    Write-Host "   - XAMPP: Start MySQL in XAMPP Control Panel" -ForegroundColor Yellow
    Write-Host "   - Standard: Start MySQL service" -ForegroundColor Yellow
    Read-Host "`nPress Enter to exit"
    exit
}

Write-Host ""

# Navigate to backend
if (-not (Test-Path "baby-adoption-backend")) {
    Write-Host "❌ Backend directory not found!" -ForegroundColor Red
    Write-Host "   Current directory: $(Get-Location)" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit
}

Set-Location "baby-adoption-backend"

# Set environment variables
Write-Host "⚙️  Setting database configuration..." -ForegroundColor Yellow
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:$mysqlPort/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
$env:SERVER_PORT = "8082"
$env:SPRING_PROFILES_ACTIVE = "local"

Write-Host "✅ Configuration:" -ForegroundColor Green
Write-Host "   Database URL: jdbc:mysql://localhost:$mysqlPort/fun" -ForegroundColor Cyan
Write-Host "   Username: root" -ForegroundColor Cyan
Write-Host "   Password: 9382019794" -ForegroundColor Cyan
Write-Host "   Server Port: 8082" -ForegroundColor Cyan
Write-Host "   Profile: local" -ForegroundColor Cyan
Write-Host ""

# Start backend
Write-Host "🚀 Starting Spring Boot backend..." -ForegroundColor Green
Write-Host "   Backend URL: http://localhost:8082" -ForegroundColor Cyan
Write-Host "   Watch for errors below!" -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Run Maven - this will show all output
mvn spring-boot:run

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Backend stopped." -ForegroundColor Yellow

