package com.missfarukh.payoneertest.ui.main;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.gson.Gson;
import com.missfarukh.payoneertest.model.ApplicableNetwork;
import com.missfarukh.payoneertest.model.ListResult;
import com.missfarukh.payoneertest.model.Networks;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MockWebServer mockWebServer;

    Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            switch (request.getPath()) {
                case "/lists/listresult.json":
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
                    return new MockResponse().setResponseCode(200).setBody(new Gson().toJson(listResult));
            }
            return new MockResponse().setResponseCode(404);
        }
    };

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();
        MainViewModel.BASE_URL = mockWebServer.url("/").toString();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getListResultMutableLiveData() throws InterruptedException {
        MainViewModel mainViewModel = new MainViewModel();

        Thread.sleep(200);
        assertNotNull(mainViewModel.getListResultMutableLiveData().getValue());
        assertTrue(mainViewModel.getListResultMutableLiveData().getValue() instanceof ListResult);
        assertEquals(2, mainViewModel.getListResultMutableLiveData().getValue().getNetworks().getApplicable().size());
        assertEquals("Master", mainViewModel.getListResultMutableLiveData().getValue().getNetworks().getApplicable().get(0).getLabel());
        assertEquals("Visal", mainViewModel.getListResultMutableLiveData().getValue().getNetworks().getApplicable().get(1).getLabel());
    }
}