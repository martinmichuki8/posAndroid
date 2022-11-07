package com.michtech.pointofSale.Ui.store;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.functions.Functions;

public class Pricing extends AppCompatActivity {
    ImageButton Back, Share, Save;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricing);

        Back = findViewById(R.id.back);
        Share = findViewById(R.id.share);
        Save = findViewById(R.id.saveToStorage);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Functions functions = new Functions(Pricing.this);
                functions.writeExternalExcelData(view);
                Toast.makeText(Pricing.this, "Saved", Toast.LENGTH_LONG).show();
            }
        });
    }
}
