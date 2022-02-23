package com.radanov.movieapp10;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radanov.movieapp10.adapters.MovieOfflineAdapter;
import com.radanov.movieapp10.adapters.MovieRecycleView;
import com.radanov.movieapp10.adapters.OnMovieListener;
import com.radanov.movieapp10.databinding.FragmentHomeBinding;
import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.viewmodels.MovieListViewModel;
import com.radanov.movieapp10.viewmodels.MovieViewModelDb;
import com.radanov.movieapp10.viewmodels.MovieViewModelOffline;

import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements OnMovieListener {

    Context context;

    private FragmentHomeBinding binding;

    //RecycleView
    private RecyclerView recyclerView;
    private MovieRecycleView movieRecycleAdapter;

    //Live data View Model
    private MovieListViewModel movieListViewModel;
    private MovieViewModelOffline movieViewModelOffline;

    private MovieViewModelDb movieViewModelDb;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);
        movieViewModelOffline = new ViewModelProvider(this).get(MovieViewModelOffline.class);

        //Objects.requireNonNull(context.getSupportActionBar()).setTitle("Movies");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        recyclerView = binding.recyclerView;

        if (!haveNetworkConnection()) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);

            final MovieOfflineAdapter adapter = new MovieOfflineAdapter();
            recyclerView.setAdapter(adapter);

            movieViewModelOffline = ViewModelProviders.of(this).get(MovieViewModelOffline.class);
            movieViewModelOffline.getAllOfflineMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModelOffline>>() {
                @Override
                public void onChanged(List<MovieModelOffline> movieModelOfflines) {
                    //update recycle
                    adapter.setOfflineMovies(movieModelOfflines);
                }

            });

            adapter.setOnItemClickListener(new MovieOfflineAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(MovieModelOffline movieModelOffline) {

                    /*Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movieOff", movieModelOffline);
                    startActivity(intent);*/
                }
            });

        } else {
            //RecycleView
            configureRecyclerView();
        }

        //Calling the observers
        // observingAnyChange();
        observePopularMovie();

        //getting popular movies
        movieListViewModel.searchMoviePop(1);


        // Inflate the layout for this fragment
        return binding.getRoot();


    }


    private void configureRecyclerView() {
        //LiveData can't be passed via the constructor
        movieRecycleAdapter = new MovieRecycleView(this);
        recyclerView.setAdapter(movieRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        //Recycle View pagination
        //Loading next page of api result
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    //Here we need to display next search results on next api page
                    movieListViewModel.searchNextPage();

                }
            }
        });
    }

    private void observePopularMovie() {

        movieListViewModel.getPop().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing for any data change
                if (movieModels != null) {
                    movieViewModelOffline.deleteAll();
                    for (int i = 0; i < movieModels.size(); i++) {
                        MovieModel movie1 = movieModels.get(i);
                        String title = movie1.getTitle();
                        String imagePath = movie1.getPoster_path();
                        String overview = movie1.getOverview();
                        float rating = movie1.getVote_average();

                        MovieModelOffline movieOffline1 = new MovieModelOffline(title, imagePath, rating, overview);
                        movieViewModelOffline.insert(movieOffline1);
                        Log.v("Tag", "Saved to database: " + movieOffline1.getTitle());
                    }
                    for (MovieModel movies : movieModels) {
                        //Get the data in Log
                        Log.v("Tag", "onChanged: " + movies.getTitle());
                        movieRecycleAdapter.setmMovies(movieModels);
                        Log.v("Tag", "onChanged: " + movies.getRelease_date());
                    }
                }
            }
        });

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
