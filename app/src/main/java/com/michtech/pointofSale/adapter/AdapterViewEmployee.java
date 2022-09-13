package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoViewEmployee;


import java.util.List;

public class AdapterViewEmployee extends BaseAdapter {
    Context context;
    List<PojoViewEmployee> list;
    DatabaseManager db;
    public AdapterViewEmployee(Context context, List<PojoViewEmployee> list){
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
        view = LayoutInflater.from(context).inflate(R.layout.employee_list, viewGroup, false);

        TextView Name = view.findViewById(R.id.description);
        TextView Phone = view.findViewById(R.id.name);

        Name.setText(list.get(position).getName());
        Phone.setText(list.get(position).getPhone());

        return view;
    }
}
