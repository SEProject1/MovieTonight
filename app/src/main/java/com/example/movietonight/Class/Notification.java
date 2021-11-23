package com.example.movietonight.Class;

public class Notification {
    private String userid;
    private String text;
    private String reviewTitle;
    private boolean isReview;

    public Notification(String userid, String text, String reviewTitle, boolean isReview) {
        this.userid = userid;
        this.text = text;
        this.reviewTitle = reviewTitle;
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

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(boolean isReview) {
        this.isReview = isReview;
    }
}
