-- ============================================
-- Database Check Script
-- Run these commands in MySQL Workbench
-- ============================================

-- 1. Check if database exists
SHOW DATABASES;

-- 2. Use the fun database
USE fun;

-- 3. Show all tables
SHOW TABLES;

-- 4. If tables exist, check their structure
DESCRIBE baby_posts;
DESCRIBE users;

-- 5. Check if there's any data
SELECT COUNT(*) as total_posts FROM baby_posts;
SELECT COUNT(*) as total_users FROM users;

-- 6. View all posts
SELECT * FROM baby_posts;

-- 7. View all users
SELECT * FROM users;

-- 8. Check table creation time (if available)
SHOW TABLE STATUS FROM fun;

