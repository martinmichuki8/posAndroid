package com.michtech.pointofSale.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.michtech.pointofSale.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class Calculate extends Service {
    DatabaseManager db;
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DatabaseManager(getApplicationContext());
        calculateTotalSales();
        calculateTotalPurchases();
        stopService(intent);
        return START_NOT_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void calculateTotalSales(){
        if(db.getTotalSalesRowCount()<1){
            db.UpdateTotalPurchase(db.calculateTotalSales(), false);
        }else{
            db.UpdateTotalPurchase(db.calculateTotalSales(), true);
        }
    }
    private void calculateTotalPurchases(){
        int price = 0;
        List<CalculateData> calculateDataList = new ArrayList<>();
        calculateDataList = db.getPurchasesData();
        for(CalculateData calculateData: calculateDataList){
            price+=calculateData.getAmount()*calculateData.getPurchasePerItem();
        }
        db.updateTotalPurchases(price);
    }
}
