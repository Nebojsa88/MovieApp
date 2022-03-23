package com.radanov.movieapp10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.radanov.movieapp10.R;

import com.radanov.movieapp10.io.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.MovieDbHolder> {


    private List<MovieModel> moviesDb = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public MovieDbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item_watch, parent, false);
        return new MovieDbHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDbHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        MovieModel currentMovie = moviesDb.get(position);
        holder.textViewTitle.setText(currentMovie.getTitle());
       //holder.textViewOverview.setText(currentMovie.getOverview());

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/" + currentMovie.getPoster_path())
                .into(((MovieDbHolder)holder).imageView);


    }

    @Override
    public int getItemCount() {
        return moviesDb.size();
    }

    public void setMoviesDb(List<MovieModel> moviesDb)
    {
        this.moviesDb = moviesDb;
        notifyDataSetChanged();
    }

    public MovieModel getMoviesDb(int position)
    {
        return moviesDb.get(position);
    }



    class MovieDbHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewOverview;
        ImageView imageView;

        public MovieDbHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitleWatch);
            //textViewOverview = itemView.findViewById(R.id.textViewOverviewWatch);
            imageView = itemView.findViewById(R.id.movie_img_watchlist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                       listener.onItemClick(moviesDb.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MovieModel movieModelDb);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
  }








