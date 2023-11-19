package com.example.userapp;

public class UserDetailsModelClass {
    String userName;
    String email;
    String password;
    String semister;
    String number;
    String uid;
    String department;

    public UserDetailsModelClass() {
    }

    public UserDetailsModelClass(String userName, String email, String password, String semister, String number, String uid, String department) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.semister = semister;
        this.number = number;
        this.uid = uid;
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
