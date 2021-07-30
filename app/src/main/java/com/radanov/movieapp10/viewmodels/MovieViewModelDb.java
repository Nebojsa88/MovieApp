package com.radanov.movieapp10.viewmodels;

import android.app.Application;

import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.repositories.MovieRepositoryDb;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModelDb extends AndroidViewModel {

    private MovieRepositoryDb repository;
    private LiveData<List<MovieModelDb>> movies;


    public MovieViewModelDb(@NonNull Application application) {
        super(application);

        repository = new MovieRepositoryDb(application);
        movies = repository.getAllDbMovies();
    }
    public void insert(MovieModelDb movieModelDb)
    {
        repository.insert(movieModelDb);
    }

    public void update(MovieModelDb movieModelDb)
    {

        repository.update(movieModelDb);
    }

    public void delete(MovieModelDb movieModelDb)
    {
        repository.delete(movieModelDb);
    }

    public LiveData<List<MovieModelDb>> getAllDbMovies()
    {
        return movies;
    }
}
