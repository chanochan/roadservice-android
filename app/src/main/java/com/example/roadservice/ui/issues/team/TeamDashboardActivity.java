package com.example.roadservice.ui.issues.team;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.team.CurrentMissionResponse;
import com.example.roadservice.backend.threads.team.CurrentMissionThread;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Mission;
import com.example.roadservice.models.SampleData;
import com.example.roadservice.ui.RSAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TeamDashboardActivity extends RSAppCompatActivity {
    private TeamDashboardHandler handler;
    private ThreadPoolExecutor executor;
    private TeamMissionFragment missionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_dashboard);
        setTitle("مشاهده‌ی ماموریت");
        setupNavigationDrawer();
        findViewById(R.id.missionDoneLayout).setVisibility(View.GONE);
        findViewById(R.id.teamDashboardFragment).setVisibility(View.GONE);

        missionFragment = TeamMissionFragment.newInstance(SampleData.ISSUE, SampleData.getMission());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.teamDashboardFragment, missionFragment)
                .commit();

        executor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new TeamDashboardHandler(Looper.getMainLooper(), this);

        updateData();
    }

    public void updateData() {
        CurrentMissionThread thread = new CurrentMissionThread(handler, null);
        executor.execute(thread);
    }

    private void setData(Issue issue, Mission mission) {
        if (issue != null) {
            missionFragment.setData(issue, mission);
            findViewById(R.id.teamDashboardFragment).setVisibility(View.VISIBLE);
            findViewById(R.id.missionDoneLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.teamDashboardFragment).setVisibility(View.GONE);
            findViewById(R.id.missionDoneLayout).setVisibility(View.VISIBLE);
        }
    }

    private static class TeamDashboardHandler extends Handler {
        private final WeakReference<TeamDashboardActivity> target;

        TeamDashboardHandler(Looper looper, TeamDashboardActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            TeamDashboardActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == CurrentMissionResponse.CODE) {
                // TODO handle errors
                CurrentMissionResponse resp = (CurrentMissionResponse) msg.obj;
                if (resp == null) {
                    Log.d("SHIT", "Empty response");
                    target.setData(null, null);
                    return;
                }
                Log.d("SHIT", "Full response");
                if (resp.status)
                    target.setData(resp.getIssue(), resp.getMission());
                else
                    target.setData(null, null);
            }
        }
    }
}