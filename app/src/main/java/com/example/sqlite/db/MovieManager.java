package com.example.sqlite.db;

import android.content.Context;

import com.example.sqlite.model.Movie;

import java.util.ArrayList;

public class MovieManager {
    private static MovieManager movieManager = null;
    private Sqlite mSqlite;
    Context ctx;

    public MovieManager(Context ctx) {
        this.ctx = ctx;
        mSqlite = Sqlite.getInstance(ctx);
    }

    public static MovieManager getInstance(Context context) {
        if (movieManager == null) {
            movieManager = new MovieManager(context);
        }
        return movieManager;
    }

    public void addMovieToDb(Movie movie) {
        mSqlite.addMovie(movie);
    }

    public boolean updateMovieToDb(String oldMovieName, Movie movie) {
        return mSqlite.updateMovie(oldMovieName, movie);
    }

    public void deleteMovieToDb(String oldMovieName) {
        mSqlite.deleteMovie(oldMovieName);
    }

    public ArrayList<Movie> getAllMovies() {
        return mSqlite.getAllData();
    }


}
