package com.michtech.pointofSale.duplicates;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;

public class DuplicateProductsList extends AppCompatActivity {
    ImageButton RelatedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplicate_product_list);

        RelatedBack = findViewById(R.id.relatedBack);

        RelatedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
