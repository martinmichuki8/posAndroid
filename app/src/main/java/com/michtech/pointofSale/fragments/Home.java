package com.michtech.pointofSale.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.History;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.Ui.more.store.SellProducts;
import com.michtech.pointofSale.adapter.HistoryAdapter;
import com.michtech.pointofSale.background.Calculate;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.graph.BarGraph;
import com.michtech.pointofSale.graph.LineGraph;
import com.michtech.pointofSale.pojo.PojoHistory;
import com.michtech.pointofSale.settings.Settings;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {
    CircleImageView Account;
    ImageButton AddSale, AddPurchase;
    ListView  listView;
    TextView viewAll, TotalSales, TotalPurchases;
    List<PojoHistory> list;
    HistoryAdapter historyAdapter;
    ImageView SalesGraph;

    List<DbHelper> dbHelperList;
    DatabaseManager db;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view  = inflater.inflate(R.layout.home, container, false);
        db = new DatabaseManager(getContext());

        Account = view.findViewById(R.id.account);
        listView = view.findViewById(R.id.listView);
        viewAll = view.findViewById(R.id.viewAll);
        TotalSales = view.findViewById(R.id.totalSales);
        TotalPurchases = view.findViewById(R.id.totalPurchases);
        AddPurchase = view.findViewById(R.id.addProduct);
        AddSale = view.findViewById(R.id.sellProducts);
        SalesGraph = view.findViewById(R.id.salesGraph);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(db.CheckTableProductHistory()<1){
                    viewAll.setVisibility(View.INVISIBLE);
                }
                showList();
                //setTotalSales();
            }
        });
        AddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SellProducts.class);
                startActivity(intent);
            }
        });
        AddPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProducts.class);
                intent.putExtra("TYPE", "no");
                intent.putExtra("ID", "0");
                startActivity(intent);
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), History.class);
                startActivity(intent);
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Settings.class);
                startActivity(intent);
            }
        });
        SalesGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BarGraph.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void onStart() {
        super.onStart();
        getContext().startService(new Intent(getContext(), Calculate.class));
        setImage();
    }
    public void onResume(){
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setTotalSales();
                setTotalPurchases();
            }
        });
    }
    private void setImage(){
        InputStream inputStream = new ByteArrayInputStream(db.getStoreImage());
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        Account.setImageBitmap(bmp);
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
    @SuppressLint("SetTextI18n")
    private void setTotalSales(){
        if(db.getTotalSalesRowCount()<1){
            TotalSales.setText("0.0");
        }else{
            TotalSales.setText(addComma(db.getTotalSales()));
        }
    }
    private void setTotalPurchases() {
        if (!db.checkPurchases()) {
            TotalPurchases.setText("0.0");
        } else {
            TotalPurchases.setText(addComma(db.getTotalPurchases()));
        }
    }
    @NonNull
    private String addComma(int price){
        String total = Integer.toString(price);
        int size = total.length()+total.length()/3;
        char t[] = new char[size+2];
        int c=size+1;
        int n=1;
        for(int i=total.length()-1; i>-1; i--){
            t[c] = total.charAt(i);
            c--;
            if(n%3==0 && n!=total.length()){
                t[c] = ',';
                c--;
            }
            n++;
        }
        total = String.valueOf(t);
        return total;
    }
}
