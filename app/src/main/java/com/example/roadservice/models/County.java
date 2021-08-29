package com.example.roadservice.models;

public class County {
    private int id;
    private int provinceId;
    private String name;

    public County(int id, int provinceId, String name) {
        this.id = id;
        this.provinceId = provinceId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getName() {
        return name;
    }
}
