package com.example.roadservice.backend.threads.citizen;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.citizen.RateIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class RateIssueThread extends BaseBackendThread {
    public RateIssueThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected RateIssueResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
