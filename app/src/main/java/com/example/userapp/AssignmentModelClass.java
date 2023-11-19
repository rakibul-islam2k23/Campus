package com.example.userapp;

public class AssignmentModelClass {

    String subject;
    String teacher;
    String jobNumber;
    String checkInDate;
    String checkOutDate;
    String assignmentTime;
    String userUid;
    String pageNo;

    public AssignmentModelClass() {
    }

    public AssignmentModelClass(String subject, String teacher, String jobNumber, String checkInDate, String checkOutDate, String assignmentTime, String userUid, String pageNo) {
        this.subject = subject;
        this.teacher = teacher;
        this.jobNumber = jobNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.assignmentTime = assignmentTime;
        this.userUid = userUid;
        this.pageNo = pageNo;
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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getAssignmentTime() {
        return assignmentTime;
    }

    public void setAssignmentTime(String assignmentTime) {
        this.assignmentTime = assignmentTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
}
