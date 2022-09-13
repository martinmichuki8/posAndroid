package com.michtech.pointofSale.functions;

import java.util.List;

public class DuplicateProducts {
    String ProductName;
    int Id;
    List<Integer> IdList;
    public DuplicateProducts(){}
    public DuplicateProducts(String ProductName, int Id, List<Integer> IdList){
        this.ProductName = ProductName;
        this.Id = Id;
        this.IdList = IdList;
    }
    public String getProductName(){
        return ProductName;
    }
    public void setProductName(String productName){
        this.ProductName = ProductName;
    }
    public int getId(){
        return Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }
    public List<Integer> getIdList(){
        return IdList;
    }
    public void setIdList(List<Integer> IdList){
        this.IdList  = IdList;
    }
}
