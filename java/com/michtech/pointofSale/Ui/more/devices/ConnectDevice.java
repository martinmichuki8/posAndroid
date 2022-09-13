package com.michtech.pointofSale.Ui.more.devices;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.esotericsoftware.kryonet.Client;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.connectDeviceFragment.ConnectDashboard;
import com.michtech.pointofSale.Ui.more.devices.connectDeviceFragment.Connecting;


import java.net.InetAddress;
import java.util.List;

public class ConnectDevice extends AppCompatActivity {
    TextView DeviceUser;
    Button Cancel;
    Client client;
    boolean started = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_device);

        DeviceUser = findViewById(R.id.device);
        Cancel = findViewById(R.id.cancel);

        Bundle bundle = getIntent().getExtras();
        assert bundle !=null;
        String deviceUser = bundle.getString("USER");
        int id = bundle.getInt("ID");

        DeviceUser.setText(deviceUser);

        setFragment(new Connecting());

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(started){
                    client.stop();
                }
                //finish();
                setFragment(new ConnectDashboard());
            }
        });
    }
    public void onResume(){
        super.onResume();
        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                //
            }

            @Override
            public void onFinish() {
                discoverDevice();
            }
        }.start();
    }
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
    private void discoverDevice(){
        client = new Client();
        started  = true;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            getAddressList();
        }
    }
    private void getAddress(){
        InetAddress inetAddress = client.discoverHost(54777, 5000);
        System.out.print(inetAddress);
    }
    private void getAddressList(){
        List<InetAddress> inetAddressList = client.discoverHosts(54777, 5000);
        for (InetAddress inetAddress: inetAddressList){
            System.out.print(inetAddress);
        }
    }
}
