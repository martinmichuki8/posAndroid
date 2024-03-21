package com.michtech.pointofSale.Ui.setup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.jsonbeans.JsonValue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.devices.fragment.CreateDeviceAccount;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.activityPortrait.CaptureActivityPortrait;
import com.michtech.pointofSale.database.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EmployeeAccount extends AppCompatActivity {
    Client client;
    FloatingActionButton SCanQrCode;
    DatabaseManager db;

    private String storeName;
    private String accountType;
    private String userName;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_create_account);

        db = new DatabaseManager(EmployeeAccount.this);

        SCanQrCode = findViewById(R.id.Scan);

        SCanQrCode.setOnClickListener(view -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(EmployeeAccount.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setPrompt("Scan barcode");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
            intentIntegrator.initiateScan();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                decodeQrCode(code);
                //Toast.makeText(EmployeeAccount.this, code, Toast.LENGTH_SHORT).show();
                //save accout
                //        save store
                db.AddStore(storeName, accountType, convertImage(BitmapFactory.decodeResource(getResources(), R.drawable.product)));
                db.AddUser(userName, password);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void startClient(String ip){
        client = new Client();
        registerClass();
        new Thread(client).start();
        try {
            client.connect(5000, ip, 54555, 54777);
            SomeRequest request = new SomeRequest();
            request.text = "created account";
            client.sendTCP(request);
            client.close();
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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



    private void decodeQrCode(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String ip = (String) jsonObject.get("Ip");
            storeName = (String) jsonObject.get("StoreName");
            accountType = (String) jsonObject.get("AccountType");
            userName = (String) jsonObject.get("UserName");
            password = (String) jsonObject.get("Password");
            clientConnect(ip);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void clientConnect(String ip){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            startClient(ip);

        }
    }
    @NonNull
    private byte[] convertImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
