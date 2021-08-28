package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.SampleData;
import com.example.roadservice.ui.issues.specialist.adapters.IssuesListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssuesListActivity extends AppCompatActivity {
    public List<Issue> issues;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_list);

        issues = new ArrayList<>();
        Collections.addAll(issues, SampleData.ISSUES_LIST);

        recyclerView = findViewById(R.id.allIssuesRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new IssuesListAdapter(issues, this));
    }

    public void showIssueDetails(int position) {
        Intent intent = new Intent(this, IssueDetailsActivity.class);
        startActivity(intent);
    }
}