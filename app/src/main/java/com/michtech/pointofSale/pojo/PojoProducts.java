package com.michtech.pointofSale.pojo;

public class PojoProducts{

    private String Product;
    private String Category;
    private String DDate;
    private int Amount;
    private int Id;

    public PojoProducts(String Product, String Category, String DDate, int Amount, int Id){
        this.Product = Product;
        this.Category = Category;
        this.DDate = DDate;
        this.Amount = Amount;
        this.Id = Id;
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
    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category = Category;
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
}
