package com.radanov.movieapp10.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.roomdb.MovieDao;
import com.radanov.movieapp10.roomdb.MovieDaoOffline;
import com.radanov.movieapp10.roomdb.MovieDatabase;
import com.radanov.movieapp10.roomdb.MovieDatabaseOffline;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MovieRepositoryOffline {

    private MovieDaoOffline movieDaoOffline;
    private LiveData<List<MovieModelOffline>> moviesOffline;

    public MovieRepositoryOffline(Application application)
    {
        MovieDatabaseOffline database = MovieDatabaseOffline.getInstance(application);
        movieDaoOffline = database.movieDaoOffline();
        moviesOffline = movieDaoOffline.getAllOfflineMovies();
    }

    public void insert(MovieModelOffline movieModelOffline)
    {
        new InsertNoteAsyncTask(movieDaoOffline).execute(movieModelOffline);
    }

    public void update(MovieModelOffline movieModelOffline)
    {
        new UpdateNoteAsyncTask(movieDaoOffline).execute(movieModelOffline);
    }

    public void delete(MovieModelOffline movieModelOffline)
    {
        new DeleteNoteAsyncTask(movieDaoOffline).execute(movieModelOffline);
    }

    public void deleteAll()
    {
        new DeleteAllNoteAsyncTask(movieDaoOffline).execute();
    }

    public LiveData<List<MovieModelOffline>> getAllOfflineMovies()
    {
        return moviesOffline;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<MovieModelOffline, Void, Void>
    {
        private MovieDaoOffline movieDaoOffline;

        private InsertNoteAsyncTask(MovieDaoOffline movieDaoOffline)
        {
            this.movieDaoOffline = movieDaoOffline;
        }
        @Override
        protected Void doInBackground(MovieModelOffline... movies) {
            movieDaoOffline.Insert(movies[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<MovieModelOffline, Void, Void>
    {
        private MovieDaoOffline movieDaoOffline;

        private UpdateNoteAsyncTask(MovieDaoOffline movieDaoOffline)
        {
            this.movieDaoOffline = movieDaoOffline;
        }
        @Override
        protected Void doInBackground(MovieModelOffline... movies) {
            movieDaoOffline.Update(movies[0]);

            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<MovieModelOffline, Void, Void>
    {
        private MovieDaoOffline movieDaoOffline;

        private DeleteNoteAsyncTask(MovieDaoOffline movieDaoOffline)
        {
            this.movieDaoOffline = movieDaoOffline;
        }
        @Override
        protected Void doInBackground(MovieModelOffline... movies) {
            movieDaoOffline.Delete(movies[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<MovieModelOffline, Void, Void>
    {
        private MovieDaoOffline movieDaoOffline;

        private DeleteAllNoteAsyncTask(MovieDaoOffline movieDaoOffline)
        {
            this.movieDaoOffline = movieDaoOffline;
        }
        @Override
        protected Void doInBackground(MovieModelOffline... movies) {
            movieDaoOffline.deleteAll();
            return null;
        }
    }


}
