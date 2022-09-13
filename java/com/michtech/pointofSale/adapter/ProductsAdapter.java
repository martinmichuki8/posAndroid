package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.ViewProduct2;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoProducts;

import java.util.List;

public class ProductsAdapter extends BaseAdapter {
    Context context;
    List<PojoProducts> list;
    String ListType;
    DatabaseManager db;
    public ProductsAdapter(Context context, List<PojoProducts> list, String ListType){
        this.context = context;
        this.list = list;
        this.ListType = ListType;
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

    @SuppressLint({"ViewHolder", "SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.store_list, viewGroup, false);
        TextView Product = view.findViewById(R.id.product);
        TextView Amount = view.findViewById(R.id.amount);
        TextView DDate = view.findViewById(R.id.we);
        TextView Category = view.findViewById(R.id.category);

        Product.setText(list.get(position).getProduct());
        Amount.setText(Integer.toString(list.get(position).getAmount()));
        DDate.setText(list.get(position).getDDate());
        Category.setText(list.get(position).getCategory());

        if(ListType.equals("Products")){
            if(db.searchExpiryExists(list.get(position).getId())){
                DDate.setText("Exp: "+db.getExpiryDate(list.get(position).getId()));
                DDate.setBackgroundResource(R.color.yellow);
                DDate.setTextColor(Color.WHITE);
                DDate.setPadding(5, 0, 5, 0);
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewProduct2.class);
                intent.putExtra("ID", list.get(position).getId());
                intent.putExtra("LIST_TYPE", ListType);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
