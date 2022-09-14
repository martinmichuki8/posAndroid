package com.michtech.pointofSale.functions;

import android.content.Context;

import androidx.annotation.NonNull;

import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.list.ProductList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Functions {

    private final Context context;

    List<Integer> ProductIds = new ArrayList<>();
    List<DuplicateProducts> duplicateProductsList;
    DatabaseManager db;

    public Functions(Context context){
        this.context = context;
    }

    public boolean checkMerge(){
        boolean found = false;
        if(ProductIds.size()>0){
            found = true;
        }
        return found;
    }
    @NonNull
    private List<String> duplicates(){
        db = new DatabaseManager(context);
        List<String> productsName, productsNameMerge, duplicates;
        duplicates = new ArrayList<>();
        productsName = db.getAllProductsName();
        productsNameMerge = productsName.stream().distinct().sorted().collect(Collectors.toList());
        int count = 0;
        for(String productNameMerge: productsNameMerge){
            for (String productName: productsName){
                if(productNameMerge.equals(productName)){
                    count++;
                }
            }
            if(count>1){
                duplicates.add(productNameMerge);
            }
            count = 0;
        }
        return duplicates;
    }
    private void checkDuplicateData(List<String> duplicates){
        duplicateProductsList = new ArrayList<>();
    }
    private void compareProductsData(String productName){
        boolean found = false;
        for(Integer productIds: db.getProductsId(productName)){
            for(Integer productId: db.getProductsId(productName)){
                //
            }
        }
    }
    private boolean compareProductsData(int id, int id2){
        db = new DatabaseManager(context);
        boolean match = false;
        for(ProductList productList: db.getSpecificProducts(id)){
            for(ProductList productList2: db.getSpecificProducts(id2)){
                if(productList.getProductName().equals(productList2.getProductName()) &&
                        productList.getCategory().equals(productList2.getCategory()) &&
                productList.getCode().equals(productList2.getCode()) &&
                        productList.getDescription().equals(productList2.getDescription()) &&
                productList.getPurchasePrice()==productList2.getPurchasePrice() && productList.getSellingPrice()==productList2.getSellingPrice()){

                    match = true;
                }
            }
        }
        return match;
    }
}
