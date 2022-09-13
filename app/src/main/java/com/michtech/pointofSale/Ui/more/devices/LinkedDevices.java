package com.michtech.pointofSale.Ui.more.devices;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterDevice;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoDevices;

import java.util.ArrayList;
import java.util.List;

public class LinkedDevices extends AppCompatActivity {
    GridView gridView;

    List<PojoDevices> list;
    List<DbHelper> dbHelperList;
    AdapterDevice adapterDevice;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_devices);

        db = new DatabaseManager(LinkedDevices.this);

        gridView = findViewById(R.id.gridView);
        showList();
        adapterDevice = new AdapterDevice(LinkedDevices.this, list);
        gridView.setAdapter(adapterDevice);
    }
    private void showList(){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getDevicesList();
        for (DbHelper dbHelper: dbHelperList){
            list.add(new PojoDevices(dbHelper.getName(), dbHelper.getId()));
        }
    }
}
