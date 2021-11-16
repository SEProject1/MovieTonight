package com.example.movietonight;

public class FavoriteMovie {//찜한 영화 정보를 저장하는 클래스
    String movieTitle;

    public FavoriteMovie(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
