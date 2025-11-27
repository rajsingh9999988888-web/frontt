package com.babyadoption.model;

import java.time.LocalDateTime;

public class BabyPost {
    private int id;
    private String name;
    private String description;
    private String phone;
    private String whatsapp;
    private String imageUrl;
    private String state;
    private String district;
    private String city;
    private String category;
    private String address;
    private String postalcode;
    private int age;
    private String nickname;
    private String title;
    private String text;
    private String ethnicity;
    private String nationality;
    private String bodytype;
    private String services;
    private String place;
    private LocalDateTime createdAt;
    private PostStatus status;
    private int userId;

    public enum PostStatus {
        PENDING, APPROVED, EXPIRED
    }

    public BabyPost(int id, String name, String description, String phone, String whatsapp, String imageUrl,
            String state, String district, String city, String category, String address, String postalcode, int age,
            String nickname, String title, String text, String ethnicity, String nationality, String bodytype,
            String services, String place, LocalDateTime createdAt, PostStatus status, int userId) {
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
    public int getId() {
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

    public int getAge() {
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
    public void setId(int id) {
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

    public void setAge(int age) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
