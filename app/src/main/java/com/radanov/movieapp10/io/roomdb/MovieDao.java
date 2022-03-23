package com.radanov.movieapp10.io.roomdb;



import com.radanov.movieapp10.io.models.MovieModel;

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
    void Insert(MovieModel movieModel);

    @Update
    void Update(MovieModel movieModel);

    @Delete
    void Delete(MovieModel movieModel);

    @Query("SELECT * FROM movie_offline ORDER BY id ASC")
    LiveData<List<MovieModel>> getAllDbNotes();



}
