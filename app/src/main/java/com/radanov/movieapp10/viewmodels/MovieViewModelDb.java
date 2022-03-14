package com.radanov.movieapp10.viewmodels;

import android.app.Application;

import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.repositories.MovieRepositoryDb;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieViewModelDb extends AndroidViewModel {

    private MovieRepositoryDb repository;
    private LiveData<List<MovieModelOffline>> movies;


    public MovieViewModelDb(@NonNull Application application) {
        super(application);

        repository = new MovieRepositoryDb(application);
        movies = repository.getAllDbMovies();
    }
    public void insert(MovieModelOffline movieModelDb)
    {
        repository.insert(movieModelDb);
    }

    public void update(MovieModelOffline movieModelDb)
    {

        repository.update(movieModelDb);
    }

    public void delete(MovieModelOffline movieModelDb)
    {
        repository.delete(movieModelDb);
    }

    public boolean checkIfAlreadyHave(MovieModelOffline movie) {

        return Objects.requireNonNull(movies.getValue()).contains(movie);



    }

    public LiveData<List<MovieModelOffline>> getAllDbMovies()
    {
        return movies;
    }
}
