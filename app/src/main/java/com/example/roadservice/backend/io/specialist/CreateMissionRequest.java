package com.example.roadservice.backend.io.specialist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateMissionRequest {
    @SerializedName("issue")
    @Expose
    public int issueId;

    @SerializedName("mission_type")
    @Expose
    public int missionTypeId;

    @SerializedName("speciality_requirements")
    @Expose
    public List<SkillRequirement> skillReqs;

    @SerializedName("machinery_requirements")
    @Expose
    public List<MachineRequirement> machineReqs;

    public static class MachineRequirement {
        @SerializedName("machinery_type")
        @Expose
        int id;

        @SerializedName("amount")
        @Expose
        int amount;

        public MachineRequirement(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    public static class SkillRequirement {
        @SerializedName("speciality")
        @Expose
        int id;

        @SerializedName("amount")
        @Expose
        int amount;

        public SkillRequirement(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }
}
