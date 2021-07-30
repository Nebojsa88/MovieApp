package com.radanov.movieapp10.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class MovieModelDb {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String movieDbTitle;

    public String movieDbDescription;

    public String imagePath;

    public MovieModelDb(String movieDbTitle, String movieDbDescription, String imagePath) {
        this.movieDbTitle = movieDbTitle;
        this.movieDbDescription = movieDbDescription;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getMovieDbTitle() {
        return movieDbTitle;
    }

    public String getMovieDbDescription() {
        return movieDbDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieDbTitle(String movieDbTitle) {
        this.movieDbTitle = movieDbTitle;
    }

    public void setMovieDbDescription(String movieDbDescription) {
        this.movieDbDescription = movieDbDescription;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "MovieModelDb{" +
                "movieDbTitle='" + movieDbTitle + '\'' +
                '}';
    }
}
