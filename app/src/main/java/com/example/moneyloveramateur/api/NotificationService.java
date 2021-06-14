package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.Notification;
import com.example.moneyloveramateur.payload.NotificationPayload;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/notification/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    NotificationService notificationService = retrofit.create(NotificationService.class);

    @POST("create/{userId}")
    Call<NotificationPayload> createNotification(@Body Notification notification, @Path("userId") Long userId);

    @GET("read")
    Call<NotificationPayload> readAllNotificationByUser(@Query("userId") Long userId);

    @DELETE("delete")
    Call<NotificationPayload> deleteAllNotificationByUser(@Query("userId") Long userId);
}
