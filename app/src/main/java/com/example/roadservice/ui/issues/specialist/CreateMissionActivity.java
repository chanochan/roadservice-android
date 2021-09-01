package com.example.roadservice.ui.issues.specialist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.specialist.CreateMissionRequest;
import com.example.roadservice.backend.io.specialist.CreateMissionResponse;
import com.example.roadservice.backend.threads.specialist.CreateMissionThread;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Machine;
import com.example.roadservice.models.Skill;
import com.example.roadservice.ui.RSAppCompatActivity;
import com.example.roadservice.ui.issues.specialist.adapters.ItemsCounterAdapter;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CreateMissionActivity extends RSAppCompatActivity {
    // TODO select mission type
    private List<ItemCounter> machinesData;
    private List<ItemCounter> skillsData;
    private RecyclerView machinesRecycler;
    private RecyclerView skillsRecycler;
    private ThreadPoolExecutor threadPoolExecutor;
    private CreateMissionHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mission);
        setupNavigationDrawer();
        setTitle("ایجاد ماموریت");

        machinesData = new ArrayList<>();
        for (Machine machine : Database.getMachines())
            machinesData.add(new ItemCounter(machine));

        skillsData = new ArrayList<>();
        for (Skill skill : Database.getSkills())
            skillsData.add(new ItemCounter(skill));

        Button saveBtn = findViewById(R.id.addMissionBtn);
        saveBtn.setOnClickListener(v -> saveMission());

        RecyclerView.LayoutManager tempLayoutManager;

        machinesRecycler = findViewById(R.id.machinesRecycler);
        tempLayoutManager = new LinearLayoutManager(this);
        machinesRecycler.setLayoutManager(tempLayoutManager);
        machinesRecycler.setAdapter(new ItemsCounterAdapter(machinesData));

        skillsRecycler = findViewById(R.id.teamsRecycler);
        tempLayoutManager = new LinearLayoutManager(this);
        skillsRecycler.setLayoutManager(tempLayoutManager);
        skillsRecycler.setAdapter(new ItemsCounterAdapter(skillsData));

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new CreateMissionHandler(Looper.getMainLooper(), this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, IssueDetailsActivity.class);
        startActivity(intent);
    }

    private void saveMission() {
        CreateMissionRequest req = new CreateMissionRequest();
        req.issueId = Database.getIssue().getId();
        req.missionTypeId = 1;
        req.machineReqs = new ArrayList<>();
        req.skillReqs = new ArrayList<>();

        for (ItemCounter counter : machinesData) {
            Machine machine = (Machine) counter.getObj();
            req.machineReqs.add(new CreateMissionRequest.MachineRequirement(
                    machine.id,
                    counter.getCount()
            ));
        }
        for (ItemCounter counter : skillsData) {
            Skill skill = (Skill) counter.getObj();
            req.skillReqs.add(new CreateMissionRequest.SkillRequirement(
                    skill.id,
                    counter.getCount()
            ));
        }

        CreateMissionThread thread = new CreateMissionThread(handler, req);
        threadPoolExecutor.execute(thread);
    }

    private void onDone() {
        finish();
    }

    private static class CreateMissionHandler extends Handler {
        private final WeakReference<CreateMissionActivity> target;

        CreateMissionHandler(Looper looper, CreateMissionActivity target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            CreateMissionActivity target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == CreateMissionResponse.CODE) {
                // TODO handle errors
                CreateMissionResponse resp = (CreateMissionResponse) msg.obj;
                if (resp == null) {
                    Log.d("SHIT", "Empty response");
                    return;
                }
                if (resp.status)
                    target.onDone();
            }
        }
    }
}