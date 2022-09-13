package com.michtech.pointofSale.Ui.more.devices.connectDeviceFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.michtech.pointofSale.R;

public class Connecting extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view = layoutInflater.inflate(R.layout.connecting, container, false);
        return view;
    }
}
