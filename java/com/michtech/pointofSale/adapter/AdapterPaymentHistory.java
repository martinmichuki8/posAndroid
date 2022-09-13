package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.employee.paymentHistory.EmployeePaymentHistory;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoPaymentHistory;


import java.util.List;

public class AdapterPaymentHistory extends BaseAdapter {
    Context context;
    List<PojoPaymentHistory> list;
    DatabaseManager db;
    public AdapterPaymentHistory(Context context, List<PojoPaymentHistory> list){
        this.context = context;
        this.list = list;
        db = new DatabaseManager(context);
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

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.payment_history_list, viewGroup, false);

        TextView Name = view.findViewById(R.id.description);
        TextView Phone = view.findViewById(R.id.name);
        ImageView Check = view.findViewById(R.id.check);

        Name.setText(list.get(position).getName());
        Phone.setText(list.get(position).getNumber());

        if(!list.get(position).getFound()){
            Check.setBackgroundResource(R.drawable.red_circle);
            Check.setImageResource(R.drawable.ic_baseline_close_24);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EmployeePaymentHistory.class);
                intent.putExtra("NAME", list.get(position).getName());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
