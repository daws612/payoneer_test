package com.missfarukh.payoneertest.api;

import com.missfarukh.payoneertest.model.ListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    @GET("lists/listresult.json")
    Call<ListResult> getListResult();
}
