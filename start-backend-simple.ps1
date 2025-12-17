# Simple Backend Startup - Direct Configuration
# This bypasses profile loading issues by setting everything directly

Write-Host "🔧 Starting Backend Server..." -ForegroundColor Green
Write-Host "Backend will run on: http://localhost:8082" -ForegroundColor Cyan
Write-Host "MySQL: localhost:3306" -ForegroundColor Yellow
Write-Host "Database: fun" -ForegroundColor Yellow
Write-Host "Username: root" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press Ctrl+C to stop the server" -ForegroundColor Yellow
Write-Host ""

# Navigate to backend directory
Set-Location "baby-adoption-backend"

# Set all database properties directly via environment variables
# This ensures they are used even if profile loading fails
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"
$env:SERVER_PORT = "8082"

# Start Spring Boot
mvn spring-boot:run

