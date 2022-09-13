package com.michtech.pointofSale.graph;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class BarGraph extends AppCompatActivity {
    AnyChartView anyChartView;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_graph);

        db = new DatabaseManager(BarGraph.this);

        anyChartView = findViewById(R.id.bar_chart);
        anyChartView.setProgressBar(findViewById(R.id.progress));

        Cartesian cartesian = AnyChart.column();

        Column column = cartesian.column(db.getDateFromHistoryProducts());
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("Ksh{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Total sales this month");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("Ksh{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Day");
        cartesian.yAxis(0).title("Total sales");

        anyChartView.setChart(cartesian);

    }
    /*@NonNull
    private List<DataEntry> addList(){
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Rouge", 80540));
        data.add(new ValueDataEntry("Foundation", 94190));
        data.add(new ValueDataEntry("Mascara", 102610));
        data.add(new ValueDataEntry("Lip gloss", 110430));
        data.add(new ValueDataEntry("Lipstick", 128000));
        data.add(new ValueDataEntry("Nail polish", 143760));
        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
        data.add(new ValueDataEntry("Eyeliner", 213210));
        data.add(new ValueDataEntry("Eyeshadows", 249980));
        return data;
    }*/
}
