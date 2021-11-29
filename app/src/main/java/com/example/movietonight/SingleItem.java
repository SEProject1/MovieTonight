package com.example.movietonight;

import java.util.ArrayList;

//상위 리사이클러뷰아이템의 하위 리사이클러뷰에 들어갈 하위아이템클래스를 정의한다.
public class SingleItem {

    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private String vote_average;
    ArrayList<Integer> genre_ids = new ArrayList<>();

    public SingleItem(String title, String original_title, String poster_path, String overview, String backdrop_path, String release_date, String vote_average, ArrayList<Integer> genre_ids) {
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.genre_ids = genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() { return vote_average; }

    public ArrayList<Integer> getGenre_ids() { return genre_ids; }
}

