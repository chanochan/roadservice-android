package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

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
    private boolean hasPendingRequest = false;

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
        if (hasPendingRequest) return;
        hasPendingRequest = true;
        RejectIssueThread thread = new RejectIssueThread(handler, new RejectIssueRequest(issue.getId()));
        threadPoolExecutor.execute(thread);
    }

    private void acceptIssue() {
        if (hasPendingRequest) return;
        Intent intent = new Intent(this, CreateMissionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        openDashboard();
    }

    private void onDone() {
        onBackPressed();
    }

    private void onFailure() {
        Toast.makeText(
                this,
                getString(R.string.submission_failure),
                Toast.LENGTH_SHORT
        ).show();
        hasPendingRequest = false;
    }

    private static class IssueDetailsHandler extends Handler {
        private static final String TAG = "IssueDetailsHandler";
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
                if (resp != null && resp.status)
                    target.onDone();
                else
                    target.onFailure();
            }
        }
    }
}