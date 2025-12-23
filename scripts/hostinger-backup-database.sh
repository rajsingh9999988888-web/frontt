#!/bin/bash
# Database Backup Script for Hostinger VPS
# This script creates a backup of the MySQL database

set -e

# Configuration
DB_NAME="fun"
DB_USER="babyapp"
BACKUP_DIR="/var/www/baby-adoption/backups"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/${DB_NAME}_backup_$DATE.sql"

# Create backup directory if it doesn't exist
mkdir -p $BACKUP_DIR

echo "🗄️  Creating database backup..."
echo "Database: $DB_NAME"
echo "Backup file: $BACKUP_FILE"

# Create backup
mysqldump -u $DB_USER -p$DB_PASSWORD $DB_NAME > $BACKUP_FILE

# Compress backup
gzip $BACKUP_FILE
BACKUP_FILE="${BACKUP_FILE}.gz"

echo "✅ Backup created: $BACKUP_FILE"

# Keep only last 7 days of backups
echo "🧹 Cleaning old backups (keeping last 7 days)..."
find $BACKUP_DIR -name "${DB_NAME}_backup_*.sql.gz" -mtime +7 -delete

echo "✅ Backup process completed!"

# Optional: Upload to remote storage (uncomment if needed)
# scp $BACKUP_FILE user@remote-server:/backups/

