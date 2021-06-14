package com.example.moneyloveramateur.api;

import com.example.moneyloveramateur.model.Budget;
import com.example.moneyloveramateur.payload.BudgetPayload;
import com.example.moneyloveramateur.payload.ReportBudgetPayload;

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

public interface BudgetService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/budget/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    BudgetService  budgetService = retrofit.create(BudgetService.class);

    @POST("create/{walletId}")
    Call<BudgetPayload> createBudget(@Body Budget budget, @Path("walletId") Long walletId);

    @GET("read")
    Call<ReportBudgetPayload> readBudgetByWalletAndTime(@Query("id") Long walletId, @Query("time") String time);

    @PUT("update")
    Call<BudgetPayload> updateBudget(@Body Budget budget);

    @DELETE("delete")
    Call<BudgetPayload> deleteBudget(@Query("id") Long budgetId);
}
