# Complete Backend Startup Script
# Run this script to start the backend with all correct settings

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Baby Adoption Backend" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop any existing Java processes
Write-Host "🛑 Stopping any existing Java processes..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL connection
Write-Host "🔍 Checking MySQL connection..." -ForegroundColor Yellow
$mysqlCheck = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
if ($mysqlCheck) {
    Write-Host "✅ MySQL is running on port 3306" -ForegroundColor Green
} else {
    Write-Host "❌ MySQL is NOT running on port 3306" -ForegroundColor Red
    Write-Host "   Please start MySQL first!" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "   For XAMPP: Start MySQL in XAMPP Control Panel" -ForegroundColor Yellow
    Write-Host "   For standard MySQL: Start MySQL service" -ForegroundColor Yellow
    exit
}

Write-Host ""

# Navigate to backend directory
Set-Location "baby-adoption-backend"

# Set all environment variables
Write-Host "⚙️  Setting database configuration..." -ForegroundColor Yellow
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
$env:SERVER_PORT = "8082"
$env:SPRING_PROFILES_ACTIVE = "local"

Write-Host "✅ Configuration:" -ForegroundColor Green
Write-Host "   Database URL: jdbc:mysql://localhost:3306/fun" -ForegroundColor Cyan
Write-Host "   Username: root" -ForegroundColor Cyan
Write-Host "   Password: 9382019794" -ForegroundColor Cyan
Write-Host "   Server Port: 8082" -ForegroundColor Cyan
Write-Host ""

# Start Spring Boot
Write-Host "🚀 Starting Spring Boot application..." -ForegroundColor Green
Write-Host "   Backend will be available at: http://localhost:8082" -ForegroundColor Cyan
Write-Host "   Press Ctrl+C to stop" -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

mvn spring-boot:run

