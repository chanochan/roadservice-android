package com.example.roadservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.roadservice.ui.ExitActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ExitActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ExitActivity.class);
        i.setFlags(FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }
}