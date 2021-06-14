package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.Group;
import com.example.moneyloveramateur.payload.ListGroupPayload;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface GroupService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/group/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GroupService groupService = retrofit.create(GroupService.class);

    @GET("read")
    Call<ListGroupPayload> readAllGroup();


}
