package com.michtech.pointofSale.Ui.more.store;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterTrend;
import com.michtech.pointofSale.pojo.PojoTrend;

import java.util.ArrayList;
import java.util.List;

public class ProductsTrend extends AppCompatActivity {
    ImageButton Back;
    ListView listView;

    List<PojoTrend> pojoTrendList;
    AdapterTrend adapterTrend;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_trend);

        Back = findViewById(R.id.back);
        listView = findViewById(R.id.listView);

        showList();
        adapterTrend = new AdapterTrend(ProductsTrend.this, pojoTrendList);
        listView.setAdapter(adapterTrend);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void showList(){
        pojoTrendList = new ArrayList<>();

        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
        pojoTrendList.add(new PojoTrend(1, "ProductName", 10));
    }
}
