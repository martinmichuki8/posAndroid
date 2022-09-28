package com.michtech.pointofSale.pojo;

public class PojoTrend {
    String ProductName;
    int Quantity;
    int Id;

    public PojoTrend(int Id, String ProductName, int Quantity){
        this.Id = Id;
        this.ProductName = ProductName;
        this.Quantity = Quantity;

    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public String getProductName(){
        return ProductName;
    }
    public void setProductName(String ProductName){
        this.ProductName = ProductName;
    }
    public int getQuantity(){
        return Quantity;
    }
    public void setQuantity(int Quantity){
        this.Quantity = Quantity;
    }
}
