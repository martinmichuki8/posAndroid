package com.michtech.pointofSale.Ui.more.employee.paymentHistory;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.adapter.AdapterEmployeePayment;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.database.DbHelper;
import com.michtech.pointofSale.pojo.PojoEmployeePaymentList;

import java.util.ArrayList;
import java.util.List;

public class EmployeePaymentHistory extends AppCompatActivity {
    TextView Name;
    ListView listView;
    AdapterEmployeePayment adapterEmployeePayment;
    List<PojoEmployeePaymentList> list;
    List<DbHelper> dbHelperList;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_payment_history);

        db = new DatabaseManager(EmployeePaymentHistory.this);

        Name = findViewById(R.id.name);
        listView = findViewById(R.id.listView);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        String name = bundle.getString("NAME");
        Name.setText(name);

        showList(name);
        adapterEmployeePayment = new AdapterEmployeePayment(EmployeePaymentHistory.this, list);
        listView.setAdapter(adapterEmployeePayment);
    }
    private void showList(String name){
        list = new ArrayList<>();
        dbHelperList = new ArrayList<>();
        dbHelperList = db.getEmployeePaymentList(name);
        for (DbHelper dbHelper: dbHelperList){
            list.add(new PojoEmployeePaymentList(dbHelper.getId(), dbHelper.getDDate(), dbHelper.getAmount(),
                    dbHelper.getStartDate(), dbHelper.getEndDate()));
        }
    }
}
