package com.example.roadservice.ui.issues.citizen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.ui.accounts.ChangePasswordActivity;

public class CitizenDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_dashboard);
    }

    public void gotoAddIssue(View view) {
        Intent intent = new Intent(this, AddIssueActivity.class);
        startActivity(intent);
    }

    public void gotoCurrentIssue(View view) {
        Intent intent = new Intent(this, CurrentIssueActivity.class);
        startActivity(intent);
    }

    public void gotoRateIssue(View view) {
        Intent intent = new Intent(this, RateIssueActivity.class);
        startActivity(intent);
    }

    public void gotoChangePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}