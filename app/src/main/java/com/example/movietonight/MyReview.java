package com.example.movietonight;

public class MyReview {//사용자 영화 리뷰
    private String movieTitle;
    private String date;//영화 감상 날짜
    private String reviewTitle;

    public MyReview(String movieTitle, String date, String reviewTitle) {
        this.movieTitle = movieTitle;
        this.date = date;
        this.reviewTitle=reviewTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }
}
