package com.example.roadservice.backend.threads;

import android.os.Handler;
import android.os.Message;

public abstract class BaseBackendThread implements Runnable {
    protected final Object request;
    private final Handler handler;

    public BaseBackendThread(Handler handler, Object request) {
        this.handler = handler;
        this.request = request;
    }

    protected abstract Object backendMethod();

    protected abstract int getResponseCode();

    @Override
    public void run() {
        Object resp = backendMethod();
        Message msg = new Message();
        msg.obj = resp;
        msg.arg1 = getResponseCode();
        handler.sendMessage(msg);
    }
}
