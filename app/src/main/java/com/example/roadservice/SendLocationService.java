package com.example.roadservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.UpdateLocationRequest;
import com.example.roadservice.backend.UpdateLocationResponse;

import java.lang.ref.WeakReference;

public class SendLocationService extends Service {
    private static final String TAG = "SendLocationService";
    private Thread senderThread;
    private boolean isRunning;
    private Handler handler;
    private RoadServiceApi api;

    @Override
    public void onCreate() {
        handler = new ShowToastHandler(getMainLooper(), this);
        isRunning = false;
        api = new RoadServiceApi();
        senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                while (true) {
                    Log.i(TAG, "Running sender thread :D");
                    try {
                        UpdateLocationResponse resp = api.updateLocation(new UpdateLocationRequest(10, 20));
                        Message msg = new Message();
                        msg.obj = String.format("Response: %s", resp.getStatus());
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        Message msg = new Message();
                        msg.obj = "Failed to update location";
                        handler.sendMessage(msg);
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning)
            return START_STICKY;
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        senderThread.start();
        isRunning = true;
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying...");
    }

    private static class ShowToastHandler extends Handler {
        private final WeakReference<SendLocationService> target;

        ShowToastHandler(Looper looper, SendLocationService target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            SendLocationService target = this.target.get();
            String text = (String) msg.obj;
            Toast.makeText(target, text, Toast.LENGTH_SHORT).show();
        }
    }
}