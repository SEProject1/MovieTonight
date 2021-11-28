package com.example.movietonight.Class;

public class Notification {
    private String userid;
    private String text;
    private String reviewTitle;
    private String nickname;

    public Notification(String userid, String text, String reviewTitle, String nickname) {
        this.userid = userid;
        this.text = text;
        this.reviewTitle = reviewTitle;
        this.nickname = nickname;
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

    public String getNickname() {return nickname;}

    public void setNickname(String nickname) {this.nickname = nickname; }
}
