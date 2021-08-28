package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.SampleData;
import com.example.roadservice.ui.issues.specialist.adapters.PendingIssueAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PendingIssuesActivity extends AppCompatActivity {
    public List<Issue> issues;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_issues);

        issues = new ArrayList<>();
        Collections.addAll(issues, SampleData.ISSUES_LIST);

        recyclerView = findViewById(R.id.pendingIssuesRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PendingIssueAdapter(issues, this));
    }

    public void acceptIssue(int position) {
        Intent intent = new Intent(this, CreateMissionActivity.class);
        startActivity(intent);
    }

    public void rejectIssue(int position) {
        this.issues.remove(position);
        this.recyclerView.getAdapter().notifyItemRemoved(position);
    }

    public void showIssueDetails(int position) {
        Intent intent = new Intent(this, IssueDetailsActivity.class);
        startActivity(intent);
    }
}