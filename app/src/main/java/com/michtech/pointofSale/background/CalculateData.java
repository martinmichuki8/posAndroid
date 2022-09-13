package com.michtech.pointofSale.background;

public class CalculateData {
    private int Amount;
    private int PurchasePerItem;
    public CalculateData(int Amount, int PurchasePricePerItem){
        this.Amount = Amount;
        this.PurchasePerItem = PurchasePricePerItem;
    }
    public int getAmount(){
        return Amount;
    }
    public void setAmount(int Amount){
        this.Amount = Amount;
    }
    public int getPurchasePerItem(){
        return PurchasePerItem;
    }
    public void setPurchasePerItem(int PurchasePricePerItem){
        this.PurchasePerItem = PurchasePricePerItem;
    }
}
