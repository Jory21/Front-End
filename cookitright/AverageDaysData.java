package com.example.cookitright.datamodels;

public class AverageDaysData {
    private String userId;
    private String cookId;
    private String days;

    public AverageDaysData() {

    }
    public AverageDaysData(String userId, String cookId, String days) {
        this.userId = userId;
        this.cookId = cookId;
        this.days = days;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
