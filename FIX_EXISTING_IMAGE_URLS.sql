-- ============================================
-- Fix Existing Image URLs in Database
-- Run this in MySQL Workbench to update old production URLs to localhost
-- ============================================

USE fun;

-- Check current image URLs
SELECT id, name, image_url FROM baby_posts WHERE image_url LIKE '%onrender.com%';

-- Update all production URLs to localhost URLs
UPDATE baby_posts 
SET image_url = REPLACE(image_url, 'https://baby-adoption-backend.onrender.com', 'http://localhost:8082')
WHERE image_url LIKE '%baby-adoption-backend.onrender.com%';

-- Verify the update
SELECT id, name, image_url FROM baby_posts;

-- Count how many were updated
SELECT COUNT(*) as updated_count 
FROM baby_posts 
WHERE image_url LIKE '%localhost:8082%';

