package com.michtech.pointofSale.Ui.No;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.AddProducts;

public class NoProductsFound extends AppCompatActivity {
    FloatingActionButton Add;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_products);

        Add = findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoProductsFound.this, AddProducts.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
