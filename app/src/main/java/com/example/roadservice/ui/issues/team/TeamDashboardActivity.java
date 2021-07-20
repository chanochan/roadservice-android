package com.example.roadservice.ui.issues.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.ui.accounts.ChangePasswordActivity;

public class TeamDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_dashboard);
    }

    public void gotoMission(View view) {
        Intent intent = new Intent(this, CurrentMissionActivity.class);
        startActivity(intent);
    }

    public void gotoChangePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}