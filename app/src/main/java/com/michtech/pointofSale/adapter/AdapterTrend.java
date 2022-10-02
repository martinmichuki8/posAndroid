package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.trend.ProductTrend;
import com.michtech.pointofSale.pojo.PojoTrend;

import java.util.List;

public class AdapterTrend extends BaseAdapter {
    Context context;
    List<PojoTrend> list;

    public AdapterTrend(Context context, List<PojoTrend> list){
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
        view = LayoutInflater.from(context).inflate(R.layout.trend_list, viewGroup, false);

        TextView ProductName = view.findViewById(R.id.productName);
        TextView Quantity = view.findViewById(R.id.quantity);

        ProductName.setText(list.get(position).getProductName());
        Quantity.setText(Integer.toString(list.get(position).getQuantity()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductTrend.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
