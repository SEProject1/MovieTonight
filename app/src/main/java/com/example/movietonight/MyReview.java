package com.example.movietonight;

public class MyReview {//사용자 영화 리뷰
    private String movieTitle;
    private String date;//영화 감상 날짜
    private String reviewTitle;
    private String review;

    public MyReview(String movieTitle, String date, String reviewTitle,String review) {
        this.movieTitle = movieTitle;
        this.date = date;
        this.reviewTitle=reviewTitle;
        this.review=review;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDate() {
        return date;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
