#!/bin/bash
# Deployment script for BabyAdopt website
# Usage: ./scripts/deploy.sh

echo "🚀 BabyAdopt Deployment Script"
echo "=============================="
echo ""

# Check if domain is set
if [ -z "$VITE_SITE_DOMAIN" ]; then
    echo "⚠️  VITE_SITE_DOMAIN not set"
    echo "Please set it in .env file or as environment variable"
    read -p "Enter your domain (e.g., yourdomain.com): " domain
    if [ -n "$domain" ]; then
        export VITE_SITE_DOMAIN="https://$domain"
        echo "✅ Domain set to: $VITE_SITE_DOMAIN"
    fi
fi

# Check if API URL is set
if [ -z "$VITE_API_BASE_URL" ]; then
    echo "⚠️  VITE_API_BASE_URL not set"
    read -p "Enter your backend URL (e.g., https://your-backend.onrender.com): " apiUrl
    if [ -n "$apiUrl" ]; then
        export VITE_API_BASE_URL="$apiUrl"
        echo "✅ API URL set to: $VITE_API_BASE_URL"
    fi
fi

echo ""
echo "📦 Building frontend..."
npm run build

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "📋 Next steps:"
    echo "1. Deploy the 'dist' folder to your hosting platform"
    echo "2. Set environment variables on your hosting platform:"
    echo "   - VITE_SITE_DOMAIN=$VITE_SITE_DOMAIN"
    echo "   - VITE_API_BASE_URL=$VITE_API_BASE_URL"
    echo "3. Test your deployment"
else
    echo "❌ Build failed. Please check errors above."
    exit 1
fi

