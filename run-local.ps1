# Local Development Startup Script
# This script helps you run the backend and frontend locally

Write-Host "🚀 Starting Local Development Environment" -ForegroundColor Cyan
Write-Host ""

# Check if MySQL is running (basic check)
Write-Host "📋 Prerequisites Check:" -ForegroundColor Yellow
Write-Host "  - Make sure MySQL is running on port 3306 or 3307" -ForegroundColor Gray
Write-Host "  - Database 'fun' will be created automatically" -ForegroundColor Gray
Write-Host "  - MySQL Username: root" -ForegroundColor Gray
Write-Host "  - MySQL Password: 9382019794" -ForegroundColor Gray
Write-Host ""

# Ask which service to start
Write-Host "Which service would you like to start?" -ForegroundColor Yellow
Write-Host "  1. Backend only" -ForegroundColor White
Write-Host "  2. Frontend only" -ForegroundColor White
Write-Host "  3. Both (requires 2 terminals)" -ForegroundColor White
Write-Host ""
$choice = Read-Host "Enter choice (1-3)"

if ($choice -eq "1" -or $choice -eq "3") {
    Write-Host ""
    Write-Host "🔧 Starting Backend..." -ForegroundColor Green
    Write-Host "  Backend will run on: http://localhost:8082" -ForegroundColor Gray
    Write-Host ""
    Set-Location "baby-adoption-backend"
    mvn spring-boot:run -Dspring-boot.run.profiles=local
}

if ($choice -eq "2" -or $choice -eq "3") {
    Write-Host ""
    Write-Host "⚛️  Starting Frontend..." -ForegroundColor Green
    Write-Host "  Frontend will run on: http://localhost:5173" -ForegroundColor Gray
    Write-Host ""
    Set-Location "baby-adoption-website"
    
    # Check if node_modules exists
    if (-not (Test-Path "node_modules")) {
        Write-Host "📦 Installing dependencies..." -ForegroundColor Yellow
        npm install
    }
    
    npm run dev
}

