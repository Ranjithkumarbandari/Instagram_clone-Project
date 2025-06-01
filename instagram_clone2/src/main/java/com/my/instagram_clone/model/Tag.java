package com.my.instagram_clone.model;

import java.sql.Timestamp;

public class Tag {
    private int id;
    private String tagName;
    private Timestamp createdAt;

    public Tag() {}

    public Tag(int id, String tagName, Timestamp createdAt) {
        this.id = id;
        this.tagName = tagName;
        this.createdAt = createdAt;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
