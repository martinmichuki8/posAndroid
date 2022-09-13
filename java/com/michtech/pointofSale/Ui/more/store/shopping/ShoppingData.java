package com.michtech.pointofSale.Ui.more.store.shopping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterShopping;
import com.michtech.pointofSale.background.ServiceSaveToHistory;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.list.ProductList;
import com.michtech.pointofSale.pojo.PojoShopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingData extends AppCompatActivity {
    TextView Price, ShoppingBag, PaymentMethod;
    ListView listView;
    ImageButton Back, Save;
    ImageView MethodImage;

    List<PojoShopping> list;
    AdapterShopping adapterShopping;

    List<DbHelper> dbHelperList;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_data);

        db = new DatabaseManager(ShoppingData.this);

        Price = findViewById(R.id.price);
        listView = findViewById(R.id.listView);
        ShoppingBag = findViewById(R.id.shopping);
        Back = findViewById(R.id.back);
        Save = findViewById(R.id.save);
        PaymentMethod = findViewById(R.id.paymentMethod);
        MethodImage = findViewById(R.id.image);

        new Handler().post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                Price.setText(Integer.toString(db.getTotalPrice()));
                ShoppingBag.setText(Integer.toString(db.getTotalAmount()));
            }
        });

       showData();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingData.this, ServiceSaveToHistory.class);
                intent.putExtra("PAYMENT_METHOD", PaymentMethod.getText().toString());
                startService(intent);
                Clear();
            }
        });
        PaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ShoppingData.this, PaymentMethod);
                popupMenu.inflate(R.menu.payment_method_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables", "SetTextI18n"})
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.cash:
                                PaymentMethod.setText("Cash");
                                MethodImage.setImageResource(R.drawable.sell);
                                break;
                            case R.id.mobile:
                                PaymentMethod.setText("Mobile");
                                MethodImage.setImageResource(R.drawable.ic_baseline_phone_android_24);
                                break;
                            case R.id.card:
                                PaymentMethod.setText("Card");
                                MethodImage.setImageResource(R.drawable.pay);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }
    private void listShow(){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getShoppingList();
        for(DbHelper helper: dbHelperList){
            list.add(new PojoShopping(helper.getProductsName(), helper.getCategory(), helper.getTotalPrice(), helper.getAmount(), helper.getId()));
        }
    }
    private void Clear(){
        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                //
            }

            @Override
            public void onFinish() {
                if(!db.checkTempProductsTable()){
                    showData();
                }else{
                    Clear();
                }
            }
        }.start();
    }
    private void showData(){
        listShow();
        adapterShopping = new AdapterShopping(ShoppingData.this, list);
        listView.setAdapter(adapterShopping);
    }
}
