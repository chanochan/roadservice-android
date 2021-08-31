package com.example.roadservice.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    private static ArrayList<Province> provinces = null;
    private static ArrayList<County> counties = null;
    private static List<Machine> machines = null;
    private static List<Skill> skills = null;

    private static List<MissionType> missionTypes = null;
    private static Issue issue;

    public static ArrayList<Province> getProvinces() {
//        return provinces;
        ArrayList<Province> result = new ArrayList<>();
        result.add(new Province(1, "تهران"));
        result.add(new Province(2, "هرمزگان"));
//        for (int i = 0; i < 32; i++)
//            result.add(new Province(i, "چهارمحال و بختیاری"));
        return result;
    }

    public static void setProvinces(ArrayList<Province> provinces) {
        Database.provinces = provinces;
    }

    public static ArrayList<County> getCounties() {
        return counties;
    }

    public static void setCounties(ArrayList<County> counties) {
        Database.counties = counties;
    }

    public static ArrayList<County> getProvinceCounties(int id) {
        ArrayList<County> result = new ArrayList<>();
        if (id == 1) {
            result.add(new County(3, 1, "تهران"));
            result.add(new County(4, 1, "دماوند"));
            return result;
        }
        if (id == 2) {
            result.add(new County(5, 2, "قشم"));
            result.add(new County(6, 2, "بندر عبّاس"));
            return result;
        }
        for (County county : counties)
            if (county.getProvinceId() == id)
                result.add(county);
        return result;
    }

    public static Issue getIssue() {
//        return issue;
        return SampleData.ISSUE;
    }

    public static void setIssue(Issue issue) {
        Database.issue = issue;
    }

    public static List<Machine> getMachines() {
        return Arrays.asList(SampleData.MACHINES);
//        return machines;
    }

    public static void setMachines(List<Machine> machines) {
        Database.machines = machines;
    }

    public static List<Skill> getSkills() {
//        return skills;
        return Arrays.asList(SampleData.SKILLS);
    }

    public static void setSkills(List<Skill> skills) {
        Database.skills = skills;
    }

    public static List<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public static void setMissionTypes(List<MissionType> missionTypes) {
        Database.missionTypes = missionTypes;
    }

    public static MissionType getMissionType(int id) {
        for (MissionType type : getMissionTypes())
            if (type.getId() == id)
                return type;
        return null;
    }

    public static Machine getMachine(int id) {
        for (Machine machine : getMachines())
            if (machine.id == id)
                return machine;
        return null;
    }

    public static Skill getSkill(int id) {
        for (Skill skill : getSkills())
            if (skill.id == id)
                return skill;
        return null;
    }
}
