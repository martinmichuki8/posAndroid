package com.michtech.pointofSale.Ui.more.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterViewEmployee;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoViewEmployee;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ViewEmployee extends AppCompatActivity {
    GridView gridView;
    FloatingActionButton Add;

    List<PojoViewEmployee> list;
    List<DbHelper> dbHelpers;
    AdapterViewEmployee adapterViewEmployee;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_employees);

        db = new DatabaseManager(ViewEmployee.this);

        gridView = findViewById(R.id.gridView);
        Add = findViewById(R.id.add);

        listShow();
        adapterViewEmployee = new AdapterViewEmployee(ViewEmployee.this, list);
        gridView.setAdapter(adapterViewEmployee);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEmployee.this, AddEmployee.class);
                startActivity(intent);
            }
        });
    }
    private void listShow(){
        list = new ArrayList<>();
        dbHelpers = new ArrayList<>();

        dbHelpers = db.getEmployeeList();

        for(DbHelper helper: dbHelpers){
            list.add(new PojoViewEmployee(helper.getId(), helper.getName(), helper.getEmail(), helper.getPhone()));
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(contextMenu, v, menuInfo);
        getMenuInflater().inflate(R.menu.employee_menu, contextMenu);
        System.out.println();
    }
}
