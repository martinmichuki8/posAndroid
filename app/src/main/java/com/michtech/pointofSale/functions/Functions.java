package com.michtech.pointofSale.functions;

import android.content.Context;

import androidx.annotation.NonNull;

import com.michtech.pointofSale.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Functions {

    private final Context context;

    List<DuplicateProducts> duplicateProductsList;
    DatabaseManager db;

    public Functions(Context context){
        this.context = context;
    }

    public boolean checkMerge(){
        boolean found = false;
        if(duplicates().size()>0){
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
    @NonNull
    private DuplicateProducts addData(String productName){
        DuplicateProducts duplicateProducts = new DuplicateProducts();
        for(Integer productIds: db.getProductsId(productName)){
            //
        }
        return duplicateProducts;
    }
}
