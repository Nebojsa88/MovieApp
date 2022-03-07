package com.radanov.movieapp10.retrofit.utils;

import android.content.Context;

import com.radanov.movieapp10.retrofit.endpoints.ApiService;
import com.radanov.movieapp10.retrofit.factory.RetrofitClient;
import com.radanov.movieapp10.utils.Credentials;


/**
 * Created by Boris on 2/1/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

   /* public static ApiService getApiService(Context context) {
        return RetrofitClient.getClient(Credentials.BASE_URL).create(ApiService.class);
    }*/

    public static ApiService getApiService() {
        return RetrofitClient.getClient(Credentials.BASE_URL).create(ApiService.class);
    }


}
