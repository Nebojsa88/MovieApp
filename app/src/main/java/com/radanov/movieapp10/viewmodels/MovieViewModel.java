package com.radanov.movieapp10.viewmodels;

import android.app.Application;
import android.content.Context;

import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.io.repositories.MovieRepository;

import java.net.ContentHandler;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<MovieModel>> movies;
    private LiveData<List<MovieModel>> watchlist;


    public MovieViewModel(@NonNull Application application) {
        super(application);

        repository = new MovieRepository(application);
        movies = repository.getAllOfflineMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies() {
        return repository.getPopularMovies();
    }

    public LiveData<List<MovieModel>> getWatchlistMovies() {
        return repository.getMoviesWatchlist();
    }


    public void insert(MovieModel movieModel)
    {
        repository.insert(movieModel);
    }

    public void update(MovieModel movieModel)
    {

        repository.update(movieModel);
    }

    public void delete(MovieModel movieModel)
    {
        repository.delete(movieModel);
    }
    public void deleteAll()
    {
        repository.deleteAll();
    }

    public void insertWatchlist(MovieModel movieModel)
    {
        repository.insertWatchlist(movieModel);
    }

    public void updateWatchlist(MovieModel movieModel)
    {

        repository.updateWatchlist(movieModel);
    }

    public void deleteWatchlist(MovieModel movieModel)
    {
        repository.deleteWatchlist(movieModel);
    }

    public LiveData<List<MovieModel>> getAllOfflineMovies()
    {
        return movies;
    }
}
