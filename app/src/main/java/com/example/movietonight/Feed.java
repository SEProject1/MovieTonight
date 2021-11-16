package com.example.movietonight;

import android.widget.ImageView;

public class Feed {
    ImageView profile;
    String name;
    String movieTitle;
    String genre;
    String review;
    int like ,dislike;

    public Feed(ImageView profile, String name, String movieTitle, String genre, String review, int like, int dislike) {
        this.profile = profile;
        this.name = name;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.review = review;
        this.like = like;
        this.dislike = dislike;
    }

    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
