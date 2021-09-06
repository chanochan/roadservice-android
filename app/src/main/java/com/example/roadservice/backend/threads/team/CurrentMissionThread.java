package com.example.roadservice.backend.threads.team;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;

public class CurrentMissionThread extends BaseBackendThread {
    private static final String TAG = "CurrentMissionThread";

    public CurrentMissionThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected CurrentMissionResponse backendMethod() {
        try {
            CurrentMissionResponse resp = new RoadServiceApi().currentMission();
            if (resp == null)
                Log.d(TAG, "In ja nulle tu current mission!!");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return CurrentMissionResponse.CODE;
    }
}
