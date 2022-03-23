package com.radanov.movieapp10.io.repositories;

import android.app.Application;
import android.os.AsyncTask;


import com.radanov.movieapp10.io.models.MovieModel;
import com.radanov.movieapp10.io.models.MovieResponse;
import com.radanov.movieapp10.io.retrofit.endpoints.ApiService;
import com.radanov.movieapp10.io.retrofit.utils.ApiUtils;
import com.radanov.movieapp10.io.roomdb.MovieDao;
import com.radanov.movieapp10.io.roomdb.MovieDaoOffline;
import com.radanov.movieapp10.io.roomdb.MovieDatabase;
import com.radanov.movieapp10.io.roomdb.MovieDatabaseOffline;
import com.radanov.movieapp10.utils.Credentials;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositoryOffline {

    private MovieDaoOffline movieDaoOffline;
    private MovieDao movieDao;


    private ApiService apiService;

    private LiveData<List<MovieModel>> moviesOffline;
    private MutableLiveData<List<MovieModel>> moviesPopular;

    //test
    private LiveData<List<MovieModel>> moviesWatchlist;

    public MovieRepositoryOffline(Application application) {
        apiService = ApiUtils.getApiService();
        moviesPopular = new MutableLiveData<>();

        MovieDatabaseOffline database = MovieDatabaseOffline.getInstance(application);
        //test
        MovieDatabase databaseWatchlist = MovieDatabase.getInstance(application);
        movieDao = databaseWatchlist.movieDao();
        moviesWatchlist = movieDao.getAllDbNotes();

        movieDaoOffline = database.movieDaoOffline();
        moviesOffline = movieDaoOffline.getAllOfflineMovies();
    }

    public LiveData<List<MovieModel>> getPopularMovies() {
        apiService.getPopular(Credentials.API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResults() != null)
                        moviesPopular.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
            }
        });
        return moviesPopular;
    }

    public void insert(MovieModel movieModel) {
        new InsertNoteAsyncTask(movieDaoOffline).execute(movieModel);
    }

    public void update(MovieModel movieModel) {
        new UpdateNoteAsyncTask(movieDaoOffline).execute(movieModel);
    }

    public void delete(MovieModel movieModel) {
        new DeleteNoteAsyncTask(movieDaoOffline).execute(movieModel);
    }

    public void deleteAll() {
        new DeleteAllNoteAsyncTask(movieDaoOffline).execute();
    }

    public LiveData<List<MovieModel>> getAllOfflineMovies() {
        return moviesOffline;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private MovieDaoOffline movieDaoOffline;

        private InsertNoteAsyncTask(MovieDaoOffline movieDaoOffline) {
            this.movieDaoOffline = movieDaoOffline;
        }

        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDaoOffline.Insert(movies[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private MovieDaoOffline movieDaoOffline;

        private UpdateNoteAsyncTask(MovieDaoOffline movieDaoOffline) {
            this.movieDaoOffline = movieDaoOffline;
        }

        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDaoOffline.Update(movies[0]);

            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private MovieDaoOffline movieDaoOffline;

        private DeleteNoteAsyncTask(MovieDaoOffline movieDaoOffline) {
            this.movieDaoOffline = movieDaoOffline;
        }

        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDaoOffline.Delete(movies[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private MovieDaoOffline movieDaoOffline;

        private DeleteAllNoteAsyncTask(MovieDaoOffline movieDaoOffline) {
            this.movieDaoOffline = movieDaoOffline;
        }

        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDaoOffline.deleteAll();
            return null;
        }
    }

    public void insertWatchlist(MovieModel movieModel)
    {
        new MovieRepositoryOffline.InsertWatchlistAsyncTask(movieDao).execute(movieModel);
    }

    public void updateWatchlist(MovieModel movieModel)
    {
        new MovieRepositoryOffline.UpdateWatchlistAsyncTask(movieDao).execute(movieModel);
    }

    public void deleteWatchlist(MovieModel movieModel)
    {
        new MovieRepositoryOffline.DeleteWatchlistAsyncTask(movieDao).execute(movieModel);
    }

    public LiveData<List<MovieModel>> getMoviesWatchlist()
    {
        return moviesWatchlist;
    }


    private static class InsertWatchlistAsyncTask extends AsyncTask<MovieModel, Void, Void>
    {
        private MovieDao movieDao;

        private InsertWatchlistAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDao.Insert(movies[0]);
            return null;
        }
    }

    private static class UpdateWatchlistAsyncTask extends AsyncTask<MovieModel, Void, Void>
    {
        private MovieDao movieDao;

        private UpdateWatchlistAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDao.Update(movies[0]);
            return null;
        }
    }
    private static class DeleteWatchlistAsyncTask extends AsyncTask<MovieModel, Void, Void>
    {
        private MovieDao movieDao;

        private DeleteWatchlistAsyncTask(MovieDao movieDao)
        {
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieModel... movies) {
            movieDao.Delete(movies[0]);
            return null;
        }
    }


}
