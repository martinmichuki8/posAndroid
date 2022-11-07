package com.michtech.pointofSale.pojo;


public class PojoPricing {
    String ProductName;
    String Description;
    int Price;
    public PojoPricing(String ProductName, String Description, int Price){
        this.ProductName = ProductName;
        this.Description = Description;
        this.Price = Price;
    }
    public String getProductName(){
        return ProductName;
    }
    public void setProductName(String ProductName){
        this.ProductName = ProductName;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
    public int getPrice(){
        return Price;
    }
    public void setPrice(int Price){
        this.Price = Price;
    }
}
