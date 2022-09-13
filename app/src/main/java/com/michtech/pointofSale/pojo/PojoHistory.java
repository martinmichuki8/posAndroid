package com.michtech.pointofSale.pojo;

public class PojoHistory{

    private String Product;
    private String Type;
    private String DDate;
    private int Amount;
    private int Id;
    private boolean newDate;
    private String Description;

    public PojoHistory(String Product, String Type, String DDate, int Amount, int Id, boolean newDate, String Description){
        this.Product = Product;
        this.Type = Type;
        this.DDate = DDate;
        this.Amount = Amount;
        this.Id = Id;
        this.newDate = newDate;
        this.Description = Description;
    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public String getProduct(){
        return Product;
    }
    public void setProduct(String Product){
        this.Product = Product;
    }
    public String getType(){
        return Type;
    }
    public void setType(String Type){
        this.Type = Type;
    }
    public String getDDate(){
        return DDate;
    }
    public void setDDate(String DDate){
        this.DDate = DDate;
    }
    public int getAmount(){
        return Amount;
    }
    public void setAmount(int Amount){
        this.Amount = Amount;
    }
    public boolean getNewDate(){
        return newDate;
    }
    public void setNewDate(boolean newDate){
        this.newDate = newDate;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
}
