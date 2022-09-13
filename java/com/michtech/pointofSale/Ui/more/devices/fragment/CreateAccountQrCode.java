package com.michtech.pointofSale.Ui.more.devices.fragment;

import static android.content.Context.WIFI_SERVICE;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.charts.Map;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.background.SomeRequest;
import com.michtech.pointofSale.database.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class CreateAccountQrCode extends Fragment {
    Server server;
    ImageView QrCode;
    DatabaseManager db;

    private String AccountType;
    private String UserName;
    private String Password;
    public CreateAccountQrCode(String AccountType, String UserName, String Password){
        this.AccountType = AccountType;
        this.UserName = UserName;
        this.Password = Password;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_account_qrcode, container, false);

        db = new DatabaseManager(getContext());

        QrCode = view.findViewById(R.id.code);

       CreateQrCode();

        return view;
    }
    @Nullable
    private Bitmap createBitmap(String text){
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 500, 500, null);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        int width, height;
        width = result.getWidth();
        height = result.getHeight();
        int[] pixels = new int[width * height];
        for(int i=0; i<height; i++){
            int offset = i * width;
            for(int k=0; k<width; k++){
                pixels[offset + k] = result.get(k, i) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
    private void CreateQrCode(){
        JSONObject object = new JSONObject();
        try {
            object.put("StoreName", db.getStoreName());
            object.put("AccountType", AccountType);
            object.put("UserName", UserName);
            object.put("Password", Password);
            object.put("Ip", getIP());

            QrCode.setImageBitmap(createBitmap(object.toString()));
            startKServer();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getIP(){
        WifiManager wifiManager = (WifiManager) getContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ip;
    }
    private void startKServer(){//start server
        server = new Server();
        server.start();
        registerClass();
        startServerListener();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startServerListener(){
        server.addListener(new Listener(){
            public void received(Connection connection, Object object){
                SomeRequest request = (SomeRequest) object;
                if(request.text.equals("created account")){
                    db.addDevices(UserName, Password, AccountType);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ChooseDeviceAccount()).commit();
                    //Toast.makeText(getContext(), "Account has been created", Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("Failed");
                }
            }
        });
        server.close();
    }

    private void registerClass(){
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
    }
    public static class SomeResponse{
        public String text;
    }
}
