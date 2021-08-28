package com.example.roadservice.backend.threads.team;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class CurrentMissionThread extends BaseBackendThread {
    public CurrentMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected CurrentMissionResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
