package com.radanov.movieapp10.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.radanov.movieapp10.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Widgets
    ImageView imageView;
    RatingBar ratingBar;
    ProgressBar progressBar;
    //Click listener
    OnMovieListener onMovieListener;


    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.movie_img_watchlist);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        progressBar = itemView.findViewById(R.id.movie_item_progressBar);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
