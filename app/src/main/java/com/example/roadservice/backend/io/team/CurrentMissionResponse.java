package com.example.roadservice.backend.io.team;

import com.example.roadservice.backend.io.basics.IssueSerializer;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Mission;
import com.example.roadservice.models.MissionType;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentMissionResponse {
    public static final int CODE = 400;

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("issue")
    @Expose
    public IssueSerializer issue;

    @SerializedName("type")
    @Expose
    public int missionTypeId;

    @SerializedName("machinery_requirements")
    @Expose
    public List<CurrentMissionMachine> machines;

    @SerializedName("service_teams")
    @Expose
    public List<CurrentMissionTeam> teams;

    public Issue getIssue() {
        return issue.toIssue();
    }

    public Mission getMission() {
        ArrayList<ItemCounter> modelMachines = new ArrayList<>();
        for (CurrentMissionMachine machine : machines)
            modelMachines.add(new ItemCounter(
                    Database.getMachine(machine.id),
                    machine.count
            ));

        HashMap<Integer, Integer> skillsCount = new HashMap<>();
        for (CurrentMissionTeam team : teams) {
            Integer currentCount = skillsCount.get(team.skillId);
            if (currentCount == null)
                currentCount = 0;
            skillsCount.put(team.skillId, currentCount + 1);
        }
        ArrayList<ItemCounter> modelSkills = new ArrayList<>();
        for (Map.Entry<Integer, Integer> skill : skillsCount.entrySet()) {
            modelSkills.add(new ItemCounter(
                    Database.getSkill(skill.getKey()),
                    skill.getValue()
            ));
        }

        MissionType missionType = Database.getMissionType(missionTypeId);
        return new Mission(
                modelMachines,
                modelSkills,
                missionType,
                this.issue.id
        );
    }

    public static class CurrentMissionMachine {
        @SerializedName("machinery_type")
        @Expose
        public int id;

        @SerializedName("amount")
        @Expose
        public int count;
    }

    public static class CurrentMissionTeam {
        @SerializedName("speciality")
        @Expose
        public int skillId;
    }
}
