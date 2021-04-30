package com.example.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.model.Movie;

import java.util.ArrayList;


public class Sqlite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_TABLE_MOVIE_ID = "movie_id";
    public static final String MOVIES_TABLE_MOVIE_NAME = "movie_name";
    public static final String MOVIES_TABLE_MOVIE_DESC = "movie_desc";
    public static final String MOVIES_TABLE_MOVIE_IMAGE = "movie_image";


    private static Sqlite sqliteInstance = null;
    SQLiteDatabase db;


    public Sqlite(Context context) {
        super(context, MOVIES_TABLE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized Sqlite getInstance(Context context) {
        if (sqliteInstance == null) {
            sqliteInstance = new Sqlite(context.getApplicationContext());
        }
        return sqliteInstance;
    }

    private static final String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            MOVIES_TABLE_NAME + "(" +
            MOVIES_TABLE_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            MOVIES_TABLE_MOVIE_NAME + " TEXT, " +
            MOVIES_TABLE_MOVIE_DESC + " TEXT, " +
            MOVIES_TABLE_MOVIE_IMAGE + " TEXT " + ")";


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);

    }

    public void addMovie(Movie movie) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIES_TABLE_MOVIE_NAME, movie.getMovieName());
        values.put(MOVIES_TABLE_MOVIE_DESC, movie.getMovieDesc());
        values.put(MOVIES_TABLE_MOVIE_IMAGE, movie.getMovieImage());
        db.insert(MOVIES_TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMovie(String oldMovieName) {
        String sql = "DELETE FROM " + MOVIES_TABLE_NAME + " WHERE " + MOVIES_TABLE_MOVIE_NAME + "='" + oldMovieName + "'";
        db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();

    }

    public boolean updateMovie(String oldMovieName, Movie movie) {

        ContentValues args = new ContentValues();
        db = this.getWritableDatabase();
        args.put(MOVIES_TABLE_MOVIE_NAME, movie.getMovieName());
        args.put(MOVIES_TABLE_MOVIE_DESC, movie.getMovieDesc());
        args.put(MOVIES_TABLE_MOVIE_IMAGE, movie.getMovieImage());
        int result = db.update(MOVIES_TABLE_NAME, args, MOVIES_TABLE_MOVIE_NAME + "=" + oldMovieName, null);
        return result > 0;
    }

    public ArrayList<Movie> getAllData() {
        String sql = "SELECT * FROM " + MOVIES_TABLE_NAME;
        db = this.getReadableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String movie_name = cursor.getString(cursor.getColumnIndex(MOVIES_TABLE_MOVIE_NAME));
                String movie_desc = cursor.getString(cursor.getColumnIndex(MOVIES_TABLE_MOVIE_DESC));
                String movie_image = cursor.getString(cursor.getColumnIndex(MOVIES_TABLE_MOVIE_IMAGE));
                movieList.add(new Movie(movie_name, movie_desc, movie_image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movieList;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
