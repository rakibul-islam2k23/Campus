package com.example.userapp;

public class BookListModelClass {

    String oneBook;
    String twoBook;
    String threeBook;
    String fourBook;
    String sixBook;

    public BookListModelClass(String oneBook, String twoBook, String threeBook, String fourBook, String sixBook) {
        this.oneBook = oneBook;
        this.twoBook = twoBook;
        this.threeBook = threeBook;
        this.fourBook = fourBook;
        this.sixBook = sixBook;
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

    public String getSixBook() {
        return sixBook;
    }

    public void setSixBook(String sixBook) {
        this.sixBook = sixBook;
    }
}
