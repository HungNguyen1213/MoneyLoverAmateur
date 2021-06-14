package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.Exchange;
import com.example.moneyloveramateur.payload.ExchangePayload;
import com.example.moneyloveramateur.payload.ListExchangePayload;
import com.example.moneyloveramateur.payload.ListReportPayload;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExchangeService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/exchange/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ExchangeService exchangeService = retrofit.create(ExchangeService.class);

    @POST("create/{walletId}")
    Call<ExchangePayload> createExchange(@Body Exchange exchange, @Path("walletId") Long walletId);

    @GET("read/{walletId}")
    Call<ListExchangePayload> readExchangeByWalletAndTime(@Path("walletId") Long walletId, @Query("time") String time);

    @PUT("update")
    Call<ExchangePayload> updateExchange(@Body Exchange exchange);

    @DELETE("delete")
    Call<ExchangePayload> deleteExchange(@Query("id") Long exchangeId);
}
