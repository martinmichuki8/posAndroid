package com.michtech.pointofSale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.michtech.pointofSale.Ui.DashBoard;
import com.michtech.pointofSale.Ui.Login.Login;
import com.michtech.pointofSale.Ui.setup.SelectAccountType;
import com.michtech.pointofSale.database.DatabaseManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseManager(MainActivity.this);

    }

    public void onResume() {
        super.onResume();
        switch (getThemeSetting()) {
            case "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            case "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            case "System" ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            db.CheckTables();

            Intent intent = null;
            if (db.checkTableStore() && db.checkTableUser()) {
                if(checkAuthentication()){
                    intent = new Intent(MainActivity.this, Login.class);
                }else{
                    intent = new Intent(MainActivity.this, DashBoard.class);
                }
            } else {
                intent = new Intent(MainActivity.this, SelectAccountType.class);
            }
            startActivity(intent);
            finish();
            //StoreDatabase();
        }, 2000);
    }

    private String getThemeSetting(){
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Theme", "");
    }
    private boolean checkAuthentication(){
        SharedPreferences sharedPreferences = getSharedPreferences("PosSettings", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Authentication", "").equals("Authenticate");
    }

    private void StoreDatabase() {
        File DbFile = new File("data/data/com.michtech.pointofSale/databases/stores");//new File(this.getAssets()+"stores");
        if (DbFile.exists()) {
            System.out.println("file already exist ,No need to Create");
        } else {
            try {
                DbFile.createNewFile();
                System.out.println("File Created successfully");
                File sd = Environment.getExternalStorageDirectory();
                InputStream is = new FileInputStream(sd.getAbsolutePath()+"/stores");
                FileOutputStream fos = new FileOutputStream(DbFile);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                System.out.println("File successfully placed on sdcard");
                //Close the streams
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}