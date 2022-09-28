package com.michtech.pointofSale.database;

import android.media.Image;

import java.util.Date;

public class DbHelper {
    private int Id;
    private String Name;
    private String Email;
    private String Phone;
    private String Bank;
    private String DDate;
    private String Paid;
    private String Category;
    private int PurchasePrice;
    private int SellingPrice;
    private String ProductsName;
    private String Description;
    private int Amount;
    private int CategoryId;
    private int PriceId;
    private String Code;
    private int TotalPrice;
    private int ProductId;
    private String PurchaseOrSale;
    private int ImageCode;
    private String Type;
    private Date date;
    private boolean setDate;
    private String startDate;
    private String endDate;
    private String Quantity;

    public DbHelper(){}
    public DbHelper(int Id, String Name, String Email, String Phone, String Bank, String DDate, String Paid,
                    String Category, int PurchasePrice, int SellingPrice, String ProductName, String Description, int Amount, int CategoryId, int PriceId, String Code,
                    int TotalPrice, int ProductId, String PurchaseOrSale, int ImageCode, String Type, Date date, boolean setDate, String startDate, String endDate,
                    String Quantity){
        this.Id = Id;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Bank = Bank;
        this.DDate = DDate;
        this.Paid = Paid;
        this.Category = Category;
        this.PurchasePrice = PurchasePrice;
        this.SellingPrice = SellingPrice;
        this.ProductsName = ProductName;
        this.Description = Description;
        this.Amount = Amount;
        this.CategoryId = CategoryId;
        this.PriceId = PriceId;
        this.Code = Code;
        this.TotalPrice = TotalPrice;
        this.ProductId = ProductId;
        this.PurchaseOrSale = PurchaseOrSale;
        this.Description = Description;
        this.Code = Code;
        this.setDate = setDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Quantity = Quantity;
    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getEmail(){
        return Email;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public String getPhone(){
        return Phone;
    }
    public void setPhone(String Phone){
        this.Phone = Phone;
    }
    public String getBank(){
        return Bank;
    }
    public void setBank(String Bank){
        this.Bank = Bank;
    }
    public String getPaid(){
        return Paid;
    }
    public void setPaid(String Paid){
        this.Paid = Paid;
    }
    public String getDDate(){
        return DDate;
    }
    public void setDDate(String DDate){
        this.DDate = DDate;
    }
    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getProductsName(){
        return ProductsName;
    }
    public void setProductsName(String ProductsName){
        this.ProductsName = ProductsName;
    }
    public int getAmount(){
        return Amount;
    }
    public void setAmount(int Amount){
        this.Amount = Amount;
    }
    public int getTotalPrice(){
        return TotalPrice;
    }
    public void setTotalPrice(int TotalPrice){
        this.TotalPrice = TotalPrice;
    }
    public int getProductId(){
        return ProductId;
    }
    public void setProductId(int ProductId){
        this.ProductId = ProductId;
    }
    public String getPurchaseOrSale(){
        return PurchaseOrSale;
    }
    public void setPurchaseOrSale(String PurchaseOrSale){
        this.PurchaseOrSale = PurchaseOrSale;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
    public int getPurchasePrice(){
        return PurchasePrice;
    }
    public void setPurchasePrice(int PurchasePrice){
        this.PurchasePrice = PurchasePrice;
    }
    public int getSellingPrice(){
        return SellingPrice;
    }
    public void setSellingPrice(int SellingPrice){
        this.SellingPrice = SellingPrice;
    }
    public String getCode(){
        return Code;
    }
    public void setCode(String Code){
        this.Code = Code;
    }
    public int getImageCode(){
        return ImageCode;
    }
    public void setImageCode(int ImageCode){
        this.ImageCode = ImageCode;
    }
    public String getType(){
        return Type;
    }
    public void setType(String Type){
        this.Type = Type;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public boolean getSetDate(){
        return setDate;
    }
    public void setSetDate(boolean setDate){
        this.setDate = setDate;
    }
    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }
    public String getQuantity(){
        return Quantity;
    }
    public void setQuantity(String Quantity){
        this.Quantity = Quantity;
    }
}
