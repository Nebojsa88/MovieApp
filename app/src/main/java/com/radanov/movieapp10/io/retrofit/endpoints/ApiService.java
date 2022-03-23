package com.radanov.movieapp10.io.retrofit.endpoints;

import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.io.models.MovieResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //Search for movies title
    //https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&query=jack
   /* @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key")String key,
            @Query("query")String query,
            @Query("page")int page);*/

    //Get popular movies
    //https://api.themoviedb.org/3/movie/popular       ?api_key=543ced1b67d908afce8f45154ad28aef&page=1

    @GET("/3/movie/popular")
    Call<MovieResponse> getPopular(
            @Query("api_key")String key,
            @Query("page")int page);

    //Search for movies by id
    //https://api.themoviedb.org/3/movie/550?api_key=543ced1b67d908afce8f45154ad28aef
    //ID 550 is for Fight club
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key")String api_key
    );




}
