package com.michtech.pointofSale.p2p;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adroitandroid.near.discovery.NearDiscovery;
import com.adroitandroid.near.model.Host;

import org.jetbrains.annotations.Contract;

import java.util.Set;

public class StartServer extends Service {
    public int onStartCommand(Intent intent, int flags, int startId) {
        startP2p();
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void startP2p(){
        new NearDiscovery.Builder()
                .setContext(getApplicationContext())
                .setDiscoveryTimeoutMillis(5000)
                .setDiscoverablePingIntervalMillis(5000)
                .setDiscoveryListener(getNearDiscoveryListener(), Looper.getMainLooper())
                .setPort(8089)
                .build();
    }
    @NonNull
    @Contract(value = " -> new", pure = true)
    private NearDiscovery.Listener getNearDiscoveryListener(){
        return new NearDiscovery.Listener() {
            @Override
            public void onPeersUpdate(@NonNull Set<? extends Host> set) {

            }

            @Override
            public void onDiscoveryTimeout() {

            }

            @Override
            public void onDiscoveryFailure(@NonNull Throwable throwable) {

            }

            @Override
            public void onDiscoverableTimeout() {

            }
        };
    }
}
