# Simple Backend Startup - Guaranteed to Work
# This script uses application-local.properties which has password hardcoded

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Backend" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop existing Java processes
Write-Host "Stopping existing Java processes..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL
Write-Host "Checking MySQL..." -ForegroundColor Yellow
$mysql = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
if (-not $mysql) {
    Write-Host "ERROR: MySQL is not running on port 3306!" -ForegroundColor Red
    Write-Host "Please start MySQL first." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit
}
Write-Host "MySQL is running" -ForegroundColor Green
Write-Host ""

# Navigate to backend
Set-Location "baby-adoption-backend"

# Set profile to local (this uses application-local.properties)
$env:SPRING_PROFILES_ACTIVE = "local"

Write-Host "Configuration:" -ForegroundColor Green
Write-Host "  Profile: local" -ForegroundColor Cyan
Write-Host "  Using: application-local.properties" -ForegroundColor Cyan
Write-Host "  Database: localhost:3306/fun" -ForegroundColor Cyan
Write-Host "  Username: root" -ForegroundColor Cyan
Write-Host "  Password: 9382019794" -ForegroundColor Cyan
Write-Host "  Port: 8082" -ForegroundColor Cyan
Write-Host ""

Write-Host "Starting Spring Boot..." -ForegroundColor Yellow
Write-Host "This will take 30-60 seconds..." -ForegroundColor Yellow
Write-Host "Look for: 'Started BabyAdoptionBackendApplication'" -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Start backend
mvn spring-boot:run

