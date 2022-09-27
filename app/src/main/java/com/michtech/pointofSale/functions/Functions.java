package com.michtech.pointofSale.functions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.duplicates.DuplicateProductsList;
import com.michtech.pointofSale.list.ProductList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Functions {

    private final String folder_main = "Pos";

    private final Activity activity;

    List<Integer> ProductIds = new ArrayList<>();
    List<DuplicateProducts> duplicateProductsList;
    DatabaseManager db;

    public Functions(Activity activity){
        this.activity = activity;
    }

    public boolean checkMerge(){
        boolean found = false;
        for(String productName: duplicates()){
            compareProductsData(productName, false);
        }
        if(ProductIds.size()>0){
            found = true;
        }
        return found;
    }
    public List<DuplicateProducts> getDuplicates(){
        duplicateProductsList = new ArrayList<>();
        for(String productName: duplicates()){
            compareProductsData(productName, true);
        }
        return duplicateProductsList;
    }
    @NonNull
    private List<String> duplicates(){
        db = new DatabaseManager(activity);
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
    private void compareProductsData(String productName, boolean ViewData){
        if(ViewData){
            ProductIds = new ArrayList<>();
        }
        for(Integer productIds: db.getProductsId(productName)){
            for(Integer productId: db.getProductsId(productName)){
                if(productId!=productIds){
                    if(compareProductsData(productId, productIds)){
                        ProductIds.add(productId);
                        ProductIds.add(productIds);
                    }
                }
            }
        }
        if(ViewData){
            DuplicateProducts duplicateProducts = new DuplicateProducts();

            duplicateProducts.setIdList(ProductIds.stream().distinct().sorted().collect(Collectors.toList()));
            duplicateProducts.setProductName(productName);

            duplicateProductsList.add(duplicateProducts);
        }
    }
    private boolean compareProductsData(int id, int id2){
        db = new DatabaseManager(activity);
        boolean match = false;
        for(ProductList productList: db.getSpecificProducts(id)){
            for(ProductList productList2: db.getSpecificProducts(id2)){
                if(productList.getProductName().equals(productList2.getProductName()) &&
                        productList.getCategory().equals(productList2.getCategory()) &&
                productList.getCode().equals(productList2.getCode()) &&
                        productList.getDescription().equals(productList2.getDescription()) &&
                productList.getPurchasePrice()==productList2.getPurchasePrice() && productList.getSellingPrice()==productList2.getSellingPrice()){
                    //match = true;
                    if(db.searchExpiryExists(id) || db.searchExpiryExists(id2)){
                        if(db.getExpiryDate(id).equals(db.getExpiryDate(id2))){
                            match = true;
                        }
                    }else{
                        match = true;
                    }
                }
            }
        }
        return match;
    }
    public void CreateDirectory(){
        File file = new File(Environment.getExternalStorageDirectory(), folder_main);
        if(!file.exists()){
            file.mkdir();
        }
    }
    public void checkPermission(String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }
    @NonNull
    public String addComma(int price){
        String total = Integer.toString(price);
        int size = total.length()+total.length()/3;
        char t[] = new char[size+2];
        int c=size+1;
        int n=1;
        for(int i=total.length()-1; i>-1; i--){
            t[c] = total.charAt(i);
            c--;
            if(n%3==0 && n!=total.length()){
                t[c] = ',';
                c--;
            }
            n++;
        }
        total = String.valueOf(t);
        return total;
    }
}
