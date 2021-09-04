package com.example.roadservice.ui.issues.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Mission;
import com.example.roadservice.ui.issues.citizen.CurrentIssueFragment;
import com.google.android.material.tabs.TabLayout;

public class TeamMissionFragment extends Fragment {
    private Issue issue;
    private Mission mission;

    private EndMissionFragment endMissionFragment;

    public TeamMissionFragment() {
        // Required empty public constructor
    }

    public static TeamMissionFragment newInstance(Issue issue, Mission mission) {
        TeamMissionFragment fragment = new TeamMissionFragment();
        fragment.issue = issue;
        fragment.mission = mission;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        endMissionFragment = EndMissionFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_mission, container, false);

        TabLayout tabLayout = view.findViewById(R.id.teamTabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    CurrentIssueFragment currentIssueFragment = CurrentIssueFragment.newInstance(issue);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.teamTabsFragment, currentIssueFragment)
                            .commit();
                } else if (tab.getPosition() == 1) {
                    CurrentMissionFragment currentMissionFragment = CurrentMissionFragment.newInstance(mission);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.teamTabsFragment, currentMissionFragment)
                            .commit();
                } else {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.teamTabsFragment, endMissionFragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        getParentFragmentManager()
//                .beginTransaction()
//                .replace(R.id.teamTabsFragment, currentIssueFragment)
//                .commit();
        tabLayout.selectTab(tabLayout.getTabAt(0));

        return view;
    }

    public void setData(Issue issue, Mission mission) {
        this.issue = issue;
        this.mission = mission;
    }
}