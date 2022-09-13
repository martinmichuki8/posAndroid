package com.michtech.pointofSale.Ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.adapter.ProductsAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.dialog.ConfirmDelete;
import com.michtech.pointofSale.list.ProductList;
import com.michtech.pointofSale.pojo.PojoProducts;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ViewProduct2 extends AppCompatActivity {
    TextView Product, Amount, Category, Barcode, Description, PurchasePrice, SellingPrice, DDate;
    TextView ProductExpiryDate;
    TextInputLayout ExpiryDate;
    TextInputEditText ExpDate;
    ImageButton Home;
    ListView listView;
    ImageView ProductImage;
    ImageView ProductMenu;
    private int id = 0;
    private String listType = "Products";

    DatePickerDialog.OnDateSetListener onDateSetListener;

    List<ProductList> list;
    List<PojoProducts> productsList;
    List<DbHelper> helperList;
    ProductsAdapter productsAdapter;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product2);
        db = new DatabaseManager(ViewProduct2.this);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getInt("ID");
        listType = bundle.getString("LIST_TYPE");

        Product = findViewById(R.id.product);
        Amount = findViewById(R.id.amount);
        Category = findViewById(R.id.category);
        Barcode = findViewById(R.id.code);
        Description = findViewById(R.id.Description);
        PurchasePrice = findViewById(R.id.purchasePrice);
        SellingPrice = findViewById(R.id.buyingPrice);
        DDate = findViewById(R.id.we);
        listView = findViewById(R.id.listView);
        ProductImage = findViewById(R.id.productImage);
        ProductMenu = findViewById(R.id.menu);
        ProductExpiryDate = findViewById(R.id.expDate);

        Home = findViewById(R.id.home);

        setData();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showExpiryDate();
                switch (listType){
                    case "Products":
                        showList();
                        break;
                    case "RecycleBin":
                        showRecycleBinList();
                        break;
                }
            }
        });

        onDateSetListener = (datePicker, year1, month1, day1) -> {
            String date;
            if(month1 <10){
                date = year1 +"-0"+ month1 +"-"+ day1;
            }else{
                date = year1 +"-"+ month1 +"-"+ day1;
            }
            Objects.requireNonNull(ExpiryDate.getEditText()).setText(date);
        };

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                switch (listType){
                    case "Products":
                        ProductImage.setImageBitmap(convertByteToImage(db.getProductImage(id)));
                        break;
                    case "RecycleBin":
                        ProductImage.setImageBitmap(convertByteToImage(db.getProductImageRecycleBin(id)));
                        break;
                }
            }
        });

        ProductMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), ProductMenu);
                switch (listType){
                    case "Products":
                        popupMenu.inflate(R.menu.product_menu);
                        break;
                    case "RecycleBin":
                        popupMenu.inflate(R.menu.recycle_bin_menu);
                        break;
                }
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                Intent intent = new Intent(ViewProduct2.this, AddProducts.class);
                                intent.putExtra("TYPE", "Update");
                                intent.putExtra("ID", id);
                                startActivity(intent);
                                break;
                            case R.id.changeAmount:
                                setChangeAmountDialog();
                                break;
                            case R.id.delete:
                                new ConfirmDelete(id).show(getSupportFragmentManager(), "DeleteProduct");
                                break;
                            case R.id.expiry:
                                setExpiryDateDialog();
                                break;
                            case R.id.restore:
                                restoreProducts();
                                finish();
                                break;
                            case R.id.deleteComplete:
                                db.deleteFromRecycleBin(id);
                                finish();
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void setData(){
        list = new ArrayList<>();
        switch (listType){
            case "Products":
                list = db.getSpecificProducts(id);
                break;
            case "RecycleBin":
                list = db.getSpecificProductFromRecycleBin(id);
                break;
        }
        for(ProductList productList: list){
            Category.setText(productList.getCategory());
            Product.setText(productList.getProductName());
            Amount.setText(Integer.toString(productList.getAmount()));
            Barcode.setText(productList.getCode());
            PurchasePrice.setText("Purchase price per item: "+Integer.toString(productList.getPurchasePrice()));
            SellingPrice.setText("Selling price per item: "+Integer.toString(productList.getSellingPrice()));
            DDate.setText(productList.getDDate());
            Description.setText("Description: "+productList.getDescription());
        }
    }
    private void showList(){
        listShow();
        productsAdapter = new ProductsAdapter(ViewProduct2.this, productsList, listType);
        listView.setAdapter(productsAdapter);
    }
    private void listShow() {
        productsList = new ArrayList<>();
        helperList = new ArrayList<>();
        helperList = db.getProductList();

        for(DbHelper helper: helperList){
            productsList.add(new PojoProducts(helper.getProductsName(), helper.getCategory(), helper.getDDate(), helper.getAmount(), helper.getId()));
        }
    }
    private void showRecycleBinList(){
        listShowBin();
        productsAdapter = new ProductsAdapter(ViewProduct2.this, productsList, "RecycleBin");
        listView.setAdapter(productsAdapter);
    }
    private void listShowBin(){
        productsList = new ArrayList<>();
        helperList = new ArrayList<>();
        helperList = db.getRemovedProductList();

        for(DbHelper helper: helperList){
            productsList.add(new PojoProducts(helper.getProductsName(), helper.getCategory(), helper.getDDate(), helper.getAmount(), helper.getId()));
        }
    }
    private Bitmap convertByteToImage(byte[] b){
        InputStream inputStream = new ByteArrayInputStream(b);
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }
    private void setChangeAmountDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProduct2.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ViewProduct2.this).inflate(R.layout.change_amount, viewGroup, false);

        TextInputLayout amount = dialogView.findViewById(R.id.expiryDate);
        TextView Cancel = dialogView.findViewById(R.id.cancel);
        TextView Update = dialogView.findViewById(R.id.update);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                if(amount.getEditText().getText().toString().isEmpty()){
                    Snackbar.make(view, "Amount cannot be empty or 0", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else {
                    calculateChangedAmount(Integer.parseInt(amount.getEditText().getText().toString()));
                    db.updateProductAmount(Integer.parseInt(amount.getEditText().getText().toString()), id);
                    alertDialog.dismiss();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            recreate();
                        }
                    });
                }
            }
        });
    }
    private void setExpiryDateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProduct2.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ViewProduct2.this).inflate(R.layout.set_expiry_date, viewGroup, false);

        ExpiryDate = dialogView.findViewById(R.id.expiryDate);
        ExpDate = dialogView.findViewById(R.id.expDate);
        TextView Cancel = dialogView.findViewById(R.id.cancel);
        TextView Update = dialogView.findViewById(R.id.update);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewProduct2.this, onDateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
        Cancel.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        Update.setOnClickListener(view -> {
            if(ExpiryDate.getEditText().getText().toString().isEmpty()){
                Snackbar.make(view, "Date cannot be empty", Snackbar.LENGTH_SHORT).setAction(null, null).show();
            }else{
                db.AddExpiry(id, ExpiryDate.getEditText().getText().toString(), db.searchExpiryExists(id));
                alertDialog.dismiss();
            }
        });
    }
    private void calculateChangedAmount(int newAmount){
        int nAmount = newAmount-db.getProductAmount(id);
        if(nAmount<0){
            nAmount = -nAmount;
            saveRemoved(nAmount);
        }else if(nAmount==0){
            // don't save
        }else{
            saveHistory(nAmount);
        }
    }
    private void saveRemoved(int newAmount){
        DatabaseManager db = new DatabaseManager(ViewProduct2.this);
        for (ProductList productList : db.getSpecificProducts(id)) {
            db.addRemovedProducts(productList.getProductName(), productList.getDescription(), newAmount, productList.getCategory(), productList.getPurchasePrice(),
                    productList.getSellingPrice(), productList.getCode(), "Updated", productList.getSellingPrice()*newAmount, db.getProductImage(id), getDate());
        }

    }
    private void saveHistory(int newAmount){
        DatabaseManager db = new DatabaseManager(ViewProduct2.this);
        for (ProductList productList : db.getSpecificProducts(id)) {
            db.saveToProductHistory(productList.getProductName(), productList.getDescription(), newAmount, productList.getCategory(), productList.getPurchasePrice(),
                    productList.getSellingPrice(), productList.getCode(), "purchased", productList.getSellingPrice()*newAmount, db.getProductImage(id), getDate());
        }

    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
    @SuppressLint("SetTextI18n")
    private void showExpiryDate(){
        if(listType.equals("Products")){
            if(db.searchExpiryExists(id)){
                ProductExpiryDate.setText("Exp: "+db.getExpiryDate(id));
                ProductExpiryDate.setBackgroundResource(R.color.yellow);
                ProductExpiryDate.setTextColor(Color.WHITE);
                ProductExpiryDate.setPadding(5, 0, 5, 0);
            }
        }
    }
    private void restoreProducts(){
        List<ProductList> productListList = db.getSpecificProductFromRecycleBin(id);
        for (ProductList productList: productListList){
            UpdateProduct(productList.getCategory(), productList.getProductName(), productList.getPurchasePrice(), productList.getSellingPrice(),
                    productList.getDescription(), productList.getAmount(), productList.getCode(), db.getProductImageRecycleBin(id));
        }
        db.deleteFromRecycleBin(id);
    }
    private void UpdateProduct(String category, String product, int purchasePrice, int sellingPrice, String description, int amount, String barcode,
                               byte[] productImage){
        if(!db.checkCategory(category)){
            db.AddCategory(category);
        }
        db.AddPrice(purchasePrice, sellingPrice);
        db.AddProducts(product, description, amount, db.getCurrentCategoryId(category), db.getCurrentPriceId(purchasePrice, sellingPrice), barcode, getDate(), productImage);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.saveToProductHistory(product, description, amount, category, purchasePrice, sellingPrice, barcode, "purchased", 0, productImage, getDate());
            }
        });
    }
}
