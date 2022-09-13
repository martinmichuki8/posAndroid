package com.michtech.pointofSale.pojo;

public class PojoPaymentHistory {

    private String Name;
    private String Number;
    private boolean found;

    public PojoPaymentHistory(){}
    public PojoPaymentHistory(String Name, String Number, boolean found){
        this.Name = Name;
        this.Number = Number;
        this.found = found;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getNumber(){
        return Number;
    }
    public void setNumber(String Number){
        this.Number = Number;
    }
    public boolean getFound(){
        return found;
    }
    public void setFound(boolean found){
        this.found = found;
    }
}
