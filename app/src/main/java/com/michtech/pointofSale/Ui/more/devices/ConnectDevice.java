package com.michtech.pointofSale.Ui.more.devices;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.connectDeviceFragment.ConnectDashboard;
import com.michtech.pointofSale.Ui.more.devices.connectDeviceFragment.Connecting;
import com.michtech.pointofSale.Ui.setup.EmployeeAccount;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class ConnectDevice extends AppCompatActivity {
    TextView DeviceUser;
    Button Cancel;
    Client client;
    boolean found = false;
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
                getAddressList();
            }
        }.start();
    }
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
    private boolean startClient(String ip){
        boolean found = false;
        try {
            client.connect(5000, ip, 54555, 54777);
            SomeRequest request = new SomeRequest();
            request.text = "User-michez123";
            client.sendTCP(request);

            //Toast.makeText(ConnectDevice.this, "Connected", Toast.LENGTH_LONG).show();

            client.addListener(new Listener(){
                public void received (Connection connection, Object object){
                    if(object instanceof SomeResponse){
                        SomeResponse someResponse = (SomeResponse)object;
                        System.out.println(someResponse.text);
                        if(someResponse.text.equals("Y")){
                            ConnectDevice.this.found = true;
                            client.close();
                            setFragment(new ConnectDashboard());
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //client.close();
        found = this.found;
        return found;
    }
    private void discoverDevice(){
        started  = true;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    private void getAddressList(){
        client = new Client();
        registerClass();
        new Thread(client).start();
        List<InetAddress> addresses = client.discoverHosts(54777, 5000);
        for(InetAddress inetAddress: addresses){
            if(startClient(delFirst(String.valueOf(inetAddress)))){
                //client.stop();
                //setFragment(new ConnectDashboard());
                break;
            }
            found = false;
        }
    }
    @NonNull
    private String delFirst(@NotNull String ip){
        String Ip = ip;
        char[] p = new char[ip.length()-1];
        int c=0;
        for(int i=1; i<ip.length(); i++){
            p[c] = ip.charAt(i);
            c++;
        }
        Ip = String.valueOf(p);
        return Ip;
    }
    private void registerClass(){
        Kryo kryo = client.getKryo();
        kryo.register(SomeResponse.class);
        kryo.register(SomeRequest.class);
    }

    public static class SomeRequest{
        public String text;
    }
    public static class SomeResponse{
        public String text;
    }
}
