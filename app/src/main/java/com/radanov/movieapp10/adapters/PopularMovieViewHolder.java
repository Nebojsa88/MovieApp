package com.radanov.movieapp10.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.radanov.movieapp10.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnMovieListener onMovieListener;

    ImageView imagePop;
    RatingBar ratingBarPop;


    public PopularMovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        imagePop = itemView.findViewById(R.id.movie_img_popualar);
        ratingBarPop = itemView.findViewById(R.id.rating_bar_pop);
        itemView.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

    }
}
