package com.michtech.pointofSale.tcp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.michtech.pointofSale.R;
import com.michtech.pointofSale.Ui.more.store.AddProducts;
import com.michtech.pointofSale.activityPortrait.CaptureActivityPortrait;
import com.michtech.pointofSale.database.DatabaseManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SendReceive extends AppCompatActivity {
    int progress = 0;
    Handler handler = new Handler();
    ImageView Send, Receive, IpImage;
    TextView Status;
    ProgressBar progressBar;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_receive);

        dialog = new Dialog(SendReceive.this);
        dialog.setContentView(R.layout.ip_dialog);
        //dialog.getWindow().setLayout(200, 200);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        Send = findViewById(R.id.send);
        Receive = findViewById(R.id.receive);
        Status = findViewById(R.id.status);
        IpImage = dialog.findViewById(R.id.ip);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        setProgressValue();

        Send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wifi_24, 0, 0, 0);
                showDialog();
                StartServer();
                Receive.setVisibility(View.INVISIBLE);
            }
        });
        Receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wifi_tethering_24, 0, 0, 0);
                scanIp();
                Send.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void showDialog(){
        IpImage.setImageBitmap(createBitmap(getIP()));
        dialog.show();
    }
    private String getIP(){
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ip;
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
        int pixels[] = new int[width * height];
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
    private void scanIp(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(SendReceive.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setPrompt("Scan Qr Code");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                String code = result.getContents();
                clientConnect(code);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setProgressValue(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress<100){
                    progress+=1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void StartServer(){
        Server server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        server.start();
        try {
            server.bind(54555, 54777);
            server.addListener(new Listener(){
                @SuppressLint("UseCompatLoadingForDrawables")
                public void received(Connection connection, Object object){
                    if(object instanceof SomeRequest){
                        dialog.dismiss();
                        SomeRequest request = (SomeRequest) object;
                        Status.setText(request.text);

                        SomeResponse response = new SomeResponse();
                        response.text = "send";
                        System.out.print(getSize()+" size");
                        connection.sendTCP(response);

                        server.close();

                        //receiveData();
                    }
                }
            });
        } catch (IOException e) {
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
    private void startClient(String ip){
        Client client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        new Thread(client).start();
        try {
            client.connect(5000, ip, 54555, 54777);
            SomeRequest request = new SomeRequest();
            request.text = "Connected";
            client.sendTCP(request);

            client.addListener(new Listener(){
                public void received (Connection connection, Object object) {
                    if (object instanceof SomeResponse) {
                        SomeResponse response = (SomeResponse)object;
                        Status.setText(response.text);

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //sendData(ip);
                    }
                }
            });
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendData(String ip){
        String path = "";
        FileInputStream fileInputStream = null;
        int size=0;
        try {
            Socket socket = new Socket(ip, 54555);
            fileInputStream = new FileInputStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bout = new BufferedOutputStream(socket.getOutputStream());
            byte[] bb = new byte[1024 * 8];
            int p=0;
            while((size = bufferedInputStream.read(bb))!=-1){
                bout.write(bb, 0, size);
                bout.flush();
                p++;
                System.out.println(p);
                //setProgress
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void receiveData(){
        try {
            Socket serverSocket = new ServerSocket(54555).accept();
            //ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
            BufferedInputStream bis = new BufferedInputStream(serverSocket.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(SendReceive.this.getFilesDir()+"/sqlite.db"));
            byte[] b = new byte[1024 * 8];
            int len;
            int p = 0;
            while((len = bis.read(b))!=-1){
                bos.write(b, 0, len);
                //jProgressBar1.setValue(p);
                p++;
            }
            bos.close();
            bis.close();
            //dis.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getSize(){
        int i=0;
        DatabaseManager db = new DatabaseManager(SendReceive.this);
        String path = db.getDatabaseLocation();
        System.out.println(path);
        FileInputStream fileInputStream = null;
        int size=0;
        try {
            fileInputStream = new FileInputStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bb = new byte[1024 * 8];
            while((size = bufferedInputStream.read(bb))!=-1){
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //CreateFile();
        return i;
    }
    private void CreateFile(){

        FileOutputStream fos;
        try {
            File myFile = new File(SendReceive.this.getFilesDir()+"/PointOfSale/test.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("text");
            myOutWriter.close();
            fOut.close();
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }

    private void findServer(){
        Client client = new Client();
        InetAddress address = client.discoverHost(54777, 5000);
        Status.setText(address.toString());
    }


    public static class SomeRequest {
        public String text;
    }
    public static class SomeResponse {
        public String text;
    }
}
