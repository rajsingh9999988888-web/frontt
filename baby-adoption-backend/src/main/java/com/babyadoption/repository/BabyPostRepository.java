package com.babyadoption.repository;

import com.babyadoption.model.BabyPost;
import com.babyadoption.model.BabyPost.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BabyPostRepository extends JpaRepository<BabyPost, Integer> {
    
    List<BabyPost> findByStatus(PostStatus status);
    
    List<BabyPost> findByUserId(Integer userId);
    
    List<BabyPost> findByStateAndDistrictAndCity(String state, String district, String city);
    
    List<BabyPost> findByState(String state);
    
    List<BabyPost> findByStateAndDistrict(String state, String district);
    
    Optional<BabyPost> findByIdAndStatus(Integer id, PostStatus status);
    
    // Dynamic city-based search method (case-insensitive)
    List<BabyPost> findByCityIgnoreCaseAndCategoryIgnoreCase(String city, String category);
}

