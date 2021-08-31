package com.example.roadservice.models;

import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import java.util.List;

public class Mission {
    private List<ItemCounter> machines, skills;
    private MissionType type;
    private int issueId;

    public Mission(List<ItemCounter> machines, List<ItemCounter> skills, MissionType type, int issueId) {
        this.machines = machines;
        this.skills = skills;
        this.type = type;
        this.issueId = issueId;
    }

    public List<ItemCounter> getMachines() {
        return machines;
    }

    public void setMachines(List<ItemCounter> machines) {
        this.machines = machines;
    }

    public List<ItemCounter> getSkills() {
        return skills;
    }

    public void setSkills(List<ItemCounter> skills) {
        this.skills = skills;
    }

    public MissionType getType() {
        return type;
    }

    public void setType(MissionType type) {
        this.type = type;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }
}
