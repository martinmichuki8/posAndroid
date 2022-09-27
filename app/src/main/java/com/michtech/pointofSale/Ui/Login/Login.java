package com.michtech.pointofSale.Ui.Login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.DashBoard;
import com.michtech.pointofSale.database.DatabaseManager;

import java.util.Objects;

public class Login extends AppCompatActivity {
    Button LoginButton;
    TextInputLayout Password;
    TextView StoreName;

    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        db = new DatabaseManager(Login.this);

        LoginButton = findViewById(R.id.login);
        Password = findViewById(R.id.password);
        StoreName = findViewById(R.id.storeName);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.requireNonNull(Password.getEditText()).getText().toString().isEmpty()){
                    Snackbar.make(view, "Enter password", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else{
                    if(Password.getEditText().getText().toString().equals(db.getAccountPassword())){
                        Intent intent = new Intent(Login.this, DashBoard.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Snackbar.make(view, "Password does not match", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }
                }
            }
        });
    }
    public void onStart(){
        super.onStart();
        StoreName.setText(db.getStoreName());
    }
}
