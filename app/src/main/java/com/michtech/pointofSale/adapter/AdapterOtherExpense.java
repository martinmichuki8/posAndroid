package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.pojo.PojoOtherExpense;


import org.w3c.dom.Text;

import java.util.List;

public class AdapterOtherExpense extends BaseAdapter {
    ImageView Image;

    Context context;
    List<PojoOtherExpense> list;
    DatabaseManager db;
    public AdapterOtherExpense(Context context, List<PojoOtherExpense> list){
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
        if(list.get(position).getId()==3 || list.get(position).getId()==6 || list.get(position).getId()==10){
            view = LayoutInflater.from(context).inflate(R.layout.date, viewGroup, false);

            TextView Week = view.findViewById(R.id.week);
            TextView Type = view.findViewById(R.id.type);
            TextView Description = view.findViewById(R.id.we);
            TextView Amount = view.findViewById(R.id.amount);
            Image = view.findViewById(R.id.image);

            Week.setText("July 23-30");
            Type.setText(list.get(position).getType());
            Description.setText(list.get(position).getDDate());
            Amount.setText(Integer.toString(list.get(position).getAmount()));

        }else{
            view = LayoutInflater.from(context).inflate(R.layout.other_expense_list, viewGroup, false);
            TextView Type = view.findViewById(R.id.type);
            TextView Description = view.findViewById(R.id.we);
            TextView Amount = view.findViewById(R.id.amount);
            Image = view.findViewById(R.id.image);

            Type.setText(list.get(position).getType());
            Description.setText(list.get(position).getDDate());
            Amount.setText(Integer.toString(list.get(position).getAmount()));

        }
        setImage(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(view, position);
            }
        });

        return view;
    }
    private void setImage(int position){
        switch(list.get(position).getImageCode()){
            case 1:
                Image.setImageResource(R.drawable.ic_baseline_emoji_transportation_24);
                break;
            case 2:
                Image.setImageResource(R.drawable.ic_baseline_electrical_services_24);
                break;
            case 3:
                Image.setImageResource(R.drawable.ic_baseline_food_bank_24);
                break;
            default:
                Image.setImageResource(R.drawable.sell);
                break;
        }
    }
    @SuppressLint("SetTextI18n")
    private void displayDialog(View view, int position){
        db = new DatabaseManager(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.display_expense, viewGroup, false);

        TextView Type = dialogView.findViewById(R.id.type);
        TextView Description = dialogView.findViewById(R.id.description);
        TextView Amount = dialogView.findViewById(R.id.amount);
        TextView DDate = dialogView.findViewById(R.id.date);
        //set data
        Type.setText(list.get(position).getType());
        Description.setText(db.getOtherExpensesDescription(list.get(position).getId()));
        Amount.setText(Integer.toString(list.get(position).getAmount()));
        DDate.setText(list.get(position).getDDate());

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
