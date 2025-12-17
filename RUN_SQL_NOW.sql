-- ============================================
-- Fix Image URLs - Run This in MySQL Workbench
-- ============================================

USE fun;

-- Step 1: Check current production URLs
SELECT id, name, image_url FROM baby_posts 
WHERE image_url LIKE '%onrender.com%';

-- Step 2: Update all production URLs to localhost
UPDATE baby_posts 
SET image_url = REPLACE(image_url, 'https://baby-adoption-backend.onrender.com', 'http://localhost:8082')
WHERE image_url LIKE '%baby-adoption-backend.onrender.com%';

-- Step 3: Verify all URLs are now localhost
SELECT id, name, image_url FROM baby_posts;

-- Step 4: Count updated rows
SELECT COUNT(*) as total_posts, 
       SUM(CASE WHEN image_url LIKE '%localhost:8082%' THEN 1 ELSE 0 END) as localhost_urls,
       SUM(CASE WHEN image_url LIKE '%onrender.com%' THEN 1 ELSE 0 END) as production_urls
FROM baby_posts;


