package com.example.userapp;

public class PdfModelClass {
    String uri;
    String details;
    String timeAndDate;

    public PdfModelClass() {
    }

    public PdfModelClass(String uri, String details, String timeAndDate) {
        this.uri = uri;
        this.details = details;
        this.timeAndDate = timeAndDate;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }
}
