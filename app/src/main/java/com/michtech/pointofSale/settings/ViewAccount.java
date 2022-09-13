package com.michtech.pointofSale.settings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ViewAccount extends AppCompatActivity {
    ImageView AccountImage;
    TextView StoreName, UserName;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_account);

        StoreName = findViewById(R.id.storeName);
        UserName = findViewById(R.id.userName);
        AccountImage = findViewById(R.id.image);

        db = new DatabaseManager(ViewAccount.this);

        StoreName.setText(db.getStoreName());
        UserName.setText(db.getUser());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        });
    }
    private void setImage(){
        InputStream inputStream = new ByteArrayInputStream(db.getStoreImage());
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        AccountImage.setImageBitmap(bmp);
    }
}
