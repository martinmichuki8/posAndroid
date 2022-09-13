package com.michtech.pointofSale.background;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.michtech.pointofSale.Ui.DashBoard;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.list.ProductList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceSaveToHistory extends Service {

    DatabaseManager db;

    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        assert bundle !=null;
        String paymentMethod = bundle.getString("PAYMENT_METHOD");
        saveToProductHistory(paymentMethod);
        stopService(intent);
        return START_NOT_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void saveToProductHistory(String paymentMethod){
        db = new DatabaseManager(getApplicationContext());
        List<DbHelper> dbHelperList1 = new ArrayList<>();
        dbHelperList1 = db.getShoppingList();
        for (DbHelper helper: dbHelperList1){
            saveToProductsHistory(helper.getId(), helper.getProductId(), helper.getAmount(), helper.getTotalPrice(), paymentMethod);
            updateAmountInTableProduct(db.getProductAmount(helper.getProductId()), helper.getAmount(), helper.getProductId());
        }
        //db.dropTableTempProduct();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
    private void updateAmountInTableProduct(int currentAmount, int newAmount, int id){
        db = new DatabaseManager(getApplicationContext());
        int amount = currentAmount - newAmount;
        System.out.println(currentAmount+" - "+newAmount+" = "+amount+" id="+id);
        if(amount<1){
            db.deleteProduct(id);
        }else{
            db.updateProductAmount(amount, id);
        }
    }
    public void saveToProductsHistory(int id, int productId, int amount, int priceSold, String paymentMethod){
        db = new DatabaseManager(getApplicationContext());
        List<ProductList> productLists = new ArrayList<>();
        productLists = db.getSpecificProducts(productId);
        for(ProductList productList: productLists){
            db.saveToProductHistory(productList.getProductName(), productList.getDescription(), amount, productList.getCategory(),
                    productList.getPurchasePrice(), productList.getSellingPrice(), productList.getCode(), "sold",
                    priceSold, db.getProductImage(productId), getDate());
            savePaymentMethod(paymentMethod, db.getProductHistoryId(productList.getProductName(), productList.getCode(), getDate(), "sold"));
        }
        db.deleteTempProduct(id);
    }
    private void savePaymentMethod(String paymentMethod, int id){
        db = new DatabaseManager(getApplicationContext());
        if(!db.checkPaymentMethodId(id)){
            db.AddPaymentMethod(id, paymentMethod);
        }
        db.AddPaymentMethodLink(id, db.getPaymentMethodId(paymentMethod));
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
}
