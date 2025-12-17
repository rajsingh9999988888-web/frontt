# ============================================
# START BOTH BACKEND AND FRONTEND
# ============================================

Clear-Host
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Baby Adoption Application" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Stop existing processes
Write-Host "🛑 Stopping existing processes..." -ForegroundColor Yellow
Get-Process -Name java,node -ErrorAction SilentlyContinue | Stop-Process -Force
Start-Sleep -Seconds 2

# Check MySQL
Write-Host "🔍 Checking MySQL..." -ForegroundColor Yellow
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
    Write-Host "   Please start MySQL first!" -ForegroundColor Yellow
    exit
}

Write-Host ""

# ============================================
# START BACKEND
# ============================================
Write-Host "🔧 Starting Backend..." -ForegroundColor Green
Write-Host ""

if (-not (Test-Path "baby-adoption-backend")) {
    Write-Host "❌ Backend directory not found!" -ForegroundColor Red
    exit
}

Set-Location "baby-adoption-backend"

# Set environment variables
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:$mysqlPort/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
$env:SERVER_PORT = "8082"

Write-Host "✅ Backend Configuration:" -ForegroundColor Green
Write-Host "   Database: localhost:$mysqlPort/fun" -ForegroundColor Cyan
Write-Host "   Username: root" -ForegroundColor Cyan
Write-Host "   Port: 8082" -ForegroundColor Cyan
Write-Host ""

Write-Host "🚀 Starting Spring Boot (this will take a moment)..." -ForegroundColor Yellow
Write-Host "   Backend URL: http://localhost:8082" -ForegroundColor Cyan
Write-Host ""

# Start backend in background
$backendJob = Start-Job -ScriptBlock {
    Set-Location $using:PWD
    $env:SPRING_DATASOURCE_URL = $using:env:SPRING_DATASOURCE_URL
    $env:SPRING_DATASOURCE_USERNAME = $using:env:SPRING_DATASOURCE_USERNAME
    $env:SPRING_DATASOURCE_PASSWORD = $using:env:SPRING_DATASOURCE_PASSWORD
    $env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = $using:env:SPRING_DATASOURCE_DRIVER_CLASS_NAME
    $env:SERVER_PORT = $using:env:SERVER_PORT
    cd baby-adoption-backend
    mvn spring-boot:run 2>&1
}

Write-Host "⏳ Waiting for backend to start (30 seconds)..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Check backend
$backendRunning = Test-NetConnection -ComputerName localhost -Port 8082 -InformationLevel Quiet -WarningAction SilentlyContinue
if ($backendRunning) {
    Write-Host "✅ Backend is RUNNING on http://localhost:8082" -ForegroundColor Green
} else {
    Write-Host "⚠️  Backend may still be starting or encountered an error" -ForegroundColor Yellow
    Write-Host "   Check backend logs in the job output" -ForegroundColor Yellow
}

Write-Host ""

# ============================================
# START FRONTEND
# ============================================
Set-Location ".."
Write-Host "⚛️  Starting Frontend..." -ForegroundColor Green
Write-Host ""

if (-not (Test-Path "baby-adoption-website")) {
    Write-Host "❌ Frontend directory not found!" -ForegroundColor Red
    exit
}

Set-Location "baby-adoption-website"

# Check if node_modules exists
if (-not (Test-Path "node_modules")) {
    Write-Host "📦 Installing frontend dependencies (first time)..." -ForegroundColor Yellow
    npm install
    Write-Host ""
}

Write-Host "🚀 Starting Vite dev server..." -ForegroundColor Yellow
Write-Host "   Frontend URL: http://localhost:5173" -ForegroundColor Cyan
Write-Host ""

# Start frontend
npm run dev

