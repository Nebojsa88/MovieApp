package com.radanov.movieapp10.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.roomdb.MovieDao;
import com.radanov.movieapp10.roomdb.MovieDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MovieRepositoryDb {

    private MovieDao movieDao;
    private LiveData<List<MovieModelDb>> movies;

    public MovieRepositoryDb(Application application)
    {
        MovieDatabase database = MovieDatabase.getInstance(application);
        movieDao = database.movieDao();
        movies = movieDao.getAllDbNotes();
    }

    public void insert(MovieModelDb movieModelDb)
    {
        new InsertNoteAsyncTask(movieDao).execute(movieModelDb);
    }

    public void update(MovieModelDb movieModelDb)
    {
        new UpdateNoteAsyncTask(movieDao).execute(movieModelDb);
    }

    public void delete(MovieModelDb movieModelDb)
    {
        new DeleteNoteAsyncTask(movieDao).execute(movieModelDb);
    }

    public LiveData<List<MovieModelDb>> getAllDbMovies()
    {
        return movies;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<MovieModelDb, Void, Void>
    {
        private MovieDao movieDao;

        private InsertNoteAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModelDb... movies) {
            movieDao.Insert(movies[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<MovieModelDb, Void, Void>
    {
        private MovieDao movieDao;

        private UpdateNoteAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModelDb... movies) {
            movieDao.Update(movies[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<MovieModelDb, Void, Void>
    {
        private MovieDao movieDao;

        private DeleteNoteAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModelDb... movies) {
            movieDao.Delete(movies[0]);
            return null;
        }
    }


}
