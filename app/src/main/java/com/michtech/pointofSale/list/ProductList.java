package com.michtech.pointofSale.list;

import com.michtech.pointofSale.adapter.ProductsAdapter;

public class ProductList {
    private int Id;
    private String Category;
    private String ProductName;
    private int Amount;
    private String Code;
    private String Description;
    private int PurchasePrice;
    private int SellingPrice;
    private String DDate;
    public ProductList(){}
    public ProductList(int Id, String Category, String Description, String ProductName, int Amount, String Code, int PurchasePrice, int SellingPrice, String DDate){
        this.Id = Id;
        this.Category = Category;
        this.ProductName = ProductName;
        this.Amount = Amount;
        this.DDate = DDate;
        this.Code = Code;
        this.PurchasePrice = PurchasePrice;
        this.SellingPrice = SellingPrice;
        this.Description = Description;
    }
    public int getId(){
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public String getDDate() {
        return DDate;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public int getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.PurchasePrice = purchasePrice;
    }

    public int getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.SellingPrice = sellingPrice;
    }

    public void setDDate(String DDate) {
        this.DDate = DDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
