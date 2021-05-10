package com.missfarukh.payoneertest.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.missfarukh.payoneertest.R;
import com.missfarukh.payoneertest.adapter.PaymentOptionsAdapter;
import com.missfarukh.payoneertest.model.ListResult;

public class MainFragment extends Fragment {
    String TAG = MainFragment.class.getSimpleName();

    private MainViewModel mViewModel;
    RecyclerView paymentOptionsRecyclerView;
    PaymentOptionsAdapter paymentOptionsAdapter;

    public MainViewModel getmViewModel() {
        return mViewModel;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        paymentOptionsRecyclerView = view.findViewById(R.id.payment_options_recyclerview);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getListResultMutableLiveData().observe(getViewLifecycleOwner(), listResultObserver);

        paymentOptionsAdapter = new PaymentOptionsAdapter(requireActivity());
        paymentOptionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        paymentOptionsRecyclerView.setAdapter(paymentOptionsAdapter);
    }


    Observer<ListResult> listResultObserver = new Observer<ListResult>() {
        @Override
        public void onChanged(ListResult listResult) {
            try {
                paymentOptionsAdapter.setApplicableNetworkList(listResult.getNetworks().getApplicable());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    };

}