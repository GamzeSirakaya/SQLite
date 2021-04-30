package com.example.sqlite.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sqlite.db.MovieManager;
import com.example.sqlite.R;
import com.example.sqlite.adapter.RecyclerAdapter;
import com.example.sqlite.db.Sqlite;
import com.example.sqlite.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    private ArrayList<Movie> allMovies = new ArrayList<>();

    private ArrayList<Integer> integers = new ArrayList<>();

    private String gamze = "Gamze";

    private static MovieManager movieManager;
    EditText edtMovieName, edtMovieDesc, edtMovieImage;
    private String TAG = MainActivity.class.getSimpleName();
    private boolean DEBUG = true;
    private Context ctx;

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;


        movieManager = MovieManager.getInstance(ctx);

        allMovies = movieManager.getAllMovies();
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        recyclerAdapter = new RecyclerAdapter(ctx, allMovies);
        recyclerView.setAdapter(recyclerAdapter);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> addTaskDialog());



/*

        integers.add(150);
        integers.add(300);
        integers.add(450);

        String myArray[] = {"sfgf", "hh", "mm"};
        Log(myArray[0]);
        Log(myArray[1]);
        Log(myArray[2]);

        integers.get(1);


        for (int i = 0; i < integers.size(); i++) {
            Log("" + integers.get(i));
        }


        ArrayList<String> array = new ArrayList<>();
        array.add("asd");
        array.add(String.valueOf(5));
        array.add("sggh");
        array.add("ff");
        array.add("Gamze");
        array.add("Sırakaya");

        for (int i = 0; i < array.size(); i++) {
            Log("MyArrayListWithRegularFor: " + array.get(i));
        }

        for (String s : array) {
            Log("MyArrayListWithInlineFor: " + s);
        }
*/


        /*fabGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                for (Movie movie : allMovies) {
                    recyclerAdapter = new RecyclerAdapter(ctx, allMovies);
                    recyclerView.setAdapter(recyclerAdapter);
                    Log("MovieName: " + movie.getMovieName());
                    Log("MovieDesc: " + movie.getMovieDesc());
                    Log("MovieImage: " + movie.getMovieImage());
                }
            }
        });*/


    }


    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_movie, null);


        edtMovieName = subView.findViewById(R.id.edtMovieName);
        edtMovieDesc = subView.findViewById(R.id.edtMovieDesc);
        edtMovieImage = subView.findViewById(R.id.edtMovieImage);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ADD MOVIE");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD", (dialog, which) -> {

            String movie_name = edtMovieName.getText().toString();
            String movie_desc = edtMovieDesc.getText().toString();
            String movie_image = edtMovieImage.getText().toString();

            /*Log("MovieName:" + movie_name);
            Log("MovieDesc:" + movie_desc);
            Log("MovieImage:" + movie_image);*/

            if (movie_name.isEmpty() || movie_desc.isEmpty() || movie_image.isEmpty()) {
                Log("FieldEmpty");
                Toast.makeText(getApplicationContext(), "Please fill out every data", Toast.LENGTH_LONG).show();
            } else {

                Log("DataReadySavetoDB");
                Movie movie2 = new Movie(movie_name, movie_desc, movie_image);
                movieManager.addMovieToDb(movie2);
                Toast.makeText(getApplicationContext(), "Movie added!", Toast.LENGTH_LONG).show();

                edtMovieName.setText("");
                edtMovieDesc.setText("");
                edtMovieImage.setText("");


            }
            dialog.dismiss();

        });

        builder.setNegativeButton("cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();


    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

}