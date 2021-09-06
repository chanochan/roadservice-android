package com.example.roadservice.backend.threads.team;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.team.EndMissionRequest;
import com.example.roadservice.backend.io.team.EndMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class EndMissionThread extends BaseBackendThread {
    private static final String TAG = "EndMissionThread";

    public EndMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected EndMissionResponse backendMethod() {
        try {
            EndMissionResponse resp = new RoadServiceApi().endMission((EndMissionRequest) request);
            if (resp == null)
                Log.d(TAG, "In ja nulle tu end mission!!");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return EndMissionResponse.CODE;
    }
}
