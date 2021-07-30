package com.radanov.movieapp10.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radanov.movieapp10.models.MovieModel;

import java.util.List;

//This class is for getting multiple movies (movies lists) - popular movies
public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose()
    private int totalCount;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotalCount(){
        return totalCount;
    }
    public List<MovieModel> getMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "totalCount=" + totalCount +
                ", movies=" + movies +
                '}';
    }
}
