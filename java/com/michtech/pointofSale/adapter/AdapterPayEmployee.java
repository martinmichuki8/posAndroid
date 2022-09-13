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
import com.michtech.pointofSale.Ui.more.employee.PaymentDashboard;
import com.michtech.pointofSale.pojo.PojoPayEmployee;

import java.util.List;

public class AdapterPayEmployee extends BaseAdapter {
    Context context;
    List<PojoPayEmployee> list;

    public AdapterPayEmployee(Context context, List<PojoPayEmployee> list){
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

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.employee_pay_list, viewGroup, false);

        TextView Name = view.findViewById(R.id.description);
        TextView Phone = view.findViewById(R.id.name);
        ImageView Check = view.findViewById(R.id.check);

        Name.setText(list.get(position).getName());
        Phone.setText(list.get(position).getPhone());
        if(list.get(position).getPaid()){
            Check.setBackgroundResource(R.drawable.circle);
            Check.setImageResource(R.drawable.ic_baseline_done_outline_24);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!list.get(position).getPaid()){
                    Intent intent = new Intent(context, PaymentDashboard.class);
                    intent.putExtra("ID", list.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });

        return view;
    }
}
