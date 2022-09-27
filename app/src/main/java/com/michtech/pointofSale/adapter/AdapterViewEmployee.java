package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
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
    Activity activity;
    List<PojoViewEmployee> list;
    DatabaseManager db;
    public AdapterViewEmployee(Activity activity, List<PojoViewEmployee> list){
        this.activity = activity;
        this.list = list;
        db = new DatabaseManager(activity);
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
        view = LayoutInflater.from(activity).inflate(R.layout.employee_list, viewGroup, false);

        TextView Name = view.findViewById(R.id.description);
        TextView Phone = view.findViewById(R.id.name);

        Name.setText(list.get(position).getName());
        Phone.setText(list.get(position).getPhone());

        activity.registerForContextMenu(view);

        return view;
    }
}
