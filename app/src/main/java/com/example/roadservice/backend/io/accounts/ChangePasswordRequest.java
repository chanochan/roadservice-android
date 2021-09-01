package com.example.roadservice.backend.io.accounts;

public class ChangePasswordRequest {
    private String current;
    private String next;

    public ChangePasswordRequest(String current, String next) {
        this.current = current;
        this.next = next;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
