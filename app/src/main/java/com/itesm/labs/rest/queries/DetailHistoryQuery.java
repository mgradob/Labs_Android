package com.itesm.labs.rest.queries;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.History;
import com.itesm.labs.rest.service.DetailHistoryService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/12/15.
 */
public class DetailHistoryQuery {

    private String ENDPOINT;

    public DetailHistoryQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public ArrayList<History> getDetailHistoryOf(String userId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        DetailHistoryService service = restAdapter.create(DetailHistoryService.class);

        return service.getDetailHistoryOf(userId);
    }

    public void postDetailHistory(History item) {
        Gson gson = new GsonBuilder()
                .create();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();

        DetailHistoryService service = restAdapter.create(DetailHistoryService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                Log.d("UPDATE HISTORY:", "OK POST");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("UPDATE HISTORY:", "ERROR POST");
                Log.e("UPDATE HISTORY:", error.getMessage());
            }
        };

        service.postDetailHistoryItem(item, callbackPost);
    }

    public void putDetailHistory(History item) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        DetailHistoryService service = restAdapter.create(DetailHistoryService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                Log.d("UPDATE HISTORY:", "OK PUT");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("UPDATE HISTORY:", "ERROR PUT");
                Log.e("UPDATE HISTORY:", error.getMessage());
            }
        };

        service.putDetailHistoryItem(item.getHistoryId(), item, callbackPost);
    }
}
