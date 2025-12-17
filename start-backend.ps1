# Start Backend Server with Local Profile
# This script starts the Spring Boot backend with local MySQL configuration

Write-Host "🔧 Starting Backend Server..." -ForegroundColor Green
Write-Host "Backend will run on: http://localhost:8082" -ForegroundColor Cyan
Write-Host "MySQL: localhost:3306" -ForegroundColor Yellow
Write-Host "Profile: local" -ForegroundColor Yellow
Write-Host ""
Write-Host "Press Ctrl+C to stop the server" -ForegroundColor Yellow
Write-Host ""

# Navigate to backend directory
Set-Location "baby-adoption-backend"

# Start Spring Boot with local profile
# Set profile and database properties as environment variables (most reliable method)
$env:SPRING_PROFILES_ACTIVE = "local"
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/fun?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "9382019794"
mvn spring-boot:run

