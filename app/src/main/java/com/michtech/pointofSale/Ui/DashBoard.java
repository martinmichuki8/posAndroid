package com.michtech.pointofSale.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.DashboardAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.fragments.Store;

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
}
