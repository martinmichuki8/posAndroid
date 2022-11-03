package com.michtech.pointofSale.fragments.employeeDevice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.History;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.Ui.more.store.SellProducts;
import com.michtech.pointofSale.adapter.HistoryAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoHistory;
import com.michtech.pointofSale.settings.Settings;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeviceHome extends Fragment {
    ListView listView;
    CircleImageView AccountImage;
    TextView SeeAll, TodayDate, TotalSales;
    ConstraintLayout AddProducts, SellProducts;

    List<PojoHistory> list;
    HistoryAdapter historyAdapter;
    List<DbHelper> dbHelperList;
    DatabaseManager db;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_dashboard, container, false);

        db = new DatabaseManager(getContext());

        listView = view.findViewById(R.id.listView);
        AccountImage = view.findViewById(R.id.accountImage);
        SeeAll = view.findViewById(R.id.seeAll);
        TodayDate = view.findViewById(R.id.date);
        TotalSales = view.findViewById(R.id.totalSales);
        AddProducts = view.findViewById(R.id.addProducts);
        SellProducts = view.findViewById(R.id.sellProducts);

        setImage();
        TodayDate.setText(getDate());
        TotalSales.setText(Integer.toString(db.getTodayTotalSales(getDateSearch())));

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showList();
            }
        });

        AccountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Settings.class);
                startActivity(intent);
            }
        });
        SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getContext(), History.class);
                startActivity(intent);
            }
        });

        AddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), com.michtech.pointofSale.Ui.more.store.AddProducts.class);
                intent.putExtra("TYPE", "no");
                intent.putExtra("ID", 0);
                startActivity(intent);
            }
        });
        SellProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), com.michtech.pointofSale.Ui.more.store.SellProducts.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void showList(){
        listShow();
        historyAdapter = new HistoryAdapter(getContext(), list);
        listView.setAdapter(historyAdapter);
    }
    private void listShow(){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getProductHistory("Home");
        int c = 0;
        for(DbHelper helper: dbHelperList){
            list.add(new PojoHistory(helper.getProductsName(), helper.getPurchaseOrSale(), helper.getDDate(), helper.getAmount(), helper.getId(), helper.getSetDate(), helper.getDescription()));
            if(c==7){
                break;
            }
            c++;
        }
    }
    private void setImage(){
        InputStream inputStream = new ByteArrayInputStream(db.getStoreImage());
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        AccountImage.setImageBitmap(bmp);
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
    @NonNull
    private String getDateSearch(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
}
