package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.accounts.RegisterRequest;
import com.example.roadservice.backend.io.accounts.RegisterResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class RegisterThread extends BaseBackendThread {
    public RegisterThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected RegisterResponse backendMethod() {
        try {
            return new RoadServiceApi().register((RegisterRequest) request);
        } catch (Exception e) {
            // TODO handle exception
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return RegisterResponse.CODE;
    }
}
