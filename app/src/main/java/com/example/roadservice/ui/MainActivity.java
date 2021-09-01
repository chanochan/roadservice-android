package com.example.roadservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.RegionsResponse;
import com.example.roadservice.backend.io.accounts.ProfileRequest;
import com.example.roadservice.backend.io.accounts.ProfileResponse;
import com.example.roadservice.backend.threads.RegionsThread;
import com.example.roadservice.backend.threads.accounts.ProfileThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Profile;
import com.example.roadservice.ui.accounts.LoginActivity;
import com.example.roadservice.ui.accounts.RegisterActivity;
import com.example.roadservice.ui.issues.citizen.CitizenDashboardActivity;
import com.example.roadservice.ui.issues.specialist.SpecialistDashboardActivity;
import com.example.roadservice.ui.issues.team.TeamDashboardActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ThreadPoolExecutor threadPoolExecutor;
    private MainActivity.MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
//        Intent intent = new Intent(this, SendLocationService.class);
//        startService(intent);

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new MainHandler(Looper.getMainLooper(), this);

        checkLoginStatus();
        fetchData();
    }

    private void fetchData() {
        RegionsThread thread = new RegionsThread(handler, null);
        threadPoolExecutor.execute(thread);
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

    private void checkLoginStatus() {
        ProfileRequest reqData = new ProfileRequest();
        ProfileThread thread = new ProfileThread(handler, reqData);
        threadPoolExecutor.execute(thread);
    }

    private void gotoDashboard(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
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
            if (msg.arg1 == ProfileResponse.CODE) {
                // TODO handle moderators login
                ProfileResponse resp = (ProfileResponse) msg.obj;
                if (resp == null)
                    return;
                Log.d("SHIT", resp.role);
                Database.setProfile(new Profile(resp.firstName + ' ' + resp.lastName,
                        resp.role,
                        resp.phoneNumber
                ));
                switch (resp.role) {
                    case "CZ":
                        target.gotoDashboard(CitizenDashboardActivity.class);
                        break;
                    case "SM":
                        target.gotoDashboard(TeamDashboardActivity.class);
                        break;
                    case "CE":
                        target.gotoDashboard(SpecialistDashboardActivity.class);
                        break;
                }
            } else if (msg.arg1 == RegionsResponse.CODE) {
                RegionsResponse resp = (RegionsResponse) msg.obj;
            }
        }
    }
}