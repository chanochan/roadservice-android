package com.example.roadservice.ui.issues.specialist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.roadservice.R;
import com.example.roadservice.ui.accounts.ChangePasswordActivity;

public class SpecialistDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_dashboard);
    }

    public void gotoChangePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void gotoIssues(View view) {
        Intent intent = new Intent(this, IssuesListActivity.class);
        startActivity(intent);
    }

    public void gotoPendings(View view) {
        Intent intent = new Intent(this, PendingIssuesActivity.class);
        startActivity(intent);
    }
}