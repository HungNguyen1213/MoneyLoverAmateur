package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.payload.ListReportPayload;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/report/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ReportService reportService = retrofit.create(ReportService.class);

    @GET("read/{walletId}")
    Call<ListReportPayload> readReportAllGroup(@Path("walletId") Long walletId, @Query("time") String time);
}
