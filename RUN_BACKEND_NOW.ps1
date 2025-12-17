# ============================================
# RUN THIS TO START THE BACKEND
# ============================================

Clear-Host
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Backend Server" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop any existing Java processes
Write-Host "Stopping existing processes..." -ForegroundColor Yellow
Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL
Write-Host "Checking MySQL..." -ForegroundColor Yellow
$mysql = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet -WarningAction SilentlyContinue
if (-not $mysql) {
    Write-Host "ERROR: MySQL is not running!" -ForegroundColor Red
    Write-Host "Please start MySQL first." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit
}
Write-Host "MySQL is running" -ForegroundColor Green
Write-Host ""

# Navigate to backend
Set-Location "baby-adoption-backend"

# Set profile
$env:SPRING_PROFILES_ACTIVE = "local"

Write-Host "Configuration:" -ForegroundColor Green
Write-Host "  Profile: local" -ForegroundColor Cyan
Write-Host "  Database: localhost:3306/fun" -ForegroundColor Cyan
Write-Host "  Username: root" -ForegroundColor Cyan
Write-Host "  Password: 9382019794" -ForegroundColor Cyan
Write-Host "  Port: 8082" -ForegroundColor Cyan
Write-Host ""

Write-Host "Starting Spring Boot..." -ForegroundColor Yellow
Write-Host "This will take 30-60 seconds..." -ForegroundColor Yellow
Write-Host "DO NOT CLOSE THIS WINDOW!" -ForegroundColor Red
Write-Host ""
Write-Host "Look for: 'Started BabyAdoptionBackendApplication'" -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Start backend - this will show all output
mvn spring-boot:run

