package com.example.sqlite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.db.MovieManager;
import com.example.sqlite.model.Movie;
import com.example.sqlite.R;
import com.example.sqlite.view.MainActivity;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Movie> list;
    private Context context;
    MovieManager movieManager;
    Movie movie;

    public RecyclerAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
        movieManager = MovieManager.getInstance(context);
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        String movieName = list.get(position).getMovieName();
        String movieDesc = list.get(position).getMovieDesc();
        String movieImage = list.get(position).getMovieImage();

        holder.movie_name.setText(movieName);
        holder.movie_desc.setText(movieDesc);
        holder.movie_image.setText(movieImage);

        holder.edit_movie.setOnClickListener(v -> {
            editTaskDialog();
        });
        holder.delete_movie.setOnClickListener(v -> {
            deleteMovie();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView movie_name, movie_desc, movie_image;
        ImageView edit_movie, delete_movie;


        public MyViewHolder(View itemView) {
            super(itemView);
            movie_name = itemView.findViewById(R.id.movie_name);
            movie_desc = itemView.findViewById(R.id.movie_desc);
            movie_image = itemView.findViewById(R.id.movie_image);
            edit_movie = itemView.findViewById(R.id.edit_movie);
            delete_movie = itemView.findViewById(R.id.delete_movie);
        }


    }

    public void editTaskDialog() {
        EditText edtMovieName, edtMovieDesc, edtMovieImage;
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_movie, null);


        edtMovieName = subView.findViewById(R.id.edtMovieName);
        edtMovieDesc = subView.findViewById(R.id.edtMovieDesc);
        edtMovieImage = subView.findViewById(R.id.edtMovieImage);

        String movie_name = edtMovieName.getText().toString();
        String movie_desc = edtMovieDesc.getText().toString();
        String movie_image = edtMovieImage.getText().toString();

        if (movie_name.isEmpty() || movie_desc.isEmpty() || movie_image.isEmpty()) {
            edtMovieDesc.setText(movie_desc);
            edtMovieName.setText(movie_name);
            edtMovieImage.setText(movie_image);

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("UPDATE MOVIE");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Update", (dialog, which) -> {


            if (movie_name.isEmpty() || movie_desc.isEmpty() || movie_image.isEmpty()) {
                Toast.makeText(context, "Please fill out every data", Toast.LENGTH_LONG).show();

            } else {

                Movie movie2 = new Movie(movie_name, movie_desc, movie_image);
                movieManager.updateMovieToDb(movie.getMovieName(), movie2);

                Toast.makeText(context, "Movie updated!", Toast.LENGTH_LONG).show();

            }


            dialog.dismiss();

        });

        builder.setNegativeButton("cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    public void deleteMovie() {
        movieManager.deleteMovieToDb(movie.getMovieName());

    }

}
