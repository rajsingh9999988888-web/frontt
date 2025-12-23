#!/bin/bash
# Deployment script for Hostinger VPS
# Usage: ./hostinger-deploy.sh

set -e

APP_DIR="/var/www/baby-adoption"
SERVICE_NAME="baby-adoption-backend"
JAR_FILE="baby-adoption-backend-0.0.1-SNAPSHOT.jar"

echo "🚀 Deploying Baby Adoption Backend to Hostinger VPS"
echo "===================================================="
echo ""

# Check if JAR file exists
if [ ! -f "$APP_DIR/$JAR_FILE" ]; then
    echo "❌ Error: JAR file not found at $APP_DIR/$JAR_FILE"
    echo "Please upload the JAR file first."
    exit 1
fi

# Check if .env file exists
if [ ! -f "$APP_DIR/.env" ]; then
    echo "⚠️  Warning: .env file not found at $APP_DIR/.env"
    echo "Please create it with required environment variables."
    read -p "Continue anyway? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Backup old JAR if exists
if [ -f "$APP_DIR/$JAR_FILE.backup" ]; then
    rm "$APP_DIR/$JAR_FILE.backup"
fi

if [ -f "$APP_DIR/$JAR_FILE" ]; then
    cp "$APP_DIR/$JAR_FILE" "$APP_DIR/$JAR_FILE.backup"
    echo "✅ Old JAR backed up"
fi

# Stop the service
echo "🛑 Stopping service..."
sudo systemctl stop $SERVICE_NAME || true

# Wait a moment
sleep 2

# Start the service
echo "▶️  Starting service..."
sudo systemctl start $SERVICE_NAME

# Wait for service to start
sleep 5

# Check service status
if sudo systemctl is-active --quiet $SERVICE_NAME; then
    echo "✅ Service started successfully!"
    echo ""
    echo "📊 Service Status:"
    sudo systemctl status $SERVICE_NAME --no-pager -l
else
    echo "❌ Service failed to start!"
    echo ""
    echo "📋 Recent logs:"
    sudo journalctl -u $SERVICE_NAME -n 50 --no-pager
    exit 1
fi

echo ""
echo "✅ Deployment completed successfully!"
echo ""
echo "📋 Useful commands:"
echo "  View logs: sudo journalctl -u $SERVICE_NAME -f"
echo "  Restart:   sudo systemctl restart $SERVICE_NAME"
echo "  Status:    sudo systemctl status $SERVICE_NAME"
echo ""

