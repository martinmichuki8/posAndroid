package com.michtech.pointofSale.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class Calculate extends Service {
    DatabaseManager db;
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DatabaseManager(getApplicationContext());
        calculateTotalSales();
        calculateTotalPurchases();
        stopService(intent);

        sendProfits();

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
    private void sendProfits(){
        Intent intent = new Intent("Profits");
        intent.putExtra("Profit", getEstimatedProfit(true));
        intent.putExtra("EstimatedProfit", getEstimatedProfit(false));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    private int getEstimatedProfit(boolean sold){
        int estimatedProfit = 0;
        for(DbHelper dbHelper: db.getProfitsData(sold)){
            estimatedProfit += (dbHelper.getAmount()*dbHelper.getSellingPrice())-(dbHelper.getAmount()*dbHelper.getPurchasePrice());
        }
        return estimatedProfit;
    }
    private int getProfit(int buyingPrice, int sellingPrice){
        return (sellingPrice-buyingPrice);
    }
}
