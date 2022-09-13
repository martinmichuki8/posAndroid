package com.michtech.pointofSale.Ui.more.other;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterOtherExpense;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoOtherExpense;

import java.util.ArrayList;
import java.util.List;

public class OtherExpenses extends AppCompatActivity {
    ListView listView;
    FloatingActionButton Add;
    TextView TotalAmount;

    List<PojoOtherExpense> pojoOtherExpenseList;
    List<DbHelper> dbHelperList;
    AdapterOtherExpense adapterOtherExpense;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_expenses);

        db = new DatabaseManager(OtherExpenses.this);

        listView = findViewById(R.id.listView);
        Add = findViewById(R.id.add);
        TotalAmount = findViewById(R.id.Amount);

        new Handler().post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                showList();
                adapterOtherExpense = new AdapterOtherExpense(OtherExpenses.this, pojoOtherExpenseList);
                listView.setAdapter(adapterOtherExpense);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherExpenses.this, AddOtherExpenses.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void onStart(){
        super.onStart();
        TotalAmount.setText(Integer.toString(db.getOtherExpenseTotalAmount()));
    }
    private void showList(){
        pojoOtherExpenseList = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getOtherExpensesList();
        for(DbHelper helper: dbHelperList){
            pojoOtherExpenseList.add(new PojoOtherExpense(helper.getId(), helper.getType(), helper.getDDate(), helper.getAmount(), helper.getImageCode()));
        }
    }
}
