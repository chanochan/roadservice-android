package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.specialist.IssuesListResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class IssuesListThread extends BaseBackendThread {
    public IssuesListThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected IssuesListResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
