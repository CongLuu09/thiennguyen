package com.kenhtao.site.thiennguyen.data.model;

public class Case {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String status;


    public Case(String description, String id, String imageUrl, String status, String title) {
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.status = status;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
