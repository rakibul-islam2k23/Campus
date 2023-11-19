package com.example.userapp;

public class RequestModelClass {

    String ownerName;
    String ownerNumber;
    String ownerPrice;
    String ownerDateAndTime;

    String buyerName;
    String buyerNumber;
    String buyerPrice;
    String buyerDataAndTime;

    public RequestModelClass() {
    }

    public RequestModelClass(String ownerName, String ownerNumber, String ownerPrice, String ownerDateAndTime, String buyerName, String buyerNumber, String buyerPrice, String buyerDataAndTime) {
        this.ownerName = ownerName;
        this.ownerNumber = ownerNumber;
        this.ownerPrice = ownerPrice;
        this.ownerDateAndTime = ownerDateAndTime;
        this.buyerName = buyerName;
        this.buyerNumber = buyerNumber;
        this.buyerPrice = buyerPrice;
        this.buyerDataAndTime = buyerDataAndTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getOwnerPrice() {
        return ownerPrice;
    }

    public void setOwnerPrice(String ownerPrice) {
        this.ownerPrice = ownerPrice;
    }

    public String getOwnerDateAndTime() {
        return ownerDateAndTime;
    }

    public void setOwnerDateAndTime(String ownerDateAndTime) {
        this.ownerDateAndTime = ownerDateAndTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getBuyerPrice() {
        return buyerPrice;
    }

    public void setBuyerPrice(String buyerPrice) {
        this.buyerPrice = buyerPrice;
    }

    public String getBuyerDataAndTime() {
        return buyerDataAndTime;
    }

    public void setBuyerDataAndTime(String buyerDataAndTime) {
        this.buyerDataAndTime = buyerDataAndTime;
    }
}
