package com.radanov.movieapp10.viewmodels;

import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieListViewModel extends ViewModel {

    //This class is used for ViewModel
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPop() {
        return movieRepository.getPop();
    }

    //3-Calling method in view-model
    public void searchMovieApi(String query, int pageNumber) {
        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber) {
        movieRepository.searchMoviePop(pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }


}
