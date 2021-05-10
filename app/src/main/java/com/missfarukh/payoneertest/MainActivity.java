package com.missfarukh.payoneertest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.missfarukh.payoneertest.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public CountingIdlingResource getIdlingResourceInTest() {
        return mainFragment.getmViewModel().getmIdlingRes();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}