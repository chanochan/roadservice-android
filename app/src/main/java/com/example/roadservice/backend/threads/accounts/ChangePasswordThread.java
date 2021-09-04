package com.example.roadservice.backend.threads.accounts;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.accounts.ChangePasswordRequest;
import com.example.roadservice.backend.io.accounts.ChangePasswordResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class ChangePasswordThread extends BaseBackendThread {
    private static final String TAG = "ChangePasswordThread";

    public ChangePasswordThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected ChangePasswordResponse backendMethod() {
        try {
            ChangePasswordResponse resp = new RoadServiceApi().changePassword((ChangePasswordRequest) request);
            if (resp == null)
                Log.d(TAG, "Empty response in here!!");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return ChangePasswordResponse.CODE;
    }
}
