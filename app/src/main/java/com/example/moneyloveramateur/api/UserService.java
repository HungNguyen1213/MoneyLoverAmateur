package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.User;
import com.example.moneyloveramateur.payload.UserPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserService userService = retrofit.create(UserService.class);

    @GET("login")
    Call<UserPayload> checkLogin(@Query("username") String username,
                                 @Query("password") String password);

    @POST("register")
    Call<UserPayload> register(@Body User user);

    @PUT("update")
    Call<UserPayload> updateUser(@Body User user);
}
