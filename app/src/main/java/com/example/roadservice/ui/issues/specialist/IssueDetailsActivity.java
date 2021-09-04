package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.specialist.RejectIssueRequest;
import com.example.roadservice.backend.io.specialist.RejectIssueResponse;
import com.example.roadservice.backend.threads.specialist.RejectIssueThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Issue;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.issues.citizen.CurrentIssueFragment;
import com.google.android.material.appbar.MaterialToolbar;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IssueDetailsActivity extends RSAppCompatActivity {
    private Issue issue;
    private IssueDetailsHandler handler;
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);
        setupNavigationDrawer();
        setTitle("جزییات مشکل");

        setupActionBar();

        issue = Database.getIssue();
        Fragment fragment = CurrentIssueFragment.newInstance(issue);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.issueDetailsFragment, fragment)
                .commit();

//        Button rejectButton = findViewById(R.id.rejectIssueBtn);
//        rejectButton.setOnClickListener(v -> rejectIssue());
//
//        Button acceptButton = findViewById(R.id.acceptIssueBtn);
//        acceptButton.setOnClickListener(v -> acceptIssue());

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new IssueDetailsHandler(Looper.getMainLooper(), this);
    }

    private void setupActionBar() {
        MaterialToolbar toolBar = findViewById(R.id.topAppBar);
        toolBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.acceptIssueHeader)
                acceptIssue();
            else if (item.getItemId() == R.id.rejectIssueHeader)
                rejectIssue();
            else
                return false;
            return true;
        });
    }

    private void rejectIssue() {
        RejectIssueThread thread = new RejectIssueThread(handler, new RejectIssueRequest(issue.getId()));
        threadPoolExecutor.execute(thread);
    }

    private void acceptIssue() {
        Intent intent = new Intent(this, CreateMissionActivity.class);
        startActivity(intent);
        finish();
    }

    private void onDone() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        openDashboard();
    }

    private static class IssueDetailsHandler extends Handler {
        private final WeakReference<IssueDetailsActivity> target;

        IssueDetailsHandler(Looper looper, IssueDetailsActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            IssueDetailsActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == RejectIssueResponse.CODE) {
                RejectIssueResponse resp = (RejectIssueResponse) msg.obj;
                if (resp == null) {
                    Log.d("SHIT", "Empty response");
                    return;
                }
                target.onDone();
            }
        }
    }
}