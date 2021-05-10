package com.missfarukh.payoneertest.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.missfarukh.payoneertest.model.ApplicableNetwork;
import com.missfarukh.payoneertest.model.ListResult;
import com.missfarukh.payoneertest.model.Networks;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ListResult> listResultMutableLiveData;

    public MutableLiveData<ListResult> getListResultMutableLiveData() {
        return listResultMutableLiveData;
    }

    public MainViewModel() {
        this.listResultMutableLiveData = new MutableLiveData<>();
        fetchListResult();
    }

    private void fetchListResult() {
        ListResult listResult = new ListResult();
        listResult.setNetworks(new Networks());
        ApplicableNetwork network = new ApplicableNetwork();
        network.setLabel("Master");
        List<ApplicableNetwork> networkList = new ArrayList<>();
        networkList.add(network);
        network = new ApplicableNetwork();
        network.setLabel("Visal");
        networkList.add(network);
        listResult.getNetworks().setApplicable(networkList);
        listResultMutableLiveData.setValue(listResult);
    }
}