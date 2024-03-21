package com.michtech.pointofSale.Ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.HistoryAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {
    ListView listView;
    ImageButton Back, Menu;
    List<PojoHistory> list;
    HistoryAdapter historyAdapter;

    private static String date = "All";
    DatePickerDialog.OnDateSetListener onDateSetListener;

    List<DbHelper> dbHelperList;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        db = new DatabaseManager(History.this);

        listView = findViewById(R.id.listView);
        Back = findViewById(R.id.back);
        Menu = findViewById(R.id.menu);

        listShowDay(getDate());
        displayList();

        Back.setOnClickListener(view -> finish());
        onDateSetListener = (datePicker, year, month, day) -> {
            month = month+1;
            String dDay = Integer.toString(day);
            if(day<10){
                dDay = "0"+day;
            }
            if(month<10){
                date = year+"-0"+month+"-"+dDay;
            }else{
                date = year+"-"+month+"-"+dDay;
            }
            System.out.println(date);
            listShowDay(date);
            displayList();
        };
        Menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(History.this, Menu);
            popupMenu.inflate(R.menu.history_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.today -> {
                            listShowDay(getDate());
                            displayList();
                        }
                        case R.id.selectDay -> DateDialog();
                        case R.id.All -> {
                            listShowAll();
                            displayList();
                        }
                    }
                    return false;
                }
            });
        });

    }
    private void displayList(){
        historyAdapter = new HistoryAdapter(History.this, list);
        listView.setAdapter(historyAdapter);
    }
    private void listShowDay(String date){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getProductHistory(date);
        for(DbHelper helper: dbHelperList){
            list.add(new PojoHistory(helper.getProductsName(), helper.getPurchaseOrSale(), helper.getDDate(), helper.getAmount(), helper.getId(), helper.getSetDate(), helper.getDescription()));
        }
    }
    private void listShowAll(){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getProductHistory("All");
        for(DbHelper helper: dbHelperList){
            list.add(new PojoHistory(helper.getProductsName(), helper.getPurchaseOrSale(), helper.getDDate(), helper.getAmount(), helper.getId(), helper.getSetDate(), helper.getDescription()));
        }
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    private void DateDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(History.this, onDateSetListener, year, month, day);
        datePickerDialog.show();
    }
}
