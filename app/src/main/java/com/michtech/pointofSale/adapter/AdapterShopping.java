package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.pojo.PojoShopping;

import java.util.List;

public class AdapterShopping extends BaseAdapter {
    Context context;
    List<PojoShopping> list;
    public AdapterShopping(Context context, List<PojoShopping> list){
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
        view = LayoutInflater.from(context).inflate(R.layout.store_list, viewGroup, false);
        TextView Product = view.findViewById(R.id.product);
        TextView Amount = view.findViewById(R.id.amount);
        TextView TotalPrice = view.findViewById(R.id.we);
        TextView Category = view.findViewById(R.id.category);

        Product.setText(list.get(position).getProduct());
        Amount.setText(Integer.toString(list.get(position).getAmount()));
        TotalPrice.setText(Integer.toString(list.get(position).getTotalPrice()));
        Category.setText(list.get(position).getCategory());

        return view;
    }
}
