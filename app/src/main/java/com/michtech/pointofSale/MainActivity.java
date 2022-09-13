package com.michtech.pointofSale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import com.michtech.pointofSale.Ui.DashBoard;
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

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        db = new DatabaseManager(MainActivity.this);

    }

    public void onStart() {
        super.onStart();
        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                db.CheckTables();

                Intent intent = null;
                if (db.checkTableStore() && db.checkTableUser()) {
                    intent = new Intent(MainActivity.this, DashBoard.class);
                } else {
                    intent = new Intent(MainActivity.this, SelectAccountType.class);
                }
                startActivity(intent);
                finish();
                //StoreDatabase();
            }
        }.start();
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
                System.out.println("File succesfully placed on sdcard");
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