package com.michtech.pointofSale.pojo;

public class PojoOtherExpense {
    String Type;
    String DDate;
    int Amount;
    int ImageCode;
    int Id;

    public  PojoOtherExpense(int Id, String Type, String DDate, int Amount, int ImageCode){
        this.Type = Type;
        this.DDate = DDate;
        this.Amount = Amount;
        this.ImageCode = ImageCode;
        this.Id = Id;
    }

    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
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
