package com.babyadoption.controller;

import com.babyadoption.model.BabyPost;
import com.babyadoption.model.BabyPost.PostStatus;
import com.babyadoption.repository.BabyPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Search Controller for city-based dynamic search
 * Handles routes like: /api/search/{city}/{category}
 */
@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private final BabyPostRepository babyPostRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    public SearchController(BabyPostRepository babyPostRepository) {
        this.babyPostRepository = babyPostRepository;
    }

    /**
     * Dynamic city-based search endpoint
     * Example: /api/search/bhopal/call-girls
     * Returns all posts matching the city and category (case-insensitive)
     */
    @GetMapping("/{city}/{category}")
    public ResponseEntity<List<BabyPost>> searchByCityAndCategory(
            @PathVariable String city,
            @PathVariable String category) {
        
        logger.info("Search request - City: {}, Category: {}", city, category);
        
        // Normalize category: convert slug to actual category name
        // e.g., "call-girls" -> "Call Girls", "call-girl" -> "Call Girls"
        String normalizedCategory = normalizeCategory(category);
        
        // Query database (case-insensitive search)
        List<BabyPost> posts = babyPostRepository.findByCityIgnoreCaseAndCategoryIgnoreCase(
                city, normalizedCategory);
        
        // Filter to show only APPROVED posts in production, all in local dev
        String activeProfile = System.getProperty("spring.profiles.active", "");
        boolean isLocalDev = "local".equals(activeProfile) || activeProfile.contains("local");
        
        List<BabyPost> filteredPosts = posts.stream()
                .filter(post -> isLocalDev || post.getStatus() == PostStatus.APPROVED)
                .collect(Collectors.toList());
        
        logger.info("Found {} posts for city: {}, category: {}", 
                filteredPosts.size(), city, normalizedCategory);
        
        // Set response headers for SEO and tracking
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Search-City", city);
        headers.add("X-Search-Category", normalizedCategory);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(filteredPosts);
    }
    
    /**
     * Normalize category slug to actual category name
     * Examples:
     * - "call-girls" or "call-girl" -> "Call Girls"
     * - "massage" -> "Massage"
     * - "male-escorts" -> "Male Escorts"
     */
    private String normalizeCategory(String categorySlug) {
        if (categorySlug == null || categorySlug.isEmpty()) {
            return categorySlug;
        }
        
        // Convert slug to readable format
        String normalized = categorySlug.toLowerCase()
                .replace("-", " ")
                .replace("_", " ");
        
        // Capitalize first letter of each word
        String[] words = normalized.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    result.append(words[i].substring(1));
                }
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        
        // Handle common variations
        String resultStr = result.toString();
        if (resultStr.contains("Call Girl")) {
            return "Call Girls";
        }
        if (resultStr.contains("Male Escort")) {
            return "Male Escorts";
        }
        
        return resultStr;
    }
}

