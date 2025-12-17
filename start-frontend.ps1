# Start Frontend Development Server
# This script starts the Vite frontend development server

Write-Host "⚛️  Starting Frontend Server..." -ForegroundColor Green
Write-Host "Frontend will run on: http://localhost:5173" -ForegroundColor Cyan
Write-Host "API: http://localhost:8082" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press Ctrl+C to stop the server" -ForegroundColor Yellow
Write-Host ""

# Navigate to frontend directory
Set-Location "baby-adoption-website"

# Check if node_modules exists
if (-not (Test-Path "node_modules")) {
    Write-Host "📦 Installing dependencies (first time only)..." -ForegroundColor Yellow
    npm install
    Write-Host ""
}

# Start Vite dev server
npm run dev

