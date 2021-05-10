package com.missfarukh.payoneertest.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.missfarukh.payoneertest.api.RetrofitAPI;
import com.missfarukh.payoneertest.model.ListResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    String TAG = MainViewModel.class.getSimpleName();
    public static String BASE_URL = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-test/";

    private MutableLiveData<ListResult> listResultMutableLiveData;
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>(false);
    private RetrofitAPI apiService;
    CountingIdlingResource mIdlingRes = new CountingIdlingResource("CountingIdlingResource");

    public CountingIdlingResource getmIdlingRes() {
        return mIdlingRes;
    }

    public MutableLiveData<ListResult> getListResultMutableLiveData() {
        return listResultMutableLiveData;
    }

    public MutableLiveData<Boolean> getShowProgress() {
        return showProgress;
    }

    public MainViewModel() {
        this.listResultMutableLiveData = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(RetrofitAPI.class);

        fetchListResult();
    }

    private void fetchListResult() {
        try {
            showProgress.setValue(true);
            mIdlingRes.increment();
            Call<ListResult> listResultCall = apiService.getListResult();
            listResultCall.enqueue(new Callback<ListResult>() {
                @Override
                public void onResponse(Call<ListResult> call, Response<ListResult> response) {
                    if (response.body() != null)
                        listResultMutableLiveData.setValue(response.body());
                    mIdlingRes.decrement(); //set Espresso not idle
                    showProgress.setValue(false);
                }

                @Override
                public void onFailure(Call<ListResult> call, Throwable t) {
                    t.printStackTrace();
                    mIdlingRes.decrement();
                    showProgress.setValue(false);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            mIdlingRes.decrement();
            showProgress.setValue(false);
        }

    }
}