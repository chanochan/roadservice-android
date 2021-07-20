package com.example.roadservice.ui.issues.citizen;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;

public class RateIssueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_issue);
    }

    public void finish(View view) {
        finish();
    }
}