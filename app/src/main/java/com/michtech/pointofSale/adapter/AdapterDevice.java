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
import com.michtech.pointofSale.Ui.more.devices.ConnectDevice;
import com.michtech.pointofSale.pojo.PojoDevices;

import java.util.List;

public class AdapterDevice extends BaseAdapter {
    Context context;
    List<PojoDevices> list;
    public AdapterDevice(Context context, List<PojoDevices> list){
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
        view = LayoutInflater.from(context).inflate(R.layout.device_list, viewGroup, false);
        TextView DeviceUser = view.findViewById(R.id.name);

        DeviceUser.setText(list.get(position).getDeviceUser());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConnectDevice.class);
                intent.putExtra("ID", list.get(position).getId());
                intent.putExtra("USER", list.get(position).getDeviceUser());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
