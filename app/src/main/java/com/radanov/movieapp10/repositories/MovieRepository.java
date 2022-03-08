package com.radanov.movieapp10.repositories;

import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieResponse;
import com.radanov.movieapp10.response.MovieApiClient;
import com.radanov.movieapp10.retrofit.endpoints.ApiService;
import com.radanov.movieapp10.retrofit.utils.ApiUtils;
import com.radanov.movieapp10.utils.Credentials;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    //This class is acting as repositories

    private MutableLiveData<List<MovieModel>> mMoviesPop;

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;
    private ApiService apiService;

    public static MovieRepository getInstance() {

        if (instance == null) {
            instance = new MovieRepository();
        }

        return instance;
    }

    //Constructor
    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
        apiService = ApiUtils.getApiService();
        mMoviesPop = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies() {
        apiService.getPopular(Credentials.API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //if(response.body().getResults() != null)
                   // mMoviesPop.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
            }
        });
        return mMoviesPop;
    }


    //2-Calling the method in repository
    public void searchMovieApi(String query, int pageNumber) {

        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber) {
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);
    }


    public void searchNextPage() {
        searchMovieApi(mQuery, mPageNumber + 1);
    }


}

