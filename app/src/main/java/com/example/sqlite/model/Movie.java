package com.example.sqlite.model;

public class Movie {
    private String movieName;
    private String movieDesc;
    private String movieImage;

    public Movie(String movieName, String movieDesc, String movieImage) {
        this.movieName = movieName;
        this.movieDesc = movieDesc;
        this.movieImage = movieImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
}
