package com.michtech.pointofSale.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.ViewProduct2;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.activityPortrait.CaptureActivityPortrait;
import com.michtech.pointofSale.adapter.ProductsAdapter;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.functions.Functions;
import com.michtech.pointofSale.pojo.PojoProducts;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store extends Fragment {

    FloatingActionButton Add;
    ListView listView;
    List<PojoProducts> list;
    ProductsAdapter productsAdapter;
    ImageView SearchWithCode;
    ImageButton StoreMenu;
    SearchView Search;
    TextView Visible;
    ConstraintLayout MergeNotification;
    private String ListType = "Products";

    List<String> categories = new ArrayList<>();

    Functions functions;
    List<DbHelper> helperList;
    DatabaseManager db;

    public interface Listener{
        void onSomeEvent(String s);
    }
    private void sendDataToActivity(String s){
        ((Listener) getActivity()).onSomeEvent(s);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store, container, false);

        Add = view.findViewById(R.id.add);
        listView = view.findViewById(R.id.listView);
        SearchWithCode = view.findViewById(R.id.searchWithCode);
        StoreMenu = view.findViewById(R.id.storeMenu);
        Search = view.findViewById(R.id.search);
        Visible = view.findViewById(R.id.visible);
        MergeNotification = view.findViewById(R.id.mergeNotification);

        if(!functions.checkMerge()){
            MergeNotification.setVisibility(View.INVISIBLE);
        }

        db = new DatabaseManager(getContext());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showList();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProducts.class);
                intent.putExtra("TYPE", "no");
                intent.putExtra("ID", "0");
                startActivity(intent);
            }
        });
        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        SearchWithCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(ListType.equals("Categories")){
                    productCategory(categories.get(i));
                    System.out.println("Category: "+categories.get(i));
                }
            }
        });

        StoreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), StoreMenu);
                popupMenu.inflate(R.menu.store_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.products:
                                products();
                                break;
                            case R.id.categories:
                                categories();
                                break;
                            case R.id.recycle_bin:
                                RecycleBin();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        return view;
    }
    public void onStart(){
        super.onStart();
        switch(ListType){
            case "Products":
                products();
                break;
            case "Categories":
                categories();
                break;
            case "RecycleBin":
                RecycleBin();
                break;
        }
    }
    private void products(){
        showList();
        ListType = "Products";
        sendDataToActivity("Products");
        listView.setDivider(null);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
        params.leftMargin = 0; params.rightMargin = 0;
        Visible.setText(ListType);
    }
    private void categories(){
        setCategories();
        ListType = "Categories";
        ColorDrawable divider = new ColorDrawable(getContext().getResources().getColor(R.color.yellow));
        listView.setDivider(divider);
        listView.setDividerHeight(1);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
        params.leftMargin = 16; params.rightMargin = 16;
        Visible.setText(ListType);
    }
    private void RecycleBin(){
        showRecycleBinList();
        ListType = "RecycleBin";
        sendDataToActivity("RecycleBin");
        listView.setDivider(null);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
        params.leftMargin = 0; params.rightMargin = 0;
        Visible.setText(ListType);
    }
    private void productCategory(String category){
        showProductsInCategory(category);
        ListType = "Products";
        listView.setDivider(null);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listView.getLayoutParams();
        params.leftMargin = 0; params.rightMargin = 0;
        Visible.setText(category);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                if(db.checkProductFromCode(code)){
                    Intent intent = new Intent(getContext(), ViewProduct2.class);
                    intent.putExtra("ID", db.getProductsIdFromCode(code));
                    intent.putExtra("LIST_TYPE", ListType);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Product not found", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showList(){
        listShow();
        productsAdapter = new ProductsAdapter(getContext(), list, "Products");
        listView.setAdapter(productsAdapter);
    }
    private void listShow() {
        list = new ArrayList<>();
        helperList = new ArrayList<>();
        helperList = db.getProductList();

        for(DbHelper helper: helperList){
            list.add(new PojoProducts(helper.getProductsName(), helper.getCategory(), helper.getDDate(), helper.getAmount(), helper.getId()));
        }
    }
    private void showRecycleBinList(){
        listShowBin();
        productsAdapter = new ProductsAdapter(getContext(), list, "RecycleBin");
        listView.setAdapter(productsAdapter);
    }
    private void listShowBin(){
        list = new ArrayList<>();
        helperList = new ArrayList<>();
        helperList = db.getRemovedProductList();

        for(DbHelper helper: helperList){
            list.add(new PojoProducts(helper.getProductsName(), helper.getCategory(), helper.getDDate(), helper.getAmount(), helper.getId()));
        }
    }
    private void setCategories(){
        categories = db.getAllCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter <String>(getContext(),
                com.anychart.R.layout.support_simple_spinner_dropdown_item,
                categories);
        listView.setAdapter(adapter);
    }
    private void showProductsInCategory(String category){
        list = new ArrayList<>();
        helperList = new ArrayList<>();
        helperList = db.getProductList(db.getCategoryId(category));
        for(DbHelper helper: helperList){
            list.add(new PojoProducts(helper.getProductsName(), helper.getCategory(), helper.getDDate(), helper.getAmount(), helper.getId()));
        }
        productsAdapter = new ProductsAdapter(getContext(), list, "Products");
        listView.setAdapter(productsAdapter);
    }
}
