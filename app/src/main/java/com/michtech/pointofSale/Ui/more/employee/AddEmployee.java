package com.michtech.pointofSale.Ui.more.employee;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.util.Calendar;

public class AddEmployee extends AppCompatActivity {
    EditText Name, Email, Phone, Bank;
    TextView DDate;
    ImageView GetDate;
    Button Add;

    DatabaseManager db;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);
        db = new DatabaseManager(AddEmployee.this);

        Name = findViewById(R.id.description);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.name);
        Bank = findViewById(R.id.bank);
        DDate = findViewById(R.id.we);
        GetDate = findViewById(R.id.getDate);
        Add = findViewById(R.id.add);

        GetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"-"+month+"-"+year;
                DDate.setText(date);
            }
        };
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckForm()){
                    String name, email, phone, bank, date;
                    name = Name.getText().toString();
                    email = Email.getText().toString();
                    phone = Phone.getText().toString();
                    date = DDate.getText().toString();
                    if(Bank.getText().toString().isEmpty()){
                        bank = "none";
                    }else{
                        bank = Bank.getText().toString();
                    }
                    if(db.CheckEmployee(name, email)){
                        Snackbar.make(view, "Employee already exists", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }else{
                        db.AddEmployee(name, email, phone, bank, date);
                        ClearForm();
                        Snackbar.make(view,"Saved", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }
                }else{
                    Snackbar.make(view,"Fill all the fields", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DateDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEmployee.this, onDateSetListener, year, month, day);
        datePickerDialog.show();
    }
    private boolean CheckForm(){
        return !Name.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() &&
                !Phone.getText().toString().isEmpty() && !DDate.getText().toString().isEmpty();
    }
    private void ClearForm(){
        Name.setText("");
        Email.setText("");
        Phone.setText("");
        Bank.setText("");
        DDate.setText("");
    }
}