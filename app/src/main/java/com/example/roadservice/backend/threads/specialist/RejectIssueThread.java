package com.example.roadservice.backend.threads.specialist;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.specialist.RejectIssueRequest;
import com.example.roadservice.backend.io.specialist.RejectIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class RejectIssueThread extends BaseBackendThread {
    private static final String TAG = "RejectIssueThread";

    public RejectIssueThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected RejectIssueResponse backendMethod() {
        try {
            RejectIssueResponse resp = new RoadServiceApi().rejectIssue((RejectIssueRequest) request);
            if (resp == null) {
                Log.d(TAG, "Empty response in reject issue thread!!");
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return RejectIssueResponse.CODE;
    }
}
