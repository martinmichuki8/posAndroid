package com.michtech.pointofSale.duplicates;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterDuplicates;
import com.michtech.pointofSale.functions.DuplicateProducts;

import java.util.ArrayList;
import java.util.List;

public class DuplicateProductsList extends AppCompatActivity implements AdapterDuplicates.Listener{
    ImageButton RelatedBack;
    Button Merge;
    ListView listView;
    List<Integer> Ids;
    List<Integer> SelectedIds = new ArrayList<>();
    List<DuplicateProducts> duplicateProductsList;
    AdapterDuplicates adapterDuplicates;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplicate_product_list);

        RelatedBack = findViewById(R.id.relatedBack);
        listView = findViewById(R.id.listView);
        Merge = findViewById(R.id.merege);

        showList();
        adapterDuplicates = new AdapterDuplicates(DuplicateProductsList.this, duplicateProductsList);
        listView.setAdapter(adapterDuplicates);

        RelatedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Integer ids: SelectedIds){
                    System.out.println(ids);
                }
            }
        });
    }
    private void showList(){
        duplicateProductsList = new ArrayList<>();
        Ids = new ArrayList<>();
        Ids.add(1);
        Ids.add(12);
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 1, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 2, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 3, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 4, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 5, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 6, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 7, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 8, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 9, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 10, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 11, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 12, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 13, Ids));
        duplicateProductsList.add(new DuplicateProducts("Lemonade", "Drink", 20, 14, Ids));
    }

    @Override
    public void onSomeEvent(int ID, @NonNull String action) {
        if(action.equals("Add")){
            SelectedIds.add(ID);
        }else{
            SelectedIds.remove(Integer.valueOf(ID));
        }
    }
}
