package com.michtech.pointofSale.Ui.more.devices;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.fragment.ChooseDeviceAccount;
import com.michtech.pointofSale.Ui.more.devices.fragment.CreateDeviceAccount;

public class AddDevices extends AppCompatActivity {
    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_devices);

        frame = findViewById(R.id.frame);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ChooseDeviceAccount()).commit();

    }
}
