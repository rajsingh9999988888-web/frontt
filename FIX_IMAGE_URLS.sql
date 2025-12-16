-- Fix Image URLs in Database
-- This script updates all image URLs from localhost to production URL

USE fun;

-- Check current URLs with localhost
SELECT id, name, image_url, status, createdAt 
FROM baby_posts 
WHERE image_url LIKE '%localhost%' OR image_url LIKE '%http://%'
ORDER BY createdAt DESC;

-- Update localhost URLs to production
UPDATE baby_posts 
SET image_url = REPLACE(image_url, 'http://localhost:8082', 'https://baby-adoption-backend.onrender.com')
WHERE image_url LIKE '%localhost:8082%';

-- Update any remaining HTTP URLs to HTTPS
UPDATE baby_posts 
SET image_url = REPLACE(image_url, 'http://', 'https://')
WHERE image_url LIKE 'http://%';

-- Verify the changes
SELECT id, name, image_url, status, createdAt 
FROM baby_posts 
ORDER BY createdAt DESC;

-- Count posts by status
SELECT status, COUNT(*) as count 
FROM baby_posts 
GROUP BY status;

