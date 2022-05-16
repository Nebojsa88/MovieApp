package com.radanov.movieapp10.ui.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.radanov.movieapp10.R;
import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.utils.Credentials;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MovieRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;


    public MovieRecycleView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == DISPLAY_SEARCH) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);

            return new MovieViewHolder(view, onMovieListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout, parent, false);

            return new PopularMovieViewHolder(view, onMovieListener);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == DISPLAY_SEARCH) {
            //Vote average is over 10 , and our rating bar is over 5 stars : dividing by 2
            ((MovieViewHolder) holder).ratingBar.setRating((mMovies.get(position).getVote_average()) / 2);

            //ImageView using Glide library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ((MovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ((MovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(((MovieViewHolder) holder).imageView);

        } else {

            //Vote avarge is over 10 , and our rating bar is over 5 stars : dividing by 2
            ((PopularMovieViewHolder) holder).ratingBarPop.setRating((mMovies.get(position).getVote_average()) / 2);

            //ImageView using Glide library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ((PopularMovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ((PopularMovieViewHolder) holder).progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(((PopularMovieViewHolder) holder).imagePop);


        }

    }

    @Override
    public int getItemViewType(int position) {
        if (Credentials.POPULAR) {
            return DISPLAY_POP;
        } else {
            return DISPLAY_SEARCH;
        }
    }

    @Override
    public int getItemCount() {

        if (mMovies != null) {
            return mMovies.size();
        }

        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the ID of the movie clicked
    public MovieModel getSelectedMovie(int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;

    }


}
