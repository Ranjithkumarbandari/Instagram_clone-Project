package com.my.instagram_clone.model;

import java.sql.Timestamp;

public class Photo {
    private int id;
    private String imageUrl;
    private String caption;
    private Timestamp createdAt;
    private String username; // uploader's username
    private int userId;
    private User user;
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // getters and setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    private boolean following;

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

}
