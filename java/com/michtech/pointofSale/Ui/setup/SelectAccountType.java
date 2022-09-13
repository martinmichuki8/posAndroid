package com.michtech.pointofSale.Ui.setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;

public class SelectAccountType extends AppCompatActivity {
    Button Next;
    RadioGroup radioGroup;
    RadioButton Admin, Employee;
    private String selected = "none";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_account_type);

        Next = findViewById(R.id.next);
        radioGroup = findViewById(R.id.radioGroup);
        Admin = findViewById(R.id.admin);
        Employee = findViewById(R.id.employee);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.admin:
                        selected = "admin";
                        break;
                    case R.id.employee:
                        selected = "employee";
                        break;
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected.equals("admin")){
                    Intent intent = new Intent(SelectAccountType.this, AccountDetails.class);
                    intent.putExtra("ACCOUNT", "Admin");
                    startActivity(intent);
                    finish();
                }else if(selected.equals("employee")){
                    Intent intent = new Intent(SelectAccountType.this, EmployeeAccount.class);
                    intent.putExtra("ACCOUNT", "Employee");
                    startActivity(intent);
                    finish();
                }else{
                    Snackbar.make(view, "Nothing is selected ", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });
    }
}
