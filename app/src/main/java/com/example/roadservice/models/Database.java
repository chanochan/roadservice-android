package com.example.roadservice.models;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Province> provinces = null;
    private static ArrayList<County> counties = null;

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
}
