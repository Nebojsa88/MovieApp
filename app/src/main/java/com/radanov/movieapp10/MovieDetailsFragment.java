package com.radanov.movieapp10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.radanov.movieapp10.databinding.FragmentMovieDetailsBinding;
import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;


public class MovieDetailsFragment extends Fragment {


    FragmentMovieDetailsBinding binding;
    private MovieModel movie;
    Context context;

    MovieViewModelDb movieViewModelDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);

        movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);


        if (getArguments() != null) {
            movie = (MovieModel) getArguments().getSerializable("movie");
        }
        initView();


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void initView() {

        binding.textViewTitleDetails.setText(movie.getTitle());
        binding.textViewDescDetails.setText(movie.getOverview());
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+ movie.getPoster_path()).into(binding.imageViewDetails);
        binding.buttonAddWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String movieTitle = binding.textViewTitleDetails.getText().toString();
                String movieDescription = binding.textViewDescDetails.getText().toString();

                MovieModelDb movieModelDb = new MovieModelDb(movieTitle, movieDescription, movie.getPoster_path());


                movieViewModelDb.insert(movieModelDb);

                Toast.makeText(context, "Movie Saved to your watchlist", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(MovieDetailsActivity.this, MovieListActivity.class);
                //startActivity(i);
                //finish();
            }
        });
    }
}