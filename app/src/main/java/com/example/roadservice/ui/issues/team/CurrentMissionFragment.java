package com.example.roadservice.ui.issues.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.models.Mission;
import com.example.roadservice.ui.issues.team.adapters.ConstItemsCounterAdapter;

import java.util.ArrayList;

public class CurrentMissionFragment extends Fragment {
    private Mission mission;
    private TextView missionTypeText;
    private RecyclerView machinesRecycler, skillsRecycler;

    public CurrentMissionFragment() {
        // Required empty public constructor
    }

    public static CurrentMissionFragment newInstance(Mission mission) {
        CurrentMissionFragment fragment = new CurrentMissionFragment();
        fragment.mission = mission;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_mission, container, false);

        missionTypeText = view.findViewById(R.id.missionTypeText);

        RecyclerView.LayoutManager tempLayoutManager;

        machinesRecycler = view.findViewById(R.id.missionMachinesRecycler);
        tempLayoutManager = new LinearLayoutManager(getActivity());
        machinesRecycler.setLayoutManager(tempLayoutManager);
        machinesRecycler.setAdapter(new ConstItemsCounterAdapter(new ArrayList<>(), "دستگاه"));

        skillsRecycler = view.findViewById(R.id.missionTeamsRecycler);
        tempLayoutManager = new LinearLayoutManager(getActivity());
        skillsRecycler.setLayoutManager(tempLayoutManager);
        skillsRecycler.setAdapter(new ConstItemsCounterAdapter(new ArrayList<>(), "تیم"));

        showData();

        return view;
    }

    public void setData(Mission mission) {
        if (mission == this.mission) return;
        this.mission = mission;
        showData();
    }

    private void showData() {
        if (mission == null) return;

        if (missionTypeText != null)
            missionTypeText.setText(mission.getType().getName());

        if (machinesRecycler != null) {
            ConstItemsCounterAdapter machinesAdapter = (ConstItemsCounterAdapter) machinesRecycler.getAdapter();
            machinesAdapter.setLocalDataSet(mission.getMachines());
            machinesAdapter.notifyDataSetChanged();
        }

        if (skillsRecycler != null) {
            ConstItemsCounterAdapter skillsAdapter = (ConstItemsCounterAdapter) skillsRecycler.getAdapter();
            skillsAdapter.setLocalDataSet(mission.getSkills());
            skillsAdapter.notifyDataSetChanged();
        }
    }
}