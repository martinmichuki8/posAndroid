package com.michtech.pointofSale.pojo;

import android.widget.Toast;

public class PojoShopping {
    private String Product;
    private String Category;
    private int TotalPrice;
    private int Amount;
    private int Id;

    public PojoShopping(String Product, String Category, int TotalPrice, int Amount, int Id){
        this.Product = Product;
        this.Category = Category;
        this.TotalPrice = TotalPrice;
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
    public int getTotalPrice(){
        return TotalPrice;
    }
    public void setDDate(int TotalPrice){
        this.TotalPrice = TotalPrice;
    }
    public int getAmount(){
        return Amount;
    }
    public void setAmount(int Amount){
        this.Amount = Amount;
    }
}
