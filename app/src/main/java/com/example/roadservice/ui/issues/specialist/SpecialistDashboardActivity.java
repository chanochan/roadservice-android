package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.specialist.PendingIssueResponse;
import com.example.roadservice.backend.threads.specialist.PendingIssuesListThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.SampleData;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.issues.specialist.adapters.PendingIssueAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpecialistDashboardActivity extends RSAppCompatActivity {
    public List<Issue> issues;
    private RecyclerView recyclerView;
    private SpecialistDashboardHandler handler;
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_dashboard);

        issues = new ArrayList<>();

        recyclerView = findViewById(R.id.pendingIssuesRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PendingIssueAdapter(issues, this));

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new SpecialistDashboardHandler(Looper.getMainLooper(), this);

        setTitle("مشکلات جدید");
        updateDataSet();
    }

    private void updateDataSet() {
//        Collections.addAll(this.issues, SampleData.ISSUES_LIST);
//        recyclerView.getAdapter().notifyDataSetChanged();
        PendingIssuesListThread thread = new PendingIssuesListThread(handler, null);
        threadPoolExecutor.execute(thread);
    }

    public void showIssueDetails(int position) {
        Database.setIssue(issues.get(position));
        Intent intent = new Intent(this, IssueDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    private static class SpecialistDashboardHandler extends Handler {
        private final WeakReference<SpecialistDashboardActivity> target;

        SpecialistDashboardHandler(Looper looper, SpecialistDashboardActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            SpecialistDashboardActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == PendingIssueResponse.CODE) {
                List<PendingIssueResponse> resp = (List<PendingIssueResponse>) msg.obj;
                target.issues.clear();
                for (PendingIssueResponse issue : resp)
                    target.issues.add(issue.toIssue());
                target.recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }
}