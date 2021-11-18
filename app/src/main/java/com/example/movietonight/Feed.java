package com.example.movietonight;

import android.widget.ImageView;

public class Feed {
    private ImageView profile;
    private String nickName;
    private String movieTitle;
    private String reviewTitle;
    private String genre;
    private String review;
    private int like ,dislike;

    public Feed(ImageView profile, String nickName, String reviewTitle,String movieTitle, String genre, String review, int like, int dislike) {
        this.profile = profile;
        this.nickName = nickName;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.review = review;
        this.like = like;
        this.dislike = dislike;
        this.reviewTitle=reviewTitle;
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

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }
}
