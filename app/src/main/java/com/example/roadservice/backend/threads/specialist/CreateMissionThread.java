package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.specialist.CreateMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class CreateMissionThread extends BaseBackendThread {
    public CreateMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected CreateMissionResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
