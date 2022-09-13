package com.michtech.pointofSale.pojo;

public class PojoViewEmployee {
    int Id;
    private String Name;
    private String Email;
    private String Phone;
    public PojoViewEmployee(int Id, String Name, String Email, String Phone){
        this.Id = Id;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
    }
    public int Id(){
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
}
