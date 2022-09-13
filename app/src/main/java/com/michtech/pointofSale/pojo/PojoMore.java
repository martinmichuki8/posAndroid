package com.michtech.pointofSale.pojo;

public class PojoMore {
    private int Image;
    private String Title;
    private String Function;

    public PojoMore(int Image, String Title, String Function){
        this.Image = Image;
        this.Title = Title;
        this.Function = Function;
    }
    public int getImage(){
        return Image;
    }
    public void setImage(int Image){
        this.Image = Image;
    }
    public String getTitle(){
        return Title;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }
    public String getFunction(){
        return Function;
    }
    public void setFunction(String Function){
        this.Function = Function;
    }
}
