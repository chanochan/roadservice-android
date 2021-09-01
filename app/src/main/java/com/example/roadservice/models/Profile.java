package com.example.roadservice.models;

public class Profile {
    private String role;
    private String phone;
    private String name;

    public Profile(String name, String role, String phone) {
        this.role = role;
        this.phone = phone;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
