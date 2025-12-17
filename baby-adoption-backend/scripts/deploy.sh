#!/bin/bash
# Deployment script for BabyAdopt backend
# Usage: ./scripts/deploy.sh

echo "🚀 BabyAdopt Backend Deployment Script"
echo "======================================"
echo ""

# Check Java and Maven
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17+"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found. Please install Maven"
    exit 1
fi

echo "📦 Building backend..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "📋 Next steps:"
    echo "1. Upload target/baby-adoption-backend-*.jar to your server"
    echo "2. Set environment variables:"
    echo "   - SPRING_PROFILES_ACTIVE=production"
    echo "   - DATABASE_URL=your_database_url"
    echo "   - BACKEND_BASE_URL=your_backend_url"
    echo "   - JWT_SECRET=your_secret_key"
    echo "3. Run: java -jar target/baby-adoption-backend-*.jar"
else
    echo "❌ Build failed. Please check errors above."
    exit 1
fi

