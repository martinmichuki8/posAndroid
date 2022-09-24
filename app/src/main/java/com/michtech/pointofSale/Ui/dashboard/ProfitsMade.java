package com.michtech.pointofSale.Ui.dashboard;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;

public class ProfitsMade extends AppCompatActivity {
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profits_made);

        progress = findViewById(R.id.progress);
    }
}
