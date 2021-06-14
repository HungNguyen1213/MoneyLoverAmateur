package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.Wallet;
import com.example.moneyloveramateur.payload.ListWalletPayload;
import com.example.moneyloveramateur.payload.WalletPayload;

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

public interface WalletService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/wallet/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WalletService walletService = retrofit.create(WalletService.class);

    @GET("owner")
    Call<ListWalletPayload> findWalletByOwner(@Query("id") Long userId);

    @POST("create/{userId}")
    Call<WalletPayload> createWallet(@Body Wallet wallet, @Path("userId") Long userId);

    @GET("read")
    Call<WalletPayload> readWalletById(@Query("id") Long walletId);

    @PUT("update")
    Call<WalletPayload> updateWallet(@Body Wallet wallet);

    @DELETE("delete")
    Call<WalletPayload> deleteWallet(@Query("id") Long walletId);
}
