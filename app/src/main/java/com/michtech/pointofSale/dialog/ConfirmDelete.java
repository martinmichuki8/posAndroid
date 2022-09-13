package com.michtech.pointofSale.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.list.ProductList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ConfirmDelete extends DialogFragment {
    private int id;
    public ConfirmDelete(int id){
        this.id = id;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Message");
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setMessage("Are you sure you want to delete?");
        builder.setCancelable(false);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseManager db = new DatabaseManager(getContext());
                saveRemoved();
                db.deleteProduct(id);
                getActivity().finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }
    private void saveRemoved(){
        DatabaseManager db = new DatabaseManager(getContext());
        for (ProductList productList : db.getSpecificProducts(id)) {
            db.addRemovedProducts(productList.getProductName(), productList.getDescription(), productList.getAmount(), productList.getCategory(), productList.getPurchasePrice(),
                    productList.getSellingPrice(), productList.getCode(), "Deleted", productList.getSellingPrice()*productList.getAmount(), db.getProductImage(id), getDate());
        }
    }
    @NonNull
    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
}
