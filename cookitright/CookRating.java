package com.example.cookitright.datamodels;

public class CookRating {
    private String userId;
    private String cookId;
    private int rating;
    public CookRating(){}

    public CookRating(String userId, String cookId, int rating) {
        this.userId = userId;
        this.cookId = cookId;
        this.rating = rating;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
