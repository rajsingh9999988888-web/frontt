package com.babyadoption.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "baby_posts")
public class BabyPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 20)
    private String whatsapp;
    
    @Column(length = 500)
    private String imageUrl;
    
    @Column(length = 100)
    private String state;
    
    @Column(length = 100)
    private String district;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 100)
    private String category;
    
    @Column(length = 500)
    private String address;
    
    @Column(length = 20)
    private String postalcode;
    
    private Integer age;
    
    @Column(length = 100)
    private String nickname;
    
    @Column(length = 255)
    private String title;
    
    @Column(length = 2000)
    private String text;
    
    @Column(length = 100)
    private String ethnicity;
    
    @Column(length = 100)
    private String nationality;
    
    @Column(length = 100)
    private String bodytype;
    
    @Column(length = 500)
    private String services;
    
    @Column(length = 500)
    private String place;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostStatus status;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public enum PostStatus {
        PENDING, APPROVED, EXPIRED
    }

    public BabyPost(Integer id, String name, String description, String phone, String whatsapp, String imageUrl,
            String state, String district, String city, String category, String address, String postalcode, Integer age,
            String nickname, String title, String text, String ethnicity, String nationality, String bodytype,
            String services, String place, LocalDateTime createdAt, PostStatus status, Integer userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.imageUrl = imageUrl;
        this.state = state;
        this.district = district;
        this.city = city;
        this.category = category;
        this.address = address;
        this.postalcode = postalcode;
        this.age = age;
        this.nickname = nickname;
        this.title = title;
        this.text = text;
        this.ethnicity = ethnicity;
        this.nationality = nationality;
        this.bodytype = bodytype;
        this.services = services;
        this.place = place;
        this.createdAt = createdAt;
        this.status = status;
        this.userId = userId;
    }

    // Default constructor
    public BabyPost() {
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public Integer getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBodytype() {
        return bodytype;
    }

    public String getServices() {
        return services;
    }

    public String getPlace() {
        return place;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
