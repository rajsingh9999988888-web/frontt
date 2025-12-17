# ========================================
# RUN THIS SCRIPT TO START THE BACKEND
# ========================================

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Baby Adoption Backend Startup" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop any existing processes
Write-Host "🛑 Stopping existing Java processes..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL
Write-Host "🔍 Checking MySQL..." -ForegroundColor Yellow
$mysql = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
if ($mysql) {
    Write-Host "✅ MySQL is running on port 3306" -ForegroundColor Green
} else {
    Write-Host "❌ MySQL is NOT running!" -ForegroundColor Red
    Write-Host "   Please start MySQL first (XAMPP or MySQL service)" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit
}

Write-Host ""

# Navigate to backend
Set-Location "baby-adoption-backend"

# Set environment variables
Write-Host "⚙️  Configuring database connection..." -ForegroundColor Yellow
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
$env:SERVER_PORT = "8082"

Write-Host "✅ Configuration set:" -ForegroundColor Green
Write-Host "   Database: localhost:3306/fun" -ForegroundColor Cyan
Write-Host "   Username: root" -ForegroundColor Cyan
Write-Host "   Password: 9382019794" -ForegroundColor Cyan
Write-Host "   Port: 8082" -ForegroundColor Cyan
Write-Host ""

# Start backend
Write-Host "🚀 Starting backend..." -ForegroundColor Green
Write-Host "   Watch for errors below!" -ForegroundColor Yellow
Write-Host "   Backend URL: http://localhost:8082" -ForegroundColor Cyan
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Run Maven - this will show all output including errors
mvn spring-boot:run

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Backend stopped." -ForegroundColor Yellow

