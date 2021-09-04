package com.example.roadservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.roadservice.R;
import com.example.roadservice.RSDaemonService;
import com.example.roadservice.backend.io.accounts.ProfileRequest;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.threads.accounts.ProfileThread;
import com.example.roadservice.backend.threads.global.MachinesThread;
import com.example.roadservice.backend.threads.global.MissionTypesThread;
import com.example.roadservice.backend.threads.global.RegionsThread;
import com.example.roadservice.backend.threads.global.SkillsThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Profile;
import com.example.roadservice.ui.accounts.LoginActivity;
import com.example.roadservice.ui.accounts.RegisterActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends RSAppCompatActivity {
    ThreadPoolExecutor threadPoolExecutor;
    private MainHandler handler;
    private int pendingRequests;
    private LinearProgressIndicator progressIndicator;
    private ConstraintLayout buttonsLayout;
    private boolean badNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.loginBtn).setOnClickListener(v -> startLogin());
        findViewById(R.id.registerBtn).setOnClickListener(v -> startRegister());
        findViewById(R.id.mainRefreshLayout).setOnClickListener(v -> fetchData());

        progressIndicator = findViewById(R.id.mainProgressIndicator);
        buttonsLayout = findViewById(R.id.mainButtonsLayout);
        Intent intent = new Intent(this, RSDaemonService.class);
        startService(intent);

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 4, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new MainHandler(Looper.getMainLooper(), this);

        fetchData();
    }

    private void fetchData() {
        badNetwork = false;
        findViewById(R.id.mainRefreshLayout).setVisibility(View.GONE);
        progressIndicator.setVisibility(View.VISIBLE);
        if (fetchBasicData())
            fetchProfile();
    }

    private boolean fetchBasicData() {
        int newThreadsCount = 0;
        if (Database.getCounties() == null) {
            addPendingRequests(+1);
            threadPoolExecutor.execute(new RegionsThread(handler, null));
            newThreadsCount++;
        }
        if (Database.getMachines() == null) {
            addPendingRequests(+1);
            threadPoolExecutor.execute(new MachinesThread(handler, null));
            newThreadsCount++;
        }
        if (Database.getSkills() == null) {
            addPendingRequests(+1);
            threadPoolExecutor.execute(new SkillsThread(handler, null));
            newThreadsCount++;
        }
        if (Database.getMissionTypes() == null) {
            addPendingRequests(+1);
            threadPoolExecutor.execute(new MissionTypesThread(handler, null));
            newThreadsCount++;
        }
        return newThreadsCount == 0;
    }

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchProfile() {
        addPendingRequests(+1);
        ProfileRequest reqData = new ProfileRequest();
        ProfileThread thread = new ProfileThread(handler, reqData);
        threadPoolExecutor.execute(thread);
    }

    private void addPendingRequests(int amount) {
        boolean wasZero = pendingRequests == 0;
        pendingRequests += amount;
        if (!wasZero && pendingRequests == 0)
            progressIndicator.setVisibility(View.GONE);
        else if (wasZero && pendingRequests > 0)
            progressIndicator.setVisibility(View.VISIBLE);
    }

    private void showButtons() {
        buttonsLayout.setVisibility(View.VISIBLE);
    }

    private void networkError() {
        this.badNetwork = true;
        Toast toast = Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT);
        toast.show();
        if (pendingRequests == 0)
            findViewById(R.id.mainRefreshLayout).setVisibility(View.VISIBLE);
    }

    private static class MainHandler extends Handler {
        private final WeakReference<MainActivity> target;

        MainHandler(Looper looper, MainActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity target = this.target.get();
            if (target == null)
                return;
            target.addPendingRequests(-1);
            if (msg.arg1 == ProfileResponse.CODE) {
                // TODO handle moderators login
                ProfileResponse resp = (ProfileResponse) msg.obj;
                if (resp == null) {
                    target.showButtons();
                    return;
                }
                Database.setProfile(new Profile(
                        resp.firstName + ' ' + resp.lastName,
                        resp.role,
                        resp.phoneNumber
                ));
                target.openDashboard();
            } else if (msg.obj == null)
                target.networkError();
            else if (target.pendingRequests == 0 && !target.badNetwork)
                target.fetchProfile();
        }
    }
}