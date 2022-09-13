package com.michtech.pointofSale.Ui.No;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.employee.AddEmployee;

public class NoEmployees extends AppCompatActivity {
    FloatingActionButton Add;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_employee);

        Add = findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoEmployees.this, AddEmployee.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
