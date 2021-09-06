package com.example.roadservice.models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.roadservice.R;
import com.example.roadservice.RoadServiceApplication;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static ArrayList<Province> provinces = null;
    private static ArrayList<County> counties = null;
    private static List<Machine> machines = null;
    private static List<Skill> skills = null;
    private static List<MissionType> missionTypes = null;
    private static Profile profile = null;

    private static Issue issue;

    public static ArrayList<Province> getProvinces() {
        return provinces;
//        ArrayList<Province> result = new ArrayList<>();
//        result.add(new Province(1, "تهران"));
//        result.add(new Province(2, "هرمزگان"));
////        for (int i = 0; i < 32; i++)
////            result.add(new Province(i, "چهارمحال و بختیاری"));
//        return result;
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
//        if (id == 1) {
//            result.add(new County(3, 1, "تهران"));
//            result.add(new County(4, 1, "دماوند"));
//            return result;
//        }
//        if (id == 2) {
//            result.add(new County(5, 2, "قشم"));
//            result.add(new County(6, 2, "بندر عبّاس"));
//            return result;
//        }
        for (County county : counties)
            if (county.getProvinceId() == id)
                result.add(county);
        return result;
    }

    public static Issue getIssue() {
        return issue;
//        return SampleData.ISSUE;
    }

    public static void setIssue(Issue issue) {
        Database.issue = issue;
    }

    public static List<Machine> getMachines() {
//        return Arrays.asList(SampleData.MACHINES);
        return machines;
    }

    public static void setMachines(List<Machine> machines) {
        Database.machines = machines;
    }

    public static List<Skill> getSkills() {
        return skills;
//        return Arrays.asList(SampleData.SKILLS);
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

    public static Profile getProfile() {
        return profile;
    }

    public static void setProfile(@Nullable Profile profile) {
        Database.profile = profile;
        if (profile != null)
            Database.setRole(profile.getRole());
        else
            Database.setRole(null);
    }

    public static void setToken(String token) {
        setCustom("AUTH", token);
    }

    public static String getToken(String defValue) {
        return getCustom("AUTH", defValue);
    }

    public static void setRole(String role) {
        setCustom("ROLE", role);
    }

    public static String getRole(String defValue) {
        return getCustom("ROLE", defValue);
    }

    public static void setCustom(String key, String value) {
        Context ctx = RoadServiceApplication.getAppContext();
        SharedPreferences sp = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sp.edit();
        if (value == null)
            editor.remove(key);
        else
            editor.putString(key, value);
        editor.apply();
    }

    public static String getCustom(String key, String defValue) {
        Context ctx = RoadServiceApplication.getAppContext();
        SharedPreferences sp = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        return sp.getString(key, defValue);
    }

    public static County getCounty(int id) {
        for (County county : getCounties())
            if (county.getId() == id)
                return county;
        return null;
    }

    public static Province getProvince(int id) {
        for (Province province : getProvinces())
            if (province.getId() == id)
                return province;
        return null;
    }
}
