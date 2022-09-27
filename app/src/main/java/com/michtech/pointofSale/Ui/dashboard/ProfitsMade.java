package com.michtech.pointofSale.Ui.dashboard;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.background.Calculate;
import com.michtech.pointofSale.functions.Functions;

public class ProfitsMade extends AppCompatActivity {
    ProgressBar progress;
    TextView Profit, EstimatedProfit, Percent;
    Functions functions;
    Handler handler = new Handler();
    ImageButton Back;
    private int progressValue=0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profits_made);

        functions = new Functions(ProfitsMade.this);

        progress = (ProgressBar) findViewById(R.id.progress2);
        Profit = findViewById(R.id.profit);
        EstimatedProfit = findViewById(R.id.estimatedProfit);
        Percent = findViewById(R.id.percent);
        Back = findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void onResume(){
        super.onResume();
        startService(new Intent(ProfitsMade.this, Calculate.class));
        LocalBroadcastManager.getInstance(ProfitsMade.this).registerReceiver(dataReceived, new IntentFilter("Profits"));
    }
    private final BroadcastReceiver dataReceived = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            int profit = intent.getIntExtra("Profit", 0);
            int estimatedProfit = intent.getIntExtra("EstimatedProfit", 0);
            Profit.setText(functions.addComma(profit));
            EstimatedProfit.setText(functions.addComma(estimatedProfit));
            setProgressValue(calculatePercentageProfit(profit, estimatedProfit));
        }
    };
    private void setProgressValue(int percentage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressValue<percentage){
                    progressValue+=1;
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            progress.setProgress(progressValue);
                            Percent.setText(Integer.toString(progressValue)+"%");
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private int calculatePercentageProfit(int profit, int estimatedProfit){
        return ((100*profit)/estimatedProfit);
    }
}
