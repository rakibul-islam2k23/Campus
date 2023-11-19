package com.example.userapp;

public class NoticeModelClass {

    String notice ,noticeTime,postId,url;
    int postLike;



    public NoticeModelClass() {
    }

    public NoticeModelClass(String notice, String noticeTime, String postId, String url, int postLike) {
        this.notice = notice;
        this.noticeTime = noticeTime;
        this.postId = postId;
        this.url = url;
        this.postLike = postLike;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }
}
