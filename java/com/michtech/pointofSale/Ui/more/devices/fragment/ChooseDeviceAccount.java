package com.michtech.pointofSale.Ui.more.devices.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.AddDevices;
import com.michtech.pointofSale.database.DatabaseManager;
import com.michtech.pointofSale.fragments.Store;

import java.util.Objects;

public class ChooseDeviceAccount extends Fragment {
    TextView StoreName;
    ConstraintLayout Custom;
    DatabaseManager db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.choose_device_account, container, false);

        db = new DatabaseManager(getContext());

        StoreName = view.findViewById(R.id.storeName);
        Custom = view.findViewById(R.id.custom);

        StoreName.setText(db.getStoreName());

        Custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CreateDeviceAccount("custom")).commit();
            }
        });
        return view;
    }
}
