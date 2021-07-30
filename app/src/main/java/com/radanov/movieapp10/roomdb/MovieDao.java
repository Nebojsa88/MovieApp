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
public interface MovieDao {


    @Insert()
    void Insert(MovieModelDb movieModelDb);

    @Update
    void Update(MovieModelDb movieModelDb);

    @Delete
    void Delete(MovieModelDb movieModelDb);

    @Query("SELECT * FROM movie_table ORDER BY id ASC")
    LiveData<List<MovieModelDb>> getAllDbNotes();



}
