package com.radanov.movieapp10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.radanov.movieapp10.R;

import com.radanov.movieapp10.io.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieOfflineAdapter extends RecyclerView.Adapter<MovieOfflineAdapter.MovieOfflineHolder> {


    private List<MovieModel> moviesOffline = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public MovieOfflineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieOfflineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieOfflineHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        MovieModel currentMovie = moviesOffline.get(position);

        //holder.textViewTitle.setText(currentMovie.movieDbTitle);
       // holder.textViewOverview.setText(currentMovie.movieDbDescription);
        holder.ratingBar.setRating(currentMovie.getVote_average()/2);


        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/" + currentMovie.getPoster_path()).into(((MovieOfflineHolder)holder).imageView);


    }

    @Override
    public int getItemCount() {
        return moviesOffline.size();
    }

    public void setOfflineMovies(List<MovieModel> moviesOffline)
    {
        this.moviesOffline = moviesOffline;
        notifyDataSetChanged();
    }

    public MovieModel getOfflinemovies(int position)
    {
        return moviesOffline.get(position);
    }



    class MovieOfflineHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewOverview;
        ImageView imageView;
        RatingBar ratingBar;


        public MovieOfflineHolder(@NonNull View itemView) {
            super(itemView);

            //textViewTitle = itemView.findViewById(R.id.textViewTitleWatch);
           // textViewOverview = itemView.findViewById(R.id.textViewOverviewWatch);
            imageView = itemView.findViewById(R.id.movie_img_watchlist);
            ratingBar = itemView.findViewById(R.id.rating_bar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                       listener.onItemClick(moviesOffline.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MovieModel movieModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
  }








