package com.michtech.pointofSale.Ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.MainActivity;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.DashboardAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.fragments.Store;
import com.michtech.pointofSale.functions.Functions;

import java.util.List;
import java.util.Objects;

public class DashBoard extends AppCompatActivity implements Store.Listener{
    ViewPager viewPager;
    TabLayout tabLayout;
    DashboardAdapter dashboardAdapter;
    DatabaseManager db;
    private final int[] tabIcons = {
            R.drawable.home,
            R.drawable.store,
            R.drawable.more
    };
    Functions functions;
    private  int STORAGE_PERMISSION_CODE = 1;
    private String ListType = "Products";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        db = new DatabaseManager(this);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        dashboardAdapter = new DashboardAdapter(getSupportFragmentManager(), DashBoard.this);
        dashboardAdapter.add();
        viewPager.setAdapter(dashboardAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();

        functions = new Functions(DashBoard.this);

        checkPermission();
    }
    private void setTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                switch(ListType){
                    case "Products":
                        if(db.checkProductFromCode(code)){
                            Intent intent = new Intent(this, ViewProduct2.class);
                            intent.putExtra("ID", db.getProductsIdFromCode(code));
                            intent.putExtra("LIST_TYPE", ListType);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "Product not found", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "RecycleBin":
                        if(db.checkRemovedProductFromCode(code)){
                            Intent intent = new Intent(this, ViewProduct2.class);
                            intent.putExtra("ID", db.getProductsIdFromCode(code));
                            intent.putExtra("LIST_TYPE", ListType);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this, "Product not found", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSomeEvent(String s) {
        ListType = s;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DashBoard.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                CreateExternalFolder();
            }
            else {
                Toast.makeText(DashBoard.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void CreateExternalFolder(){
        functions = new Functions(DashBoard.this);
        functions.CreateDirectory();
    }
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            CreateExternalFolder();
        }else{
            functions.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        }
    }
}
