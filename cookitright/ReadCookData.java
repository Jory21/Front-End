
package com.example.cookitright.datamodels;

public class ReadCookData {

    private String cookId;
    private String cookName;
    private String chef;
    private String cookRecipe;
    private String cookCoverImage;

    public ReadCookData(String cookId, String cookName, String chef, String cookRecipe, String cookCoverImage){
        this.cookId=cookId;
        this.cookName=cookName;
        this.chef=chef;
        this.cookRecipe=cookRecipe;
        this.cookCoverImage=cookCoverImage;
    }
    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
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

}