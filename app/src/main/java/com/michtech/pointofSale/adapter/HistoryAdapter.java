package com.michtech.pointofSale.adapter;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.ViewProduct;
import com.michtech.pointofSale.pojo.PojoHistory;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    List<PojoHistory> list;
    public HistoryAdapter(Context context, List<PojoHistory> list){
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
        if(list.get(position).getNewDate()){
            view = LayoutInflater.from(context).inflate(R.layout.dashboard_list_date, viewGroup, false);
            TextView Product = view.findViewById(R.id.product);
            TextView Amount = view.findViewById(R.id.amount);
            TextView Description = view.findViewById(R.id.we);
            TextView Type = view.findViewById(R.id.category);
            TextView NewDate = view.findViewById(R.id.day);

            Product.setText(list.get(position).getProduct());
            Amount.setText(Integer.toString(list.get(position).getAmount()));
            Description.setText(list.get(position).getDescription());
            Type.setText(list.get(position).getType());
            NewDate.setText(convertDate(list.get(position).getDDate()));
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.dashboard_list, viewGroup, false);
            TextView Product = view.findViewById(R.id.product);
            TextView Amount = view.findViewById(R.id.amount);
            TextView Description = view.findViewById(R.id.we);
            TextView Type = view.findViewById(R.id.category);

            Product.setText(list.get(position).getProduct());
            Amount.setText(Integer.toString(list.get(position).getAmount()));
            Description.setText(list.get(position).getDescription());
            Type.setText(list.get(position).getType());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewProduct.class);
                intent.putExtra("ID", list.get(position).getId());
                context.startActivity(intent);
            }
        });

        return view;
    }
    @NonNull
    private String convertDate(String date){
        String dt = date;
        if(checkToday(date)){
            dt = "Today";
        }else{
            try {
                @SuppressLint("SimpleDateFormat") Date day=new SimpleDateFormat("yyyy-MM-dd").parse(date);
                @SuppressLint("SimpleDateFormat") Format f  = new SimpleDateFormat("EEEE dd MMMM yyyy");
                dt = f.format(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dt;
    }
    private boolean checkToday(@NonNull String date){
        return date.equals(getDate());
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
}
