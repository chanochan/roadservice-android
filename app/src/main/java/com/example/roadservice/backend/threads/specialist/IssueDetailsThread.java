package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.specialist.IssueDetailsResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class IssueDetailsThread extends BaseBackendThread {
    public IssueDetailsThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected IssueDetailsResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
