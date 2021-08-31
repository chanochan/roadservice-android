package com.example.roadservice.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RSAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
}
