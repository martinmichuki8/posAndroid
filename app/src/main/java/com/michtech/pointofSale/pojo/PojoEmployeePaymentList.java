package com.michtech.pointofSale.pojo;

public class PojoEmployeePaymentList {
    private int id;
    private String DDate;
    private int Amount;
    String startDate;
    String endDate;
    public PojoEmployeePaymentList(int id, String DDate, int Amount, String startDate, String endDate){
        this.id = id;
        this.DDate = DDate;
        this.Amount = Amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getDDate(){
        return DDate;
    }
    public void setDDate(String DDate){
        this.DDate = DDate;
    }
    public int getAmount() {
        return Amount;
    }
    public void setAmount(int Amount) {
        this.Amount = Amount;
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
}
