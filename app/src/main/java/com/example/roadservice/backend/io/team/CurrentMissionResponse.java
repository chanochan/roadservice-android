package com.example.roadservice.backend.io.team;

import com.example.roadservice.models.Database;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Mission;
import com.example.roadservice.models.MissionType;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrentMissionResponse {
    public static final int CODE = 400;

    @SerializedName("status")
    @Expose
    public boolean status;

    @SerializedName("issue")
    @Expose
    public CurrentMissionIssue issue;

    @SerializedName("type")
    @Expose
    public int missionTypeId;

    @SerializedName("machinery_requirements")
    @Expose
    public List<CurrentMissionMachine> machines;

    @SerializedName("service_teams")
    @Expose
    public List<CurrentMissionSkill> skills;

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

        ArrayList<ItemCounter> modelSkills = new ArrayList<>();
        for (CurrentMissionSkill skill : skills)
            modelSkills.add(new ItemCounter(
                    Database.getSkill(skill.id),
                    skill.count
            ));

        MissionType missionType = Database.getMissionType(missionTypeId);
        return new Mission(
                modelMachines,
                modelSkills,
                missionType,
                this.issue.id
        );
    }

    public static class CurrentMissionIssue {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("lat")
        @Expose
        public double latitude;

        @SerializedName("long")
        @Expose
        public double longitude;

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("county")
        @Expose
        public int countyId;

        public Issue toIssue() {
            return new Issue(
                    id,
                    new GeoLocation(latitude, longitude),
                    title,
                    description,
                    null,
                    countyId
            );
        }
    }

    public static class CurrentMissionMachine {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("amount")
        @Expose
        public int count;
    }

    public static class CurrentMissionSkill {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("amount")
        @Expose
        public int count;
    }
}
