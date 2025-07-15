package com.kenhtao.site.thiennguyen.data.model;

public class HelpRequest {
    private String id;
    private String title;
    private String description;
    private int imageResId;

    public HelpRequest(String id, String title,String description, int imageResId ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
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

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
