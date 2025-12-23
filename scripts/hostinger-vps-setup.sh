#!/bin/bash
# Hostinger VPS Initial Setup Script
# This script sets up the VPS for Baby Adoption Backend deployment

set -e

echo "🚀 Hostinger VPS Setup Script for Baby Adoption Backend"
echo "========================================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Update system
echo -e "${YELLOW}📦 Updating system packages...${NC}"
sudo apt update && sudo apt upgrade -y

# Install Java 17
echo -e "${YELLOW}☕ Installing Java 17...${NC}"
sudo apt install openjdk-17-jdk -y

# Verify Java installation
JAVA_VERSION=$(java -version 2>&1 | head -n 1)
echo -e "${GREEN}✅ Java installed: $JAVA_VERSION${NC}"

# Install MySQL Server
echo -e "${YELLOW}🗄️  Installing MySQL Server...${NC}"
sudo apt install mysql-server -y

# Start and enable MySQL
sudo systemctl start mysql
sudo systemctl enable mysql

echo -e "${GREEN}✅ MySQL installed and started${NC}"

# Install Nginx
echo -e "${YELLOW}🌐 Installing Nginx...${NC}"
sudo apt install nginx -y

# Start and enable Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

echo -e "${GREEN}✅ Nginx installed and started${NC}"

# Install essential tools
echo -e "${YELLOW}🛠️  Installing essential tools...${NC}"
sudo apt install -y curl wget git unzip htop

# Setup firewall
echo -e "${YELLOW}🔥 Configuring firewall...${NC}"
sudo ufw --force enable
sudo ufw allow OpenSSH
sudo ufw allow 'Nginx Full'

echo -e "${GREEN}✅ Firewall configured${NC}"

# Create application directory
echo -e "${YELLOW}📁 Creating application directory...${NC}"
sudo mkdir -p /var/www/baby-adoption
sudo mkdir -p /var/www/baby-adoption/uploads
sudo mkdir -p /var/www/baby-adoption/logs
sudo chown -R $USER:$USER /var/www/baby-adoption

echo -e "${GREEN}✅ Application directory created${NC}"

# Create logs directory
sudo mkdir -p /var/log/baby-adoption
sudo chown -R $USER:$USER /var/log/baby-adoption

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ VPS Setup Complete!${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo "📋 Next Steps:"
echo "1. Setup MySQL database:"
echo "   sudo mysql_secure_installation"
echo "   sudo mysql -u root -p"
echo ""
echo "2. Create database and user:"
echo "   CREATE DATABASE fun CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
echo "   CREATE USER 'babyapp'@'localhost' IDENTIFIED BY 'your_password';"
echo "   GRANT ALL PRIVILEGES ON fun.* TO 'babyapp'@'localhost';"
echo "   FLUSH PRIVILEGES;"
echo ""
echo "3. Upload your JAR file to /var/www/baby-adoption/"
echo ""
echo "4. Create .env file with environment variables"
echo ""
echo "5. Setup systemd service (see baby-adoption-backend.service)"
echo ""
echo "6. Configure Nginx (see nginx-baby-adoption.conf)"
echo ""

