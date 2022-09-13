package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.pojo.PojoEmployeePaymentList;

import java.util.List;

public class AdapterEmployeePayment extends BaseAdapter {
    Context context;
    List<PojoEmployeePaymentList> list;
    public AdapterEmployeePayment(Context context, List<PojoEmployeePaymentList> list){
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
        view = LayoutInflater.from(context).inflate(R.layout.employee_payment_history_list, viewGroup, false);
        TextView DDate = view.findViewById(R.id.date);
        TextView Amount = view.findViewById(R.id.amount);

        DDate.setText(list.get(position).getDDate());
        Amount.setText(Integer.toString(list.get(position).getAmount()));
        return view;
    }
}
