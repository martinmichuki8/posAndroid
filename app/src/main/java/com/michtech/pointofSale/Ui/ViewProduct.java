package com.michtech.pointofSale.Ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProduct extends AppCompatActivity {
    TextView Category, ProductName, Amount, Description, Barcode, Price, BuyingPrice, SellingPrice, DDate;
    CircleImageView ProductImage;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);

        db = new DatabaseManager(ViewProduct.this);

        ProductImage = findViewById(R.id.productImage);
        Category = findViewById(R.id.category);
        ProductName = findViewById(R.id.productName);
        Amount = findViewById(R.id.amount);
        Description = findViewById(R.id.we);
        Barcode = findViewById(R.id.code);
        Price = findViewById(R.id.price);
        BuyingPrice = findViewById(R.id.buyingPrice);
        SellingPrice = findViewById(R.id.sellingPrice);
        DDate = findViewById(R.id.date);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("ID");

        getProductDetails(id);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ProductImage.setImageBitmap(convertByteToImage(db.getProductHistoryImage(id)));
            }
        });
    }
    private Bitmap convertByteToImage(byte[] b){
        InputStream inputStream = new ByteArrayInputStream(b);
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }
    @SuppressLint("SetTextI18n")
    private void getProductDetails(int id){
        List<DbHelper> dbHelperList = new ArrayList<>();
        dbHelperList = db.getSpecificProductFromHistory(id);
        for(DbHelper helper: dbHelperList){
            ProductName.setText(helper.getProductsName());
            Amount.setText(Integer.toString(helper.getAmount()));
            Category.setText(helper.getCategory());
            Description.setText(helper.getDescription());
            Barcode.setText(helper.getCode());
            if(!helper.getPurchaseOrSale().equals("sold")){
                Price.setText(Integer.toString(helper.getAmount()*helper.getPurchasePrice()));
            }else{
                Price.setText(Integer.toString(helper.getTotalPrice()));
            }
            BuyingPrice.setText(Integer.toString(helper.getPurchasePrice()));
            SellingPrice.setText(Integer.toString(helper.getSellingPrice()));
            DDate.setText(helper.getPurchaseOrSale()+": "+helper.getDDate());
        }
    }
}
