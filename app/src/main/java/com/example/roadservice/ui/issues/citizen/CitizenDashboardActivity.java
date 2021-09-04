package com.example.roadservice.ui.issues.citizen;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.citizen.CurrentIssueRequest;
import com.example.roadservice.backend.io.citizen.CurrentIssueResponse;
import com.example.roadservice.backend.threads.citizen.CurrentIssueThread;
import com.example.roadservice.models.Issue;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.uitls.BooleanContainer;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CitizenDashboardActivity extends RSAppCompatActivity {
    private final BooleanContainer runBackgroundThread = new BooleanContainer(true);
    private CitizenDashboardHandler handler;
    private ThreadPoolExecutor threadPoolExecutor;
    private int currentPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_dashboard);
        setupNavigationDrawer();
        setTitle("ثبت مشکل جدید");

        handler = new CitizenDashboardHandler(Looper.getMainLooper(), this);
        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );

        new Thread(() -> {
            while (runBackgroundThread.get()) {
                if (currentPage == 1) {
                    CurrentIssueThread thread = new CurrentIssueThread(
                            handler,
                            new CurrentIssueRequest()
                    );
                    threadPoolExecutor.execute(thread);
                }
                try {
                    Thread.sleep(15 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void gotoAddIssue() {
        if (currentPage == 0) return;
        currentPage = 0;
        setTitle("ثبت مشکل جدید");
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.citizen_fragment, AddIssueFragment.class, null)
                .commit();
    }

    public void gotoCurrentIssue(Issue issue) {
        if (currentPage == 1) return;
        currentPage = 1;
        setTitle("مشکل در دست بررسی");
        Fragment fragment = CurrentIssueFragment.newInstance(issue);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.citizen_fragment, fragment)
                .commit();
    }

    public void gotoRateIssue(Issue issue) {
        if (currentPage == 2) return;
        currentPage = 2;
        setTitle("ارزیابی عملکرد");
        Fragment fragment = RateIssueFragment.newInstance(issue);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.citizen_fragment, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        runBackgroundThread.reset();
        super.onDestroy();
    }

    private static class CitizenDashboardHandler extends Handler {
        private final WeakReference<CitizenDashboardActivity> target;

        CitizenDashboardHandler(Looper looper, CitizenDashboardActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            CitizenDashboardActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == CurrentIssueResponse.CODE) {
                CurrentIssueResponse resp = (CurrentIssueResponse) msg.obj;
//                target.gotoCurrentIssue(Database.getIssue());
                if (resp == null)
                    return;
                Issue issue = resp.toIssue();
                Log.d("SHIT", resp.state);
                if (resp.state.equals("SC") || resp.state.equals("RJ") || resp.state.equals("FL"))
                    target.gotoAddIssue();
                else if (resp.state.equals("DO"))
                    target.gotoRateIssue(issue);
                else
                    target.gotoCurrentIssue(issue);
            }
        }
    }
}