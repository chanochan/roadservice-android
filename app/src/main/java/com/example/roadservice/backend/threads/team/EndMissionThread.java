package com.example.roadservice.backend.threads.team;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.team.EndMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class EndMissionThread extends BaseBackendThread {
    public EndMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected EndMissionResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
