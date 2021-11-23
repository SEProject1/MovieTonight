package com.example.movietonight.Class;

public class Notification {
    private String userid;
    private String text;
    private String reviewId;
    private boolean isReview;

    public Notification(String userid, String text, String reviewId, boolean isReview) {
        this.userid = userid;
        this.text = text;
        this.reviewId = reviewId;
        this.isReview = isReview;
    }

    public Notification() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(boolean isReview) {
        this.isReview = isReview;
    }
}
