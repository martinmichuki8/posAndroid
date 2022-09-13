package com.michtech.pointofSale.Ui.more.employee;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterPayEmployee;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoPayEmployee;

import java.util.ArrayList;
import java.util.List;

public class PayEmployee extends AppCompatActivity {
    Spinner spinner;
    GridView listView;
    Button Renew;

    AdapterPayEmployee adapterPayEmployee;
    List<PojoPayEmployee> list;

    DatabaseManager db;
    List<DbHelper> dbHelperList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_employees);
        db = new DatabaseManager(PayEmployee.this);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);
        Renew = findViewById(R.id.renew);

        Renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.UpdatePaid();
                recreate();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        switch (spinner.getSelectedItem().toString()){
                            case "All employees":
                                listShowAll();
                                adapterPayEmployee = new AdapterPayEmployee(PayEmployee.this, list);
                                listView.setAdapter(adapterPayEmployee);
                                Renew.setVisibility(View.INVISIBLE);
                                break;
                            case "Paid employees":
                                listShowPaid();
                                adapterPayEmployee = new AdapterPayEmployee(PayEmployee.this, list);
                                listView.setAdapter(adapterPayEmployee);
                                Renew.setVisibility(View.VISIBLE);
                                break;
                            case "Unpaid employees":
                                listShowNotPaid();
                                adapterPayEmployee = new AdapterPayEmployee(PayEmployee.this, list);
                                listView.setAdapter(adapterPayEmployee);
                                Renew.setVisibility(View.INVISIBLE);
                                break;
                            default:
                                listView.setAdapter(null);
                                break;
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });
    }
    public void onStart(){
        super.onStart();
        addToSpinner();
    }
    private void addToSpinner(){
        List<String> sList = new ArrayList<>();
        sList.add("All employees");
        sList.add("Paid employees");
        sList.add("Unpaid employees");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PayEmployee.this, android.R.layout.simple_spinner_dropdown_item, sList);
        spinner.setAdapter(adapter);
    }
    private void listShowAll(){
        boolean paid=false;
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getPaymentList("All");
        list = new ArrayList<>();

        for(DbHelper helper: dbHelperList){
            if(helper.getPaid().equals("Paid")){
                paid = true;
            }else{
                paid = false;
            }
            list.add(new PojoPayEmployee(helper.getName(), helper.getPhone(), paid, helper.getId()));
        }
    }
    private void listShowNotPaid(){
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getPaymentList("NotPaid");
        list = new ArrayList<>();

        for(DbHelper helper: dbHelperList){
            list.add(new PojoPayEmployee(helper.getName(), helper.getPhone(), false, helper.getId()));
        }
    }
    private void listShowPaid(){
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getPaymentList("Paid");
        list = new ArrayList<>();

        for(DbHelper helper: dbHelperList){
            list.add(new PojoPayEmployee(helper.getName(), helper.getPhone(), true, helper.getId()));
        }
    }
}
