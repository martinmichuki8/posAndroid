package com.michtech.pointofSale.Ui.more.store;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.shopping.ShoppingData;
import com.michtech.pointofSale.activityPortrait.CaptureActivityPortrait;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.dialog.MessageDialog;
import com.michtech.pointofSale.list.ProductList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SellProducts extends AppCompatActivity {
    private int sellingPricePerItem=0;
    private int Max_Amount=0;
    private boolean moreExists = false;
    private int shoppingAmount = 0;
    private String ProductCategory;
    private String productDescription;

    EditText Amount, Barcode, SellingPrice;
    AutoCompleteTextView ProductName;
    TextView Category, Description, PurchasePrice;
    ImageView ProductImage, ScanBarcode;
    ImageButton Back, Add;
    TextView AddedAmount;
    FloatingActionButton Shopping;
    Spinner DescriptionSpinner;

    List<ProductList> productLists;

    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_products);

        db = new DatabaseManager(SellProducts.this);

        ProductName = findViewById(R.id.product);
        Amount = findViewById(R.id.amount);
        Barcode = findViewById(R.id.barcode);
        SellingPrice = findViewById(R.id.buyingPrice);
        Category = findViewById(R.id.category);
        Description = findViewById(R.id.we);
        PurchasePrice = findViewById(R.id.purchase);
        ProductImage = findViewById(R.id.productImage);
        Back = findViewById(R.id.back);
        Add = findViewById(R.id.add);
        AddedAmount = findViewById(R.id.addedAmount);
        Shopping = findViewById(R.id.shopping);
        DescriptionSpinner = findViewById(R.id.descriptionSpinner);
        ScanBarcode = findViewById(R.id.scan);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                List<String> productList = new ArrayList<>();
                productList = db.getAllProductsName();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SellProducts.this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, productList);
                ProductName.setAdapter(adapter);
            }
        });
        ProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!ProductName.getText().toString().isEmpty()){
                    fillDataFromProduct(ProductName.getText().toString());
                }
            }
        });
        ProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                moreExists = false;
            }
        });
        ProductName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(ProductName.getText().toString().isEmpty()){
                    sellingPricePerItem = 0;
                    clearFilledData();
                }else{
                    if(!moreExists){
                        if(db.checkProductName(ProductName.getText().toString())){
                            fillDataFromProduct(ProductName.getText().toString());
                        }else{
                            sellingPricePerItem = 0;
                            clearFilledData();
                        }
                    }
                }
            }
        });
        Amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(sellingPricePerItem>0){
                    if(!Amount.getText().toString().isEmpty()){
                        if(Integer.parseInt(Amount.getText().toString())>Max_Amount){
                            Amount.setText("");
                            Snackbar.make(view, "Maximum amount is "+Max_Amount, Snackbar.LENGTH_LONG).setAction(null, null).show();
                        }
                    }
                }
                return false;
            }
        });
        Amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                if(!Amount.getText().toString().isEmpty()){
                    if(sellingPricePerItem>0){
                        if(Integer.parseInt(Amount.getText().toString())>0){
                            int amt = Integer.parseInt(Amount.getText().toString());
                            SellingPrice.setText(Integer.toString(sellingPricePerItem*amt));
                        }
                    }
                }else{
                    SellingPrice.setText("");
                }
            }
        });
        DescriptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!DescriptionSpinner.getSelectedItem().toString().isEmpty()){
                    String description;
                    description = DescriptionSpinner.getSelectedItem().toString();
                    productDescription = description;
                    Description.setText("Description: "+productDescription);
                    fillFromDescriptionSpinner(ProductName.getText().toString(), description);
                    updatePrice();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if(checkData()){
                    if(CheckProduct()){
                        if(Integer.parseInt(Amount.getText().toString())<=0){
                            Snackbar.make(view, "Amount cannot be 0", Snackbar.LENGTH_LONG).show();
                        }else{
                            db.addProductTempTable(ProductCategory, ProductName.getText().toString(), productDescription, Integer.parseInt(Amount.getText().toString()
                            ), db.getProductId(ProductName.getText().toString(), productDescription), Integer.parseInt(SellingPrice.getText().toString()));

                            shoppingAmount = db.getTempProductCount();
                            AddedAmount.setText(Integer.toString(shoppingAmount));

                            clearFilledData();
                            ProductName.setText("");
                        }
                    }else{
                        new MessageDialog("Error", "Product not found", "productNotFound", "ok").show(getSupportFragmentManager(),"ProductNotFound");
                    }
                }else{
                    Snackbar.make(view, "Fill all th fields", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        ScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(SellProducts.this);
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
        Shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shoppingAmount>0){
                    Intent intent = new Intent(SellProducts.this, ShoppingData.class);
                    startActivity(intent);
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                Barcode.setText(code);
                fillFromCode(code);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @SuppressLint("SetTextI18n")
    public void onStart(){
        super.onStart();
        checkTempTableProducts();
        db.createTempTable();
        shoppingAmount = db.getTempProductCount();
        AddedAmount.setText(Integer.toString(shoppingAmount));
    }
    @SuppressLint("SetTextI18n")
    private void fillDataFromProduct(String productName){
        DescriptionSpinner.setVisibility(View.INVISIBLE);
        productLists = new ArrayList<>();
        int id = db.getProductId(productName);
        productLists = db.getSpecificProducts(id);
        for(ProductList productList: productLists){
            Barcode.setText(productList.getCode());
            Max_Amount = productList.getAmount()-calculateRemainingProductAmount(productList.getProductName(), productList.getDescription());
            sellingPricePerItem = productList.getSellingPrice();
            Category.setText("Category: "+productList.getCategory());
            this.ProductCategory = productList.getCategory();
            productDescription = productList.getDescription();
            Description.setText("Description: "+productList.getDescription());
            PurchasePrice.setText("Purchase price per item: "+Integer.toString(productList.getPurchasePrice()));
        }
        new Handler().post(new Runnable(){
            @Override
            public void run() {
                ProductImage.setImageBitmap(convertByteToImage(db.getProductImage(id)));
                if(db.checkDescriptionNumber(productName)){
                    showSpinner(productName);
                    moreExists = true;
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void fillFromCode(String code){
        DescriptionSpinner.setVisibility(View.INVISIBLE);
        productLists = new ArrayList<>();
        int id = db.getProductsIdFromCode(code);
        productLists = db.getSpecificProducts(id);
        for(ProductList productList: productLists){
            ProductName.setText(productList.getProductName());
            Barcode.setText(productList.getCode());
            Max_Amount = productList.getAmount()-calculateRemainingProductAmount(productList.getProductName(), productList.getDescription());
            sellingPricePerItem = productList.getSellingPrice();
            Category.setText("Category: "+productList.getCategory());
            this.ProductCategory = productList.getCategory();
            productDescription = productList.getDescription();
            Description.setText("Description: "+productList.getDescription());
            PurchasePrice.setText("Purchase price per item: "+Integer.toString(productList.getPurchasePrice()));
            Amount.setText("1");
        }
        new Handler().post(new Runnable(){
            @Override
            public void run() {
                ProductImage.setImageBitmap(convertByteToImage(db.getProductImage(id)));
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void fillFromDescriptionSpinner(String productName, String description){
        productLists = new ArrayList<>();
        int id = db.getProductId(productName, description);
        productLists = db.getSpecificProducts(id);
        for(ProductList productList: productLists){
            Barcode.setText(productList.getCode());
            Max_Amount = productList.getAmount();
            sellingPricePerItem = productList.getSellingPrice();
            Category.setText("Category: "+productList.getCategory());
            Description.setText("Description: "+productList.getDescription());
            productDescription = productList.getDescription();
            PurchasePrice.setText("Purchase price per item: "+Integer.toString(productList.getPurchasePrice()));
        }
        new Handler().post(new Runnable(){
            @Override
            public void run() {
                ProductImage.setImageBitmap(convertByteToImage(db.getProductImage(id)));
            }
        });
    }
    private void showSpinner(String productName){
        DescriptionSpinner.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SellProducts.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                db.getAllProductDescriptions(productName));
        DescriptionSpinner.setAdapter(adapter);
    }
    private Bitmap convertByteToImage(byte[] b){
        InputStream inputStream = new ByteArrayInputStream(b);
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }
    @SuppressLint("SetTextI18n")
    private void updatePrice(){
        if(!Amount.getText().toString().isEmpty()){
            if(sellingPricePerItem>0){
                int amt = Integer.parseInt(Amount.getText().toString());
                SellingPrice.setText(Integer.toString(sellingPricePerItem*amt));
            }
        }
    }
    private boolean checkData(){
        return !ProductName.getText().toString().isEmpty() && !Amount.getText().toString().isEmpty() && !SellingPrice.getText().toString().isEmpty();
    }
    @SuppressLint("SetTextI18n")
    private void clearFilledData(){
        Amount.setText("");
        Barcode.setText("");
        SellingPrice.setText("");
        Category.setText("Category:");
        Description.setText("Description:");
        PurchasePrice.setText("Purchase price per item:");
        ProductImage.setImageBitmap(null);
        DescriptionSpinner.setVisibility(View.INVISIBLE);
        DescriptionSpinner.setAdapter(null);
        moreExists = false;
    }
    private boolean CheckProduct(){
        boolean found = false;
        if(!ProductName.getText().toString().isEmpty()){
            if(db.checkProductName(ProductName.getText().toString())){
                found = true;
            }
        }
        return found;
    }
    public int calculateRemainingProductAmount(String productName, String description){
        int amt = db.getAmountFromTempTable(productName, description);
        return amt;
    }
    public void checkTempTableProducts(){
        if(db.checkTempProductsTable()){
            new MessageDialog("Message", "Some data have not been saved", "tempProductTableFound", "Save").show(getSupportFragmentManager(),"ProductNotFound");
        }
    }
}
