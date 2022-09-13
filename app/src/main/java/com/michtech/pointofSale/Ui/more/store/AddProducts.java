package com.michtech.pointofSale.Ui.more.store;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.activityPortrait.CaptureActivityPortrait;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.list.ProductList;
import com.michtech.pointofSale.media.MediaLoader;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProducts extends AppCompatActivity{
    EditText Category, Product, Amount, Description, Barcode, PurchasePrice, SellingPrice;
    ImageButton Back, Save, TakePicture;
    ImageView ProductImage, ScanBarcode;
    TextView Status;
    private String Update = "no";
    private int id;

    Bitmap productImage;

    private final int REQUEST_IMAGE_CAPTURE = 1;
    DatabaseManager db;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);

        db = new DatabaseManager(AddProducts.this);

        Bundle bundle = getIntent().getExtras();
        assert bundle!=null;
        Update = bundle.getString("TYPE");
        id = bundle.getInt("ID");

        Category = findViewById(R.id.category);
        Product = findViewById(R.id.product);
        Amount = findViewById(R.id.amount);
        Description = findViewById(R.id.we);
        Barcode = findViewById(R.id.code);
        PurchasePrice = findViewById(R.id.purchase);
        SellingPrice = findViewById(R.id.sell);
        ProductImage = findViewById(R.id.productImage);
        TakePicture = findViewById(R.id.takePicture);
        ScanBarcode = findViewById(R.id.scan);
        Status = findViewById(R.id.status);

        if(Update.equals("Update")){
            Status.setText("Edit products");
        }

        Back = findViewById(R.id.back);
        Save = findViewById(R.id.save);

        TakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromStorage();
                /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }catch (ActivityNotFoundException e){
                    //
                }*/
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData()){
                    String category, product, description, barcode;
                    int amount, purchasePrice, sellingPrice;
                    Bitmap image;

                    category = Category.getText().toString();
                    product = Product.getText().toString();
                    description = Description.getText().toString();
                    amount = Integer.parseInt(Amount.getText().toString());
                    purchasePrice = Integer.parseInt(PurchasePrice.getText().toString());
                    sellingPrice = Integer.parseInt(SellingPrice.getText().toString());

                    if(Barcode.getText().toString().isEmpty()){
                        barcode = "1234";
                    }else{
                        barcode = Barcode.getText().toString();
                    }
                    if (ProductImage.getDrawable()==null){
                        productImage = BitmapFactory.decodeResource(getResources(), R.drawable.product_image);
                    }

                    if(Update.equals("Update")){
                        if(!db.checkCategory(category)){
                            db.AddCategory(category);
                        }
                        db.updatePrice(purchasePrice, sellingPrice, id);

                        int amt = db.getProductAmount(id);
                        String[] data = db.getProductNameCode(id);
                        int productHistoryId = db.getProductHistoryId(data[0], data[1], db.getProductsAddDate(id), "purchased");
                        //get data from database not from form product, barcode
                        db.UpdateProducts(product, description, amount, db.getCurrentCategoryId(category), db.getCurrentPriceId(purchasePrice, sellingPrice), barcode, getDate(),
                                getBitmapAsByteArray(getBitmap()), id);

                        int newAmount = amt;
                        if(amount>amt){
                            newAmount = db.getProductHistoryAmount(productHistoryId)+(amount-amt);
                        }else if(amount<amt){
                            newAmount = db.getProductHistoryAmount(productHistoryId)-(amt-amount);
                        }
                        db.updateProductHistory(product, description, newAmount, category, purchasePrice, sellingPrice, barcode, "purchased",
                                0, getBitmapAsByteArray(getBitmap()), productHistoryId);
                        Snackbar.make(view, "Updated", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }else{
                        if(!db.checkCategory(category)){
                            db.AddCategory(category);
                        }
                        db.AddPrice(purchasePrice, sellingPrice);
                        db.AddProducts(product, description, amount, db.getCurrentCategoryId(category), db.getCurrentPriceId(purchasePrice, sellingPrice), barcode, getDate(), getBitmapAsByteArray(productImage));
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                db.saveToProductHistory(product, description, amount, category, purchasePrice, sellingPrice, barcode, "purchased", 0, getBitmapAsByteArray(productImage), getDate());
                            }
                        });
                        Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG).setAction(null, null).show();
                    }
                    clear();

                }else{
                    Snackbar.make(view, "Fill all the fields", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(AddProducts.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                intentIntegrator.setPrompt("Scan barcode");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                intentIntegrator.initiateScan();
            }
        });
    }
    public void onResume(){
        super.onResume();
        if(Update.equals("Update")){
            getCurrentData();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            productImage = (Bitmap) extras.get("data");
            ProductImage.setImageBitmap(productImage);
        }
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                Barcode.setText(code);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private Bitmap getBitmap(){
        Drawable drawable = ProductImage.getDrawable();
        return ((BitmapDrawable)drawable).getBitmap();
    }
    @SuppressLint("SetTextI18n")
    private void getCurrentData(){
        List<ProductList> productListList = new ArrayList<>();
        productListList = db.getSpecificProducts(id);
        for (ProductList productList: productListList){
            Product.setText(productList.getProductName());
            Category.setText(productList.getCategory());
            Amount.setText(Integer.toString(productList.getAmount()));
            Description.setText(productList.getDescription());
            Barcode.setText(productList.getCode());
            PurchasePrice.setText(Integer.toString(productList.getPurchasePrice()));
            SellingPrice.setText(Integer.toString(productList.getSellingPrice()));
            ProductImage.setImageBitmap(convertByteToImage(db.getProductImage(id)));
        }
    }
    private Bitmap convertByteToImage(byte[] b){
        InputStream inputStream = new ByteArrayInputStream(b);
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }
    private void clear(){
        Category.setText(null);
        Product.setText(null);
        Amount.setText(null);
        Description.setText(null);
        Barcode.setText(null);
        PurchasePrice.setText(null);
        SellingPrice.setText(null);
        ProductImage.setImageBitmap(null);
    }
    private boolean checkData(){
        return !Category.getText().toString().isEmpty() && !Product.getText().toString().isEmpty() && !Amount.getText().toString().isEmpty() &&
                !Description.getText().toString().isEmpty() && !PurchasePrice.getText().toString().isEmpty() && !SellingPrice.getText().toString().isEmpty();
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
    public void Permission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(AddProducts.this, Manifest.permission.CAMERA)){
        }else{
            ActivityCompat.requestPermissions(AddProducts.this, new String[]{
                    Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void getImageFromStorage(){
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.album(this)
                .singleChoice()
                .camera(true)
                .cameraVideoQuality(1)
                .cameraVideoLimitBytes(Long.MAX_VALUE)
                .cameraVideoLimitDuration(Long.MAX_VALUE)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        if(result.get(0).getMediaType()==AlbumFile.TYPE_IMAGE){
                            String imagePath = result.get(0).getPath();
                            Bitmap image = BitmapFactory.decodeFile(imagePath);
                            ProductImage.setImageBitmap(null);
                            productImage = reduceImageSize(image);
                            ProductImage.setImageBitmap(productImage);
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        //
                    }
                })
                .start();
    }
    private Bitmap reduceImageSize(@NonNull Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int mp = 400;
        if(getImageQuality().equals("Low")){
            mp = 200;
        }
        float scaleWidth = ((float) mp) / width;
        float scaleHeight = ((float) mp) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
    }
    public byte[] getBitmapAsByteArray(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    private String getImageQuality(){
        SharedPreferences sharedPreferences = getSharedPreferences("ImageQuality", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Quality", "");
    }
}
