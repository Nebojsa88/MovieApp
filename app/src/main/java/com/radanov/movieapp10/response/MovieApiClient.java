package com.radanov.movieapp10.response;

import android.util.Log;

import com.radanov.movieapp10.AppExecutors;
import com.radanov.movieapp10.models.MovieModel;
import com.radanov.movieapp10.models.MovieModelOffline;
import com.radanov.movieapp10.models.MovieResponse;
import com.radanov.movieapp10.retrofit.utils.ApiUtils;
import com.radanov.movieapp10.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LiveData for search
    private MutableLiveData<List<MovieModel>> mMovies;


    private static MovieApiClient instance;

    //Making Global RUNNABLE request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //Making Global POPULAR RUNNABLE request
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;

    //LiveData for popular
    private MutableLiveData<List<MovieModel>> mMoviesPop;



    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }

        return instance;
    }
    //Constructor
    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();


    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    //1-This method will that we are going to call throught classes
    public void searchMoviesApi(String query, int pageNumber){

        if(retrieveMoviesRunnable != null ){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().getNetworkIO().submit(retrieveMoviesRunnable);


        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling retrofit code
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }


    public void searchMoviesPop(int pageNumber){

        if(retrieveMoviesRunnablePop != null ){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().getNetworkIO().submit(retrieveMoviesRunnablePop);


        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling retrofit code
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }





    //Class
    //Retreiving data from RestAPI by runnable class
    //We have 2 types of queries
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {

            //Getting response object
            try{
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                    if(pageNumber == 1) {
                        //Sending data to LiveData
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMovies.postValue(list);



                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);


                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "ERROR "+ error);
                    mMovies.postValue(null);

                }


            } catch (IOException e) {
                mMovies.postValue(null);
                e.printStackTrace();
            }



        }

        //Search Method query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){

            return ApiUtils.getApiService().searchMovie(Credentials.API_KEY, query, pageNumber);

        }

        public void cancelRequest(){
            Log.v("Tag", "Cancelling search request ");
            cancelRequest = true;

        }
    }

    private class RetrieveMoviesRunnablePop implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {

            //Getting response object
            try{
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    List<MovieModelOffline> offlineList = new ArrayList(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber == 1) {
                        //Sending data to LiveData
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMoviesPop.postValue(list);

                    }else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }

                }else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "ERROR "+ error);
                    mMoviesPop.postValue(null);

                }
            } catch (IOException e) {
                mMoviesPop.postValue(null);
                e.printStackTrace();
            }
        }

        //Search Method query
        private Call<MovieResponse> getPop(int pageNumber){

            return ApiUtils.getApiService().getPopular(Credentials.API_KEY, pageNumber);

        }

        public void cancelRequest(){
            Log.v("Tag", "Cancelling search request ");
            cancelRequest = true;

        }

    }
}








