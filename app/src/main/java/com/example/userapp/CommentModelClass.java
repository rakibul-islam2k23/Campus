package com.example.userapp;

public class CommentModelClass {

    String comment;
    String commentTime;
    String commenterName;
    String commentPostId;
    String commentRandomKey;
    String commentDepartment;
    String commentUserAut;
    String imageUrl;

    public CommentModelClass() {
    }

    public CommentModelClass(String comment, String commentTime, String commenterName, String commentPostId, String commentRandomKey, String commentDepartment, String commentUserAut, String imageUrl) {
        this.comment = comment;
        this.commentTime = commentTime;
        this.commenterName = commenterName;
        this.commentPostId = commentPostId;
        this.commentRandomKey = commentRandomKey;
        this.commentDepartment = commentDepartment;
        this.commentUserAut = commentUserAut;
        this.imageUrl = imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentPostId() {
        return commentPostId;
    }

    public void setCommentPostId(String commentPostId) {
        this.commentPostId = commentPostId;
    }

    public String getCommentRandomKey() {
        return commentRandomKey;
    }

    public void setCommentRandomKey(String commentRandomKey) {
        this.commentRandomKey = commentRandomKey;
    }

    public String getCommentDepartment() {
        return commentDepartment;
    }

    public void setCommentDepartment(String commentDepartment) {
        this.commentDepartment = commentDepartment;
    }

    public String getCommentUserAut() {
        return commentUserAut;
    }

    public void setCommentUserAut(String commentUserAut) {
        this.commentUserAut = commentUserAut;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
