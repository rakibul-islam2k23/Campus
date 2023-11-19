package com.example.userapp;

public class ClassTimeModelClass {
    String recentTime;
    String week;
    String startClass;
    String endClass;
    String subject;
    String teacher;
    String place;

    public ClassTimeModelClass() {
    }

    public ClassTimeModelClass(String recentTime, String week, String startClass, String endClass, String subject, String teacher, String place) {
        this.recentTime = recentTime;
        this.week = week;
        this.startClass = startClass;
        this.endClass = endClass;
        this.subject = subject;
        this.teacher = teacher;
        this.place = place;
    }

    public String getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(String recentTime) {
        this.recentTime = recentTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getStartClass() {
        return startClass;
    }

    public void setStartClass(String startClass) {
        this.startClass = startClass;
    }

    public String getEndClass() {
        return endClass;
    }

    public void setEndClass(String endClass) {
        this.endClass = endClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
