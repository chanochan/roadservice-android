package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.specialist.PendingIssuesListResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class PendingIssuesListThread extends BaseBackendThread {
    public PendingIssuesListThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected PendingIssuesListResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
