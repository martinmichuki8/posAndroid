package com.michtech.pointofSale.pojo;

public class PojoDevices {
    private String DeviceUser;
    private int Id;
    public PojoDevices(String DeviceUser, int Id){
        this.DeviceUser = DeviceUser;
        this.Id = Id;
    }
    public String getDeviceUser(){
        return DeviceUser;
    }
    public void setDeviceUser(String DeviceUser){
        this.DeviceUser = DeviceUser;
    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
}
