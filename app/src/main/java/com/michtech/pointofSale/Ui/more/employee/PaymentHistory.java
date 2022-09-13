package com.michtech.pointofSale.Ui.more.employee;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.Interface.PaymentHistoryInterface;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterPaymentHistory;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoPaymentHistory;
import com.michtech.pointofSale.pojo.PojoViewEmployee;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentHistory extends AppCompatActivity {
    GridView gridView;

    List<PojoPaymentHistory> list;
    List<DbHelper> dbHelpers;
    AdapterPaymentHistory adapterPaymentHistory;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);

        db = new DatabaseManager(PaymentHistory.this);

        gridView = findViewById(R.id.gridView);

        PaymentHistoryInterface paymentHistoryInterface = new PaymentHistoryInterface() {
            @NonNull
            @Contract(pure = true)
            @Override
            public List<PojoPaymentHistory> setHistoryData() {
                List<PojoPaymentHistory> paymentHistoryList = new ArrayList<>();
                List<String> names = db.getEmployeesPaymentHistoryNames();
                List<String> Name = names.stream().distinct().sorted().collect(Collectors.toList());
                for(String name: Name){
                    paymentHistoryList.add(db.getEmployeePaymentHistory(name));
                }
                return paymentHistoryList;
            }

            @Override
            public void viewPaymentHistoryData() {
                listShow(setHistoryData());
                adapterPaymentHistory = new AdapterPaymentHistory(PaymentHistory.this, list);
                gridView.setAdapter(adapterPaymentHistory);
            }
        };
        paymentHistoryInterface.viewPaymentHistoryData();
    }
    private void listShow(@NonNull List<PojoPaymentHistory> paymentHistoryList){
        list = new ArrayList<>();
        for(PojoPaymentHistory paymentHistory: paymentHistoryList){
            list.add(new PojoPaymentHistory(paymentHistory.getName(), paymentHistory.getNumber(), paymentHistory.getFound()));
        }
    }
}
