package com.michtech.pointofSale.Ui.more.store.trend;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.michtech.pointofSale.R;

import java.util.ArrayList;

public class ProductTrend extends AppCompatActivity {
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_trend);

        lineChart = findViewById(R.id.lineGraph);

        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "Trend");
        lineData = new LineData(lineDataSet);
        lineChart.animateXY(1000, 1000);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        //lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(0f);
        lineChart.setDrawGridBackground(false);
        XAxis xAxis;
        xAxis = lineChart.getXAxis();
    }
    private void getEntries() {
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(2f, 0));
        lineEntries.add(new Entry(4f, 11));
        lineEntries.add(new Entry(6f, 2));
        lineEntries.add(new Entry(8f, 7));
        lineEntries.add(new Entry(10f, 10));
        lineEntries.add(new Entry(12f, 8));
        lineEntries.add(new Entry(14f, 6));
    }
}
