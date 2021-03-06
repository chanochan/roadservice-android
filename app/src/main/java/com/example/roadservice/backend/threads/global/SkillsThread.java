package com.example.roadservice.backend.threads.global;

import android.os.Handler;
import android.util.Log;

import com.example.roadservice.backend.RoadServiceApi;
import com.example.roadservice.backend.io.global.SkillResponse;
import com.example.roadservice.backend.threads.BaseBackendThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillsThread extends BaseBackendThread {
    private static final String TAG = "SkillsThread";

    public SkillsThread(Handler handler, Object request) {
        super(handler, request);
    }

    @Override
    protected Object backendMethod() {
        try {
            List<SkillResponse> resp = new RoadServiceApi().skills();
            if (resp == null) {
                Log.d(TAG, "Empty response in skills thread");
                return null;
            }
            List<Skill> skills = new ArrayList<>();
            for (SkillResponse respItem : resp)
                skills.add(respItem.toSkill());
            Database.setSkills(skills);
            Log.d(TAG, skills.toString());
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getResponseCode() {
        return SkillResponse.CODE;
    }
}
