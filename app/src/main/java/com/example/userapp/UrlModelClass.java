package com.example.userapp;

import android.net.Uri;

public class UrlModelClass {
    String  url ;

    public UrlModelClass() {
    }

    public UrlModelClass(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
