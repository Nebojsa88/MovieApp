package com.radanov.movieapp10.roomdb;


import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.models.MovieModelOffline;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDaoOffline {

    @Insert()
    void Insert(MovieModelOffline movieModelOffline);

    @Update
    void Update(MovieModelOffline movieModelOffline);

    @Delete
    void Delete(MovieModelOffline movieModelOffline);

    @Query("DELETE  FROM movie_offline")
    void deleteAll();


    @Query("SELECT * FROM movie_offline ORDER BY id ASC")
    LiveData<List<MovieModelOffline>> getAllOfflineMovies();

}
