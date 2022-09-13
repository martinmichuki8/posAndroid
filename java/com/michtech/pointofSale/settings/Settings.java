package com.michtech.pointofSale.settings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {
    CircleImageView StoreImage;
    TextView StoreName, UserName, ChangeTheme, ImageQuality;
    ConstraintLayout Account;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        db = new DatabaseManager(Settings.this);

        StoreImage = findViewById(R.id.storeImage);
        StoreName = findViewById(R.id.storeName);
        UserName = findViewById(R.id.userName);
        ChangeTheme = findViewById(R.id.changeTheme);
        ImageQuality = findViewById(R.id.imageQuality);
        Account = findViewById(R.id.viewAccount);

        StoreName.setText(db.getStoreName());
        UserName.setText(db.getUser());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Settings.this, ViewAccount.class);
                //startActivity(intent);
            }
        });

        ChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogTheme();
            }
        });
        ImageQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogImageQuality();
            }
        });
    }
    private void setImage(){
        InputStream inputStream = new ByteArrayInputStream(db.getStoreImage());
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        StoreImage.setImageBitmap(bmp);
    }
    private void setDialogTheme(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(Settings.this).inflate(R.layout.change_theme, viewGroup, false);

        RadioGroup themes = dialogView.findViewById(R.id.quality);
        RadioButton Light = dialogView.findViewById(R.id.light);
        RadioButton Dark = dialogView.findViewById(R.id.dark);
        RadioButton UseSystem = dialogView.findViewById(R.id.useSystem);
        TextView Cancel = dialogView.findViewById(R.id.cancel);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        switch(getThemeSetting()){
            case "Light":
                Light.setChecked(true);
                break;
            case "Dark":
                Dark.setChecked(true);
                break;
            case "System":
                UseSystem.setChecked(true);
                break;
        }

        themes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.light:
                        setThemeSetting("Light");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case R.id.dark:
                        setThemeSetting("Dark");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.useSystem:
                        setThemeSetting("System");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        break;
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void setDialogImageQuality(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(Settings.this).inflate(R.layout.change_image_quality, viewGroup, false);

        RadioGroup quality = dialogView.findViewById(R.id.quality);
        RadioButton medium = dialogView.findViewById(R.id.medium);
        RadioButton low = dialogView.findViewById(R.id.low);
        TextView Cancel = dialogView.findViewById(R.id.cancel);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if(getImageQuality().equals("Low")){
            low.setChecked(true);
        }else{
            medium.setChecked(true);
        }

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        quality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.medium:
                        setImageQualitySetting("Medium");
                        break;
                    case R.id.low:
                        setImageQualitySetting("Low");
                        break;
                }
            }
        });
    }
    private void setImageQualitySetting(String qualitySetting){
        SharedPreferences sharedPreferences = getSharedPreferences("ImageQuality", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Quality", qualitySetting);
        editor.apply();
    }
    private String getImageQuality(){
        SharedPreferences sharedPreferences = getSharedPreferences("ImageQuality", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Quality", "");
    }
    private void setThemeSetting(String theme){
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Theme", theme);
        editor.apply();
    }
    private String getThemeSetting(){
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Theme", "");
    }
}
