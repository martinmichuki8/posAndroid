package com.michtech.pointofSale.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterMore;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoMore;

import java.util.ArrayList;
import java.util.List;

public class More extends Fragment {
    GridView storeGrid;
    GridView employeeGrid;
    GridView otherGrid;
    GridView deviceGrid;
    ConstraintLayout EmployeeSpace, OtherSpace, DeviceSpace;
    List<PojoMore> list;
    AdapterMore adapterMore;

    private static String AccountType = "Admin";
    DatabaseManager db;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view  = inflater.inflate(R.layout.more, container, false);

        db = new DatabaseManager(getContext());

        AccountType = db.getAccountType();

        storeGrid = view.findViewById(R.id.storeGrid);
        employeeGrid = view.findViewById(R.id.employ);
        otherGrid = view.findViewById(R.id.otherGrid);
        deviceGrid = view.findViewById(R.id.deviceGrid);

        EmployeeSpace = view.findViewById(R.id.employeeSpace);
        OtherSpace = view.findViewById(R.id.otherSpace);
        DeviceSpace = view.findViewById(R.id.deviceSpace);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                listShow();
                adapterMore = new AdapterMore(getContext(), list);
                storeGrid.setAdapter(adapterMore);

                if(AccountType.equals("Admin")){
                    listEmployee();
                    adapterMore = new AdapterMore(getContext(), list);
                    employeeGrid.setAdapter(adapterMore);

                    showOther();
                    adapterMore = new AdapterMore(getContext(), list);
                    otherGrid.setAdapter(adapterMore);

                    devices();
                    adapterMore = new AdapterMore(getContext(), list);
                    deviceGrid.setAdapter(adapterMore);
                }
            }
        });

        return view;
    }
    public void onStart(){
        super.onStart();
        if(!AccountType.equals("Admin")){
            EmployeeSpace.setVisibility(View.GONE);
            OtherSpace.setVisibility(View.GONE);
            DeviceSpace.setVisibility(View.GONE);
        }
    }
    private void listShow(){
        list = new ArrayList<>();
        list.add(new PojoMore(R.drawable.add, "Add products", "AddProducts"));
        list.add(new PojoMore(R.drawable.sell, "Sell products", "SellProducts"));
        if(AccountType.equals("Admin")){
            list.add(new PojoMore(R.drawable.ic_baseline_trending_up_24, "Trend", "trend"));
        }
    }
    private void listEmployee(){
        list = new ArrayList<>();
        list.add(new PojoMore(R.drawable.group, "Employees", "ViewEmployees"));
        list.add(new PojoMore(R.drawable.pay, "Pay Employees", "PayEmployees"));
        list.add(new PojoMore(R.drawable.ic_baseline_history_24, "Payment history", "paymentHistory"));
    }
    private void showOther(){
        list = new ArrayList<>();
        list.add(new PojoMore(R.drawable.shopping_bag, "Other expenses", "other"));
    }
    private void devices(){
        list = new ArrayList<>();
        list.add(new PojoMore(R.drawable.ic_baseline_phonelink_setup_24, "Add devices", "AddDevice"));
        list.add(new PojoMore(R.drawable.ic_baseline_phone_android_24, "Linked devices", "Linked"));
    }
}
