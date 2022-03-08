package com.radanov.movieapp10.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelDb;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.models.MovieResponse;
import com.radanov.movieapp10.response.MovieApiClient;
import com.radanov.movieapp10.retrofit.endpoints.ApiService;
import com.radanov.movieapp10.retrofit.utils.ApiUtils;
import com.radanov.movieapp10.roomdb.MovieDao;
import com.radanov.movieapp10.roomdb.MovieDaoOffline;
import com.radanov.movieapp10.roomdb.MovieDatabase;
import com.radanov.movieapp10.roomdb.MovieDatabaseOffline;
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

    private ApiService apiService;
    private MovieApiClient movieApiClient;

    private LiveData<List<MovieModelOffline>> moviesOffline;
    private MutableLiveData<List<MovieModelOffline>> moviesPopular;

    public MovieRepositoryOffline(Application application)
    {
        movieApiClient = MovieApiClient.getInstance();
        apiService = ApiUtils.getApiService();
        moviesPopular = new MutableLiveData<>();
        MovieDatabaseOffline database = MovieDatabaseOffline.getInstance(application);
        movieDaoOffline = database.movieDaoOffline();
        moviesOffline = movieDaoOffline.getAllOfflineMovies();
    }

    public LiveData<List<MovieModelOffline>> getPopularMovies() {
        apiService.getPopular(Credentials.API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(response.body().getResults() != null)
                        moviesPopular.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
            }
        });
        return moviesPopular;
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
