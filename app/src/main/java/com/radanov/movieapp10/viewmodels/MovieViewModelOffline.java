package com.radanov.movieapp10.viewmodels;

import android.app.Application;

import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.repositories.MovieRepositoryOffline;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModelOffline extends AndroidViewModel {

    private MovieRepositoryOffline repository;
    private LiveData<List<MovieModelOffline>> movies;


    public MovieViewModelOffline(@NonNull Application application) {
        super(application);

        repository = new MovieRepositoryOffline(application);
        movies = repository.getAllOfflineMovies();
    }

    public LiveData<List<MovieModelOffline>> getPopularMovies() {
        return repository.getPopularMovies();
    }

    public void insert(MovieModelOffline movieModelOffline)
    {
        repository.insert(movieModelOffline);
    }

    public void update(MovieModelOffline movieModelOffline)
    {

        repository.update(movieModelOffline);
    }

    public void delete(MovieModelOffline movieModelOffline)
    {
        repository.delete(movieModelOffline);
    }
    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<MovieModelOffline>> getAllOfflineMovies()
    {
        return movies;
    }
}
