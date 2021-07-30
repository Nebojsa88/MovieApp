package com.radanov.movieapp10.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radanov.movieapp10.models.MovieModel;

//This class is for single movie request
public class MovieResponse {

    //1- finding movie object
    @SerializedName("results")
    @Expose
    private MovieModel movie ;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }



}
