package com.example.movietonight;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feed implements Comparable<Feed>{
    private ImageView profile;
    private String nickName;
    private String movieTitle;
    private String reviewTitle;
    private String genre;
    private String review;
    private String like ,dislike;
    private Date mdate;
    private final String idToken;

    public Feed(ImageView profile, String nickName, String reviewTitle,String movieTitle, String genre, String review, String like, String dislike,String mdate,String idToken) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        this.profile = profile;
        this.nickName = nickName;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.review = review;
        this.like = like;
        this.dislike = dislike;
        this.reviewTitle=reviewTitle;
        this.idToken=idToken;
        try {
            this.mdate=format.parse(mdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public int compareTo(Feed feed){
        if(feed.mdate.after(mdate)){
            return 1;
        }else if(mdate.after(feed.mdate)){
            return -1;
        }
        return 0;
    }
    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String name) {
        this.nickName = name;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLike() {
        return like;
    }

    public String getDislike() {
        return dislike;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public Date getMdate() {
        return mdate;
    }

    public String getIdToken() {
        return idToken;
    }
}
