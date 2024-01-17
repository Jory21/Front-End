package com.example.cookitright.datamodels;

public class CookData {


    private String cookName;
    private String chef;
    private String cookCategory;
    private String cookRecipe;
    private String cookCoverImage;
    private boolean isRead;
    private boolean isCurrent;
    private boolean isFinished;

    public CookData(String cookName, String chef, String cookCategory, String cookRecipe, String cookCoverImage, boolean isRead, boolean isCurrent, boolean isFinished) {
        this.cookName = cookName;
        this.chef = chef;
        this.cookCategory = cookCategory;
        this.cookRecipe = cookRecipe;
        this.cookCoverImage = cookCoverImage;
        this.isRead = isRead;
        this.isCurrent = isCurrent;
        this.isFinished = isFinished;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getCookCategory() {
        return cookCategory;
    }

    public void setCookCategory(String cookCategory) {
        this.cookCategory = cookCategory;
    }

    public String getCookRecipe() {
        return cookRecipe;
    }

    public void setCookRecipe(String cookRecipe) {
        this.cookRecipe = cookRecipe;
    }

    public String getCookCoverImage() {
        return cookCoverImage;
    }

    public void setCookCoverImage(String cookCoverImage) {
        this.cookCoverImage = cookCoverImage;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
