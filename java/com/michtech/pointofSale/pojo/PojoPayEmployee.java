package com.michtech.pointofSale.pojo;

public class PojoPayEmployee {
    private String Name;
    private String Phone;
    private boolean Paid;
    int Id;

    public PojoPayEmployee(String Name, String Phone, boolean Paid, int Id){
        this.Name = Name;
        this.Phone = Phone;
        this.Paid = Paid;
        this.Id = Id;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getPhone(){
        return Phone;
    }
    public void setPhone(String Phone){
        this.Phone = Phone;
    }
    public boolean getPaid(){
        return Paid;
    }
    public void setPaid(boolean Paid){
        this.Paid = Paid;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
}
