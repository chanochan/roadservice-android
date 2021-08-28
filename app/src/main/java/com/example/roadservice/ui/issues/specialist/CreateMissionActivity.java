package com.example.roadservice.ui.issues.specialist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.models.SampleData;
import com.example.roadservice.ui.issues.specialist.adapters.ItemsCounterAdapter;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import java.util.ArrayList;
import java.util.List;

public class CreateMissionActivity extends AppCompatActivity {
    private List<ItemCounter> machinesData;
    private List<ItemCounter> teamsData;
    private RecyclerView machinesRecycler;
    private RecyclerView teamsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mission);

        machinesData = new ArrayList<>();
        for (String machine : SampleData.MACHINES)
            machinesData.add(new ItemCounter(machine));

        teamsData = new ArrayList<>();
        for (String skill : SampleData.SKILLS)
            teamsData.add(new ItemCounter(skill));

        Button saveBtn = findViewById(R.id.addMissionBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMission();
            }
        });

        RecyclerView.LayoutManager tempLayoutManager;

        machinesRecycler = findViewById(R.id.machinesRecycler);
        tempLayoutManager = new LinearLayoutManager(this);
        machinesRecycler.setLayoutManager(tempLayoutManager);
        machinesRecycler.setAdapter(new ItemsCounterAdapter(machinesData, this));

        teamsRecycler = findViewById(R.id.teamsRecycler);
        tempLayoutManager = new LinearLayoutManager(this);
        teamsRecycler.setLayoutManager(tempLayoutManager);
        teamsRecycler.setAdapter(new ItemsCounterAdapter(teamsData, this));
    }

    private void saveMission() {
        finish();
    }
}