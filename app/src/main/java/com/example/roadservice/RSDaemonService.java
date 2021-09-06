package com.example.roadservice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.specialist.PendingIssueResponse;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.io.team.UpdateLocationRequest;
import com.example.roadservice.backend.io.team.UpdateLocationResponse;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.ui.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RSDaemonService extends Service {
    public static final String SP_KEY = "RSDaemon";
    private static final String TAG = "RSDaemonService";
    private static final String NOTIFICATION_CHANNEL_ID = "RSDaemon::Notification";
    private static final String SERVICE_CHANNEL_ID = "RSDaemon::Service";
    private static final String ACTION_EXIT = "EXIT";
    private Runnable senderThread;
    private boolean isRunning;
    private Handler handler;
    private RoadServiceApi api;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        handler = new ShowToastHandler(getMainLooper(), this);
        isRunning = false;
        api = new RoadServiceApi();
        NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.APP_NAME),
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        showForegroundNotification(
                "سامانه‌ی امداد جادّه‌ای",
                "ساج با قدرت در حال اجراست."
        );
        senderThread = () -> {
            int x = 0;
            while (true) {
                Log.i(TAG, "Running sender thread");
                switch (Database.getRole("")) {
                    case "CZ":
                        processCitizen(manager);
                        break;
                    case "SM":
                        processTeam(manager);
                        break;
                    case "CE":
                        processSpecialist(manager);
                        break;
                    default:
                        break;
                }

                try {
                    Thread.sleep(getSleepTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private long getSleepTime() {
        return 60 * 1000;
    }

    private void processCitizen(NotificationManager manager) {
        // TODO report whether it is done or not
    }

    private void processTeam(NotificationManager manager) {
        try {
            Log.d(TAG, "Sending location");
            GeoLocation location = getLastLocation();
            if (location != null) {
                UpdateLocationResponse resp = api.updateLocation(
                        new UpdateLocationRequest(
                                location.getLatitude(),
                                location.getLongitude()
                        )
                );
                if (resp != null && resp.status)
                    Log.d(TAG, "Location updated");
            } else
                Log.d(TAG, "Can't access to location");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "Getting mission");
            CurrentMissionResponse mission = api.currentMission();
            if (mission != null && mission.status) {
                Log.d(TAG, "Got mission");
                String newValue = Integer.toString(mission.issue.id);
                String oldValue = Database.getCustom(SP_KEY, null);
                if (!newValue.equals(oldValue)) {
                    sendNotification(manager, mission.issue.title, getString(R.string.got_new_mission));
                    Database.setCustom(SP_KEY, newValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSpecialist(NotificationManager manager) {
        try {
            Log.d(TAG, "Getting pending issues");
            List<PendingIssueResponse> issues = api.pendingIssuesList();
            if (issues != null) {
                Log.d(TAG, "Got pending issues");
                PendingIssueResponse newIssue = null;
                for (PendingIssueResponse issue : issues)
                    if (newIssue == null || issue.id > newIssue.id)
                        newIssue = issue;
                if (newIssue != null) {
                    String newValue = Integer.toString(newIssue.id);
                    String oldValue = Database.getCustom(SP_KEY, null);
                    if (!newValue.equals(oldValue)) {
                        sendNotification(manager, newIssue.title, getString(R.string.got_new_issue));
                        Database.setCustom(SP_KEY, newValue);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(NotificationManager manager, String title, String description) {
        Intent nIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(
                this, 0, nIntent, 0
        );
        Notification notification = (new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID))
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        manager.notify(2, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showForegroundNotification(String title, String description) {
        NotificationChannel channel = new NotificationChannel(
                SERVICE_CHANNEL_ID,
                getString(R.string.APP_NAME) + "sss",
                NotificationManager.IMPORTANCE_MIN
        );
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Intent nIntent = new Intent(getApplicationContext(), MainActivity.class);
        nIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(
                this, 0, nIntent, 0
        );

        Intent exitIntent = new Intent(this, ExitActionReceiver.class);
        exitIntent.setAction(ACTION_EXIT);
        PendingIntent pendingExitIntent = PendingIntent.getBroadcast(
                this,
                0,
                exitIntent,
                0
        );

        Notification notification = (new NotificationCompat.Builder(this, SERVICE_CHANNEL_ID))
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(pIntent)
                .addAction(
                        R.drawable.ic_baseline_exit_to_app_24,
                        getString(R.string.logout),
                        pendingExitIntent
                )
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
        manager.notify(1, notification);
        startForeground(1, notification);
    }

    private GeoLocation getLastLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return null;
        Task<Location> task = fusedLocationClient.getLastLocation();
        try {
            Tasks.await(task);
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null)
                    return new GeoLocation(location.getLatitude(), location.getLongitude());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning)
            return START_STICKY;
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        new Thread(senderThread).start();
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
        private final WeakReference<RSDaemonService> target;

        ShowToastHandler(Looper looper, RSDaemonService target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            RSDaemonService target = this.target.get();
            String text = (String) msg.obj;
            Toast.makeText(target, text, Toast.LENGTH_SHORT).show();
        }
    }
}