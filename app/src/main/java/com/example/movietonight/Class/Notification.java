package com.example.movietonight.Class;

public class Notification {
    private String userid;
    private String text;
    private String postid;
    private boolean ispost;

    public Notification(String userid, String text, String postid, boolean ispost) {
        this.userid = userid;
        this.text = text;
        this.postid = postid;
        this.ispost = ispost;
    }

    public Notification() {
    }




}
