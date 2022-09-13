package com.michtech.pointofSale.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.shopping.ShoppingData;
import com.michtech.pointofSale.database.DatabaseManager;

public class MessageDialog extends DialogFragment {
    String title;
    String message;
    String toDo;
    String pButton;
    public MessageDialog(String title, String message, String toDo, String pButton){
        this.title = title;
        this.message = message;
        this.toDo = toDo;
        this.pButton = pButton;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(pButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleToDo();
            }
        });
        if(toDo.equals("tempProductTableFound")){
            builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseManager db = new DatabaseManager(getContext());
                    db.dropTableTempProduct();
                    db.createTempTable();
                }
            });
        }else{
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }
    private void handleToDo(){
        switch(toDo){
            case "tempProductTableFound":
                Intent intent = new Intent(getContext(), ShoppingData.class);
                startActivity(intent);
                break;
        }
    }
}
