package com.michtech.pointofSale.settings;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.util.Objects;

public class SecurityQuestion extends AppCompatActivity {
    ImageButton Back;
    TextInputLayout Question;
    Button Save;

    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_question);

        db = new DatabaseManager(SecurityQuestion.this);

        Back = findViewById(R.id.back);
        Question = findViewById(R.id.question);
        Save = findViewById(R.id.save);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.requireNonNull(Question.getEditText()).getText().toString().isEmpty()){
                    Snackbar.make(view, "Enter your answer", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else{
                    db.saveSecurityQuestion(Question.getEditText().getText().toString());
                    setAuthentication("Authenticate");
                    Toast.makeText(SecurityQuestion.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void setAuthentication(String authenticate){
        SharedPreferences sharedPreferences = getSharedPreferences("PosSettings", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Authentication", authenticate);
        editor.apply();
    }
}
