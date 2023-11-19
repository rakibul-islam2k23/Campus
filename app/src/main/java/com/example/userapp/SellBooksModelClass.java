package com.example.userapp;

public class SellBooksModelClass {

    String name;
    String department;
    String uid;
    String semister;
    String problem;
    String price;
    String oneBook;
    String twoBook;
    String threeBook;
    String fourBook;
    String fiveBook;
    String sixBook;
    String timeAndDate;
    String number;
    String  totalBooks;

    public SellBooksModelClass() {
    }


    public SellBooksModelClass(String name, String department, String uid, String semister, String problem, String price, String oneBook, String twoBook, String threeBook, String fourBook, String fiveBook, String sixBook, String timeAndDate, String number, String totalBooks) {
        this.name = name;
        this.department = department;
        this.uid = uid;
        this.semister = semister;
        this.problem = problem;
        this.price = price;
        this.oneBook = oneBook;
        this.twoBook = twoBook;
        this.threeBook = threeBook;
        this.fourBook = fourBook;
        this.fiveBook = fiveBook;
        this.sixBook = sixBook;
        this.timeAndDate = timeAndDate;
        this.number = number;
        this.totalBooks = totalBooks;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOneBook() {
        return oneBook;
    }

    public void setOneBook(String oneBook) {
        this.oneBook = oneBook;
    }

    public String getTwoBook() {
        return twoBook;
    }

    public void setTwoBook(String twoBook) {
        this.twoBook = twoBook;
    }

    public String getThreeBook() {
        return threeBook;
    }

    public void setThreeBook(String threeBook) {
        this.threeBook = threeBook;
    }

    public String getFourBook() {
        return fourBook;
    }

    public void setFourBook(String fourBook) {
        this.fourBook = fourBook;
    }

    public String getFiveBook() {
        return fiveBook;
    }

    public void setFiveBook(String fiveBook) {
        this.fiveBook = fiveBook;
    }

    public String getSixBook() {
        return sixBook;
    }

    public void setSixBook(String sixBook) {
        this.sixBook = sixBook;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(String totalBooks) {
        this.totalBooks = totalBooks;
    }
}
