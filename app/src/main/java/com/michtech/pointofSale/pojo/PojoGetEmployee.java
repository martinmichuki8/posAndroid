package com.michtech.pointofSale.pojo;

public class PojoGetEmployee {
    private String Name;
    private String Email;
    private String Phone;
    private String Bank;

    public PojoGetEmployee(String Name, String Email, String Phone, String Bank){
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Bank = Bank;
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
}
