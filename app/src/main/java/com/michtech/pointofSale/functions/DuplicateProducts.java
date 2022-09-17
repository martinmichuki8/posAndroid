package com.michtech.pointofSale.functions;

import java.util.List;

public class DuplicateProducts {
    String ProductName;
    String Category;
    int Amount;
    int Id;
    List<Integer> IdList;
    public DuplicateProducts(){}
    public DuplicateProducts(String ProductName, String Category, int Amount, int Id, List<Integer> IdList){
        this.ProductName = ProductName;
        this.Id = Id;
        this.IdList = IdList;
        this.Category = Category;
        this.Amount = Amount;
    }
    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getProductName(){
        return ProductName;
    }
    public void setProductName(String ProductName){
        this.ProductName = ProductName;
    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public List<Integer> getIdList(){
        return IdList;
    }
    public void setIdList(List<Integer> IdList){
        this.IdList  = IdList;
    }
    public int getAmount(){
        return Amount;
    }
    public void setAmount(int Amount){
        this.Amount = Amount;
    }
}
