package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;

import com.example.roadservice.backend.io.accounts.LoginResponse;
import com.example.roadservice.backend.io.accounts.LogoutResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class LogoutThread extends BaseBackendThread {
    public LogoutThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected LogoutResponse backendMethod() {
        return null;
    }

    @Override
    protected int getResponseCode() {
        // TODO
        return LoginResponse.CODE;
    }
}
