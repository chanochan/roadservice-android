package com.example.roadservice.ui.accounts;

import android.os.Bundle;

import com.example.roadservice.R;
import com.example.roadservice.ui.RSAppCompatActivity;

public class ChangePasswordActivity extends RSAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setupNavigationDrawer();
        setTitle("تغییر گذرواژه");

        findViewById(R.id.changePasswordButton).setOnClickListener(v -> submit());
    }

    private void submit() {
    }
}