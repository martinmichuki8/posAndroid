package com.michtech.pointofSale.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.History;
import com.michtech.pointofSale.Ui.dashboard.ProfitsMade;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {
    CircleImageView Account;
    ImageButton AddSale, AddPurchase;
    ListView  listView;
    TextView viewAll, TotalSales, TotalPurchases;
    TextView Profit, EstimatedProfit;
    List<PojoHistory> list;
    HistoryAdapter historyAdapter;
    ImageView SalesGraph;
    ConstraintLayout Profits, EstimatedProfits;

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
        Profit = view.findViewById(R.id.profit);
        EstimatedProfit = view.findViewById(R.id.estimatedProfit);
        Profits = view.findViewById(R.id.profits);
        EstimatedProfits = view.findViewById(R.id.estimatedProfits);

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
        Profits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfitsMade.class);
                startActivity(intent);
            }
        });
        EstimatedProfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfitsMade.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void onStart() {
        super.onStart();
        setProfits();
        setImage();
    }
    public void onResume(){
        super.onResume();
        requireContext().startService(new Intent(getContext(), Calculate.class));
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(dataReceived, new IntentFilter("Profits"));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setTotalSales();
                setTotalPurchases();
            }
        });
    }
    private final BroadcastReceiver dataReceived = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            int profit = intent.getIntExtra("Profit", 0);
            int estimatedProfit = intent.getIntExtra("EstimatedProfit", 0);
            Profit.setText(addComma(profit));
            EstimatedProfit.setText(addComma(estimatedProfit));
        }
    };
    public void onPause(){
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(dataReceived);
        super.onPause();
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
    @SuppressLint("SetTextI18n")
    private void setProfits(){
        int[] data = db.getProfits();
        Profit.setText(Integer.toString(data[0]));
        EstimatedProfit.setText(Integer.toString(data[1]));
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
