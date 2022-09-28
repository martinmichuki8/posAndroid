package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.AddDevices;
import com.michtech.pointofSale.Ui.more.devices.LinkedDevices;
import com.michtech.pointofSale.Ui.more.employee.AddEmployee;
import com.michtech.pointofSale.Ui.more.employee.PaymentHistory;
import com.michtech.pointofSale.Ui.more.employee.ViewEmployee;
import com.michtech.pointofSale.Ui.more.other.OtherExpenses;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.Ui.No.NoEmployees;
import com.michtech.pointofSale.Ui.No.NoProductsFound;
import com.michtech.pointofSale.Ui.more.employee.PayEmployee;
import com.michtech.pointofSale.Ui.more.store.ProductsTrend;
import com.michtech.pointofSale.Ui.more.store.SellProducts;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoMore;

import java.util.List;

public class AdapterMore extends BaseAdapter {
    Context context;
    List<PojoMore> list;
    DatabaseManager db;
    public AdapterMore(Context context, List<PojoMore> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.more_list, viewGroup, false);
        db = new DatabaseManager(context);

        ImageButton Image = view.findViewById(R.id.image);
        TextView Title = view.findViewById(R.id.title);

        Image.setImageResource(list.get(position).getImage());
        Title.setText(list.get(position).getTitle());

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (list.get(position).getFunction()){
                    case "AddProducts":
                        intent = new Intent(context, AddProducts.class);
                        intent.putExtra("TYPE", "no");
                        intent.putExtra("ID", "0");
                        context.startActivity(intent);
                        break;
                    case "SellProducts":
                        if(db.CheckProducts()<1){
                            intent = new Intent(context, NoProductsFound.class);
                        }else{
                            intent = new Intent(context, SellProducts.class);
                        }
                        context.startActivity(intent);
                        break;
                    case "trend":
                        intent = new Intent(context, ProductsTrend.class);
                        context.startActivity(intent);
                        break;
                    case "paymentHistory":
                        intent = new Intent(context, PaymentHistory.class);
                        context.startActivity(intent);
                        break;
                    case "ViewEmployees":
                        if(db.checkEmployees()<1){
                            intent = new Intent(context, NoEmployees.class);
                        }else{
                            intent = new Intent(context, ViewEmployee.class);
                        }
                        context.startActivity(intent);
                        break;
                    case "PayEmployees":
                        if(db.checkEmployees()<1){
                            intent = new Intent(context, NoEmployees.class);
                        }else{
                            intent = new Intent(context, PayEmployee.class);
                        }
                        context.startActivity(intent);
                        break;
                    case "other":
                        intent = new Intent(context, OtherExpenses.class);
                        context.startActivity(intent);
                        break;
                    case "AddDevice":
                        intent = new Intent(context, AddDevices.class);
                        context.startActivity(intent);
                        break;
                    case "Linked":
                        intent = new Intent(context, LinkedDevices.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });

        return view;
    }
}
