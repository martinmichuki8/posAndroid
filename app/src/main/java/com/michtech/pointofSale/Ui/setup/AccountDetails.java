package com.michtech.pointofSale.Ui.setup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.DashBoard;
import com.michtech.pointofSale.database.DatabaseManager;

import java.io.ByteArrayOutputStream;

public class AccountDetails extends AppCompatActivity {
    EditText Store, UserName, Password, ConfirmPassword;
    Button CreateAccount;
    private String AccountType;

    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detail);

        db = new DatabaseManager(AccountDetails.this);

        Store = findViewById(R.id.store);
        UserName = findViewById(R.id.userName);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmPassword);
        CreateAccount = findViewById(R.id.create);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        this.AccountType = bundle.getString("ACCOUNT");

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData()){
                    if(ConfirmPassword(view)){
                        db.AddStore(Store.getText().toString(), AccountType, convertImage(BitmapFactory.decodeResource(getResources(), R.drawable.product)));
                        db.AddUser(UserName.getText().toString(), Password.getText().toString());
                        Intent intent = new Intent(AccountDetails.this, DashBoard.class);
                        startActivity(intent);
                        Clear();
                        finish();
                    }
                }else{
                    Snackbar.make(view, "Fill all the fields", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });

    }
    private boolean checkData(){
        return !Store.getText().toString().isEmpty() && !UserName.getText().toString().isEmpty() &&
                !Password.getText().toString().isEmpty() && !ConfirmPassword.getText().toString().isEmpty();
    }
    private boolean ConfirmPassword(View view){
        boolean check = false;
        if(Password.getText().toString().length()>7){
            if(Password.getText().toString().equals(ConfirmPassword.getText().toString())){
                check = true;
            }else{
                Snackbar.make(view, "Password does not match", Snackbar.LENGTH_LONG).setAction(null, null).show();
            }
        }else{
            Snackbar.make(view, "Password is too short", Snackbar.LENGTH_LONG).setAction(null, null).show();
        }
        return check;
    }
    private void Clear(){
        Store.setText("");
        UserName.setText("");
        Password.setText("");
        ConfirmPassword.setText("");
    }
    private byte[] convertImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
