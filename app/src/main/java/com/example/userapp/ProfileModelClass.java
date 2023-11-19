package com.example.userapp;

import android.widget.EditText;

public class ProfileModelClass {

    String name;
    String department;
    String semister;
    String number;
    String url;

    public ProfileModelClass() {
    }

    public ProfileModelClass(String name, String department, String semister, String number, String url) {
        this.name = name;
        this.department = department;
        this.semister = semister;
        this.number = number;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
