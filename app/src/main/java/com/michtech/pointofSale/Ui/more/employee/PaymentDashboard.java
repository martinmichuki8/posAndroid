package com.michtech.pointofSale.Ui.more.employee;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoGetEmployee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentDashboard extends AppCompatActivity {
    Button Save;
    TextView Name, Phone, Bank, StartDate, EndDate;
    EditText Amount;
    ImageView SetStartDate, SetEndDate;
    private String startDate="", endDate="";

    List<PojoGetEmployee> getEmployee;
    DatabaseManager db;
    DatePickerDialog.OnDateSetListener onDateSetListener, onDateSetListener2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_dashboard);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("ID");

        db = new DatabaseManager(PaymentDashboard.this);

        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);
        Bank = findViewById(R.id.bank);
        StartDate = findViewById(R.id.startDate);
        EndDate = findViewById(R.id.endDate);
        Save = findViewById(R.id.save);
        Amount = findViewById(R.id.amount);
        SetStartDate = findViewById(R.id.setStartDate);
        SetEndDate = findViewById(R.id.setEndDate);

        setData(id);

        SetStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog("start");
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"-"+month+"-"+year;
                StartDate.setText("Start date: "+date);
                startDate = year+"-"+month+"-"+day;
            }
        };
        SetEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog("end");
            }
        });
        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"-"+month+"-"+year;
                EndDate.setText("End date: "+date);
                endDate = year+"-"+month+"-"+day;
            }
        };

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Amount.getText().toString().isEmpty() || Integer.parseInt(Amount.getText().toString())==0){
                    Snackbar.make(view, "Amount cannot be empty or 0", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else{
                    if(startDate.equals("") || endDate.equals("")){
                        Snackbar.make(view, "Set payment period", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }else{
                        String name = null, email = null, phone = null, bank = null;
                        getEmployee = new ArrayList<>();
                        getEmployee = db.getEmployeeData(id);
                        for (PojoGetEmployee pojoGetEmployee: getEmployee) {
                            name = pojoGetEmployee.getName();
                            email = pojoGetEmployee.getEmail();
                            phone = pojoGetEmployee.getPhone();
                            bank = pojoGetEmployee.getBank();
                        }
                        db.savePaymentHistory(name, email, phone, bank, startDate, endDate, Integer.parseInt(Amount.getText().toString()), getDate());
                        db.updatePayment(id);
                        Toast.makeText(PaymentDashboard.this, "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
    private void setData(int id){
        getEmployee = new ArrayList<>();
        getEmployee = db.getEmployeeData(id);
        for (PojoGetEmployee pojoGetEmployee: getEmployee){
            Name.setText(pojoGetEmployee.getName());
            Phone.setText(pojoGetEmployee.getPhone());
            Bank.setText(pojoGetEmployee.getBank());
        }
    }
    private void DateDialog(@NonNull String type){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        if(type.equals("start")){
            datePickerDialog = new DatePickerDialog(PaymentDashboard.this, onDateSetListener, year, month, day);
        }else{
            datePickerDialog = new DatePickerDialog(PaymentDashboard.this, onDateSetListener2, year, month, day);
        }
        datePickerDialog.show();
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
}
