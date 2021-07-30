package com.radanov.movieapp10.repositories;

import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.response.MovieApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MovieRepository {
    //This class is acting as repositories

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){

        if(instance == null){
            instance = new MovieRepository();
        }

        return instance;
    }
    //Constructor
    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
     }

    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }




     //2-Calling the method in repository
    public void searchMovieApi(String query, int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);
    }


    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }



}

