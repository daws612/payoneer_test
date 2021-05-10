package com.missfarukh.payoneertest.api;

import com.missfarukh.payoneertest.model.ListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    public static String BASE_URL = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-test/";

    @GET("lists/listresult.json")
    Call<ListResult> getListResult();
}
