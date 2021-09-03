package com.example.roadservice.backend.threads.citizen;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.citizen.RateIssueRequest;
import com.example.roadservice.backend.io.citizen.RateIssueResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class RateIssueThread extends BaseBackendThread {
    private static final String TAG = "RateIssueThread";

    public RateIssueThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected RateIssueResponse backendMethod() {
        try {
            Log.d(TAG, "Send request");
            RateIssueResponse resp = new RoadServiceApi().rateIssue((RateIssueRequest) request);
            Log.d(TAG, "Receive response");
            if (resp == null) {
                Log.d(TAG, "Empty response in rate issue thread!!");
            }
            return resp;
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return RateIssueResponse.CODE;
    }
}
