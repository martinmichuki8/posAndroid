package com.michtech.pointofSale.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.functions.DuplicateProducts;

import java.util.ArrayList;
import java.util.List;

public class AdapterDuplicates extends BaseAdapter {

    static List<Integer> Ids = new ArrayList<>();
    Activity activity;
    List<DuplicateProducts> list;

    public interface Listener{
        void onSomeEvent(int a, String action);
    }

    public AdapterDuplicates(Activity activity, List<DuplicateProducts> list){
        this.activity = activity;
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

        view = LayoutInflater.from(activity).inflate(R.layout.duplicate_list, viewGroup, false);

        TextView ProductName = view.findViewById(R.id.product);
        TextView Category = view.findViewById(R.id.category);
        TextView Amount = view.findViewById(R.id.amount);
        ImageView Selected  = view.findViewById(R.id.selected);

        ProductName.setText(list.get(position).getProductName());
        Category.setText(list.get(position).getCategory());
        Amount.setText(Integer.toString(list.get(position).getAmount()));

        if(searchId(list.get(position).getId())) {
            Selected.setVisibility(View.INVISIBLE);
        }else{
            Selected.setVisibility(View.VISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchId(list.get(position).getId())){
                    Ids.add(list.get(position).getId());
                    ((Listener) activity).onSomeEvent(list.get(position).getId(), "Add");
                    Selected.setVisibility(View.VISIBLE);
                }else{
                    Selected.setVisibility(View.INVISIBLE);
                    Ids.remove(Integer.valueOf(list.get(position).getId()));
                    ((Listener) activity).onSomeEvent(list.get(position).getId(), "Remove");
                }
            }
        });

        return view;
    }
    private boolean searchId(int ID){
        int index = Ids.indexOf(ID);
        return index == -1;
    }
}
