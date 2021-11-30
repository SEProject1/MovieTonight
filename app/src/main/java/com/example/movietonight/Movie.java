package com.example.movietonight;

import java.util.ArrayList;

public class Movie {
    //Json 데이터를 참조
    private String id;
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private String vote_average;
    ArrayList<Integer> genre_ids = new ArrayList<>();

    public Movie(String id, String title, String original_title, String poster_path, String overview, String backdrop_path, String release_date, String vote_average, ArrayList<Integer> genre_ids) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.genre_ids = genre_ids;
    }

    public String getId() { return id; }

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
