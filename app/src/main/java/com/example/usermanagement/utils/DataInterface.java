package com.example.usermanagement.utils;

import com.example.usermanagement.adapter.FetchUserAdapter;
import com.example.usermanagement.response.DeleteAccountResponse;
import com.example.usermanagement.response.FetchUserResponse;
import com.example.usermanagement.response.LoginResponse;
import com.example.usermanagement.response.RegisterResponse;
import com.example.usermanagement.response.UpdatePasswordResponse;
import com.example.usermanagement.response.UpdateUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchUser();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<UpdateUserResponse> updateUser(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePasswordResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPass,
            @Field("new") String newPass
    );

    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteAccountResponse> deleteAccount(
            @Field("id") int id
    );
}
