package com.michtech.pointofSale.duplicates;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterDuplicates;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.functions.DuplicateProducts;
import com.michtech.pointofSale.functions.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DuplicateProductsList extends AppCompatActivity implements AdapterDuplicates.Listener{
    ImageButton RelatedBack;
    Button Merge;
    ListView listView;
    List<Integer> Ids;
    List<Integer> SelectedIds = new ArrayList<>();
    List<DuplicateProducts> duplicateProductsList;
    AdapterDuplicates adapterDuplicates;
    DatabaseManager db;
    Functions functions;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplicate_product_list);

        db = new DatabaseManager(DuplicateProductsList.this);
        functions = new Functions(DuplicateProductsList.this);

        RelatedBack = findViewById(R.id.relatedBack);
        listView = findViewById(R.id.listView);
        Merge = findViewById(R.id.merege);

        setData();

        RelatedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Merge.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                if(SelectedIds.size()==0){
                    Snackbar.make(view, "Nothing selected", Snackbar.LENGTH_LONG).setAction(null, null).show();
                }else{
                    mergeData();
                    //finish();
                    recreate();
                }
            }
        });
    }
    private void setData(){
        showList();
        adapterDuplicates = new AdapterDuplicates(DuplicateProductsList.this, duplicateProductsList);
        listView.setAdapter(adapterDuplicates);
    }
    private void showList(){
        duplicateProductsList = new ArrayList<>();
        Ids = new ArrayList<>();

        for(DuplicateProducts duplicateProducts: functions.getDuplicates()){
            int amount = 0;
            //System.out.println("product: "+duplicateProducts.getProductName());
            for(Integer ids: duplicateProducts.getIdList()){
                //System.out.println(ids);
                amount += db.getProductAmount(ids);
            }
            System.out.println("Amount: "+amount);
            duplicateProductsList.add(new DuplicateProducts(duplicateProducts.getProductName(),
                    db.getCategory(db.getProductCategoryId(duplicateProducts.getIdList().get(0))), amount,
                    duplicateProducts.getIdList().get(0), duplicateProducts.getIdList()));
        }
    }
    @Override
    public void onSomeEvent(int ID, @NonNull String action) {
        if(action.equals("Add")){
            SelectedIds.add(ID);
            System.out.println("Selected: "+ID);
        }else{
            SelectedIds.remove(Integer.valueOf(ID));
        }
    }
    private void mergeData(){
        duplicateProductsList = new ArrayList<>();
        for(DuplicateProducts duplicateProducts: functions.getDuplicates()){
            int amount = 0;
            for(Integer ids: SelectedIds){
                if(duplicateProducts.getIdList().get(0)==ids){
                    for(Integer id: duplicateProducts.getIdList()){
                        amount += db.getProductAmount(id);
                    }
                    db.updateProductAmount(amount, ids);
                    deleteMerged(duplicateProducts.getIdList(), ids);
                }
            }
        }
    }
    private void deleteMerged(@NonNull List<Integer> ids, int IdSkip){
        for(Integer id: ids){
            if(IdSkip==id){
                //continue;
            }else{
                db.deleteProduct(id);
            }
        }
    }
}
