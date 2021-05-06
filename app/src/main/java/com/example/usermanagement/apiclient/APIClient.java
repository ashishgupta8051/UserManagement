package com.example.usermanagement.apiclient;

import com.example.usermanagement.utils.Credentials;
import com.example.usermanagement.utils.DataInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static String BASE_URL = "http//192.168.43.252/UserApi/";
    private static APIClient apiClient;
    private static Retrofit retrofit;

    private APIClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized APIClient getApiClient(){
        if (apiClient == null){
            apiClient = new APIClient();
        }
        return apiClient;
    }

    public DataInterface getDataInterface(){
        return retrofit.create(DataInterface.class);
    }
}
