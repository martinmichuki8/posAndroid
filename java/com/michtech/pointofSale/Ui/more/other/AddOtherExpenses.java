package com.michtech.pointofSale.Ui.more.other;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddOtherExpenses extends AppCompatActivity {
    EditText Description, Amount;
    AutoCompleteTextView Type;
    ImageButton Back, Save;

    private int ImageCode = 0;

    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_other_expenses);

        db = new DatabaseManager(AddOtherExpenses.this);

        Type = findViewById(R.id.type);
        Description = findViewById(R.id.description);
        Amount = findViewById(R.id.amount);
        Save = findViewById(R.id.save);
        Back = findViewById(R.id.back);

        setAutocomplete();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                if(validate()){
                    setImageCode(Type.getText().toString());
                    db.AddOtherExpenses(Type.getText().toString(), Description.getText().toString(),
                            Integer.parseInt(Amount.getText().toString()), ImageCode, getDate());
                    clear();
                    Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else{
                    Snackbar.make(view, "Fill all the fields", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });
    }
    private boolean validate(){
        return !Type.getText().toString().isEmpty() && !Description.getText().toString().isEmpty() && !Amount.getText().toString().isEmpty();
    }
    private SimpleDateFormat getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return sdf;
    }
    private void setAutocomplete(){
        List<String> types = new ArrayList<>();
        types.add("Transport");
        types.add("Electricity");
        types.add("Food");
        types.add("Water");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types);
        Type.setAdapter(adapter);
    }
    private void setImageCode(@NonNull String type){
        switch (type){
            case "transport":
            case "Transport":
                ImageCode = 1;
                break;
            case "Electricity":
            case "electricity":
                ImageCode = 2;
                break;
            case "Food":
            case "food":
                ImageCode = 3;
                break;
            case "Water":
            case "water":
                ImageCode = 4;
                break;
        }
    }
    private void clear(){
        Type.setText("");
        Description.setText("");
        Amount.setText("");
    }
}
