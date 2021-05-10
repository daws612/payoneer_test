package com.missfarukh.payoneertest.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.missfarukh.payoneertest.api.RetrofitAPI;
import com.missfarukh.payoneertest.model.ListResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ListResult> listResultMutableLiveData;
    private RetrofitAPI apiService;

    public MutableLiveData<ListResult> getListResultMutableLiveData() {
        return listResultMutableLiveData;
    }

    public MainViewModel() {
        this.listResultMutableLiveData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(RetrofitAPI.class);

        fetchListResult();
    }

    private void fetchListResult() {
        Call<ListResult> listResultCall = apiService.getListResult();
        listResultCall.enqueue(new Callback<ListResult>() {
            @Override
            public void onResponse(Call<ListResult> call, Response<ListResult> response) {
                if (response.body() != null)
                    listResultMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ListResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}