package com.itesm.labs.rest.queries;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.service.CartService;
import com.itesm.labs.rest.service.ComponentService;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/11/15.
 */
public class ComponentQuery {

    private String ENDPOINT;

    public ComponentQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public Component getComponent(int componentId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        ComponentService service = restAdapter.create(ComponentService.class);

        Component component = service.getComponent(componentId);

        return component;
    }

    public void newComponent(Component item){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        ComponentService service = restAdapter.create(ComponentService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                Log.d("UPDATE CART:", "OK POST");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("UPDATE CART:", "ERROR POST");
                Log.e("UPDATE CART:", error.getMessage());
            }
        };

        service.postComponent(item, callbackPost);
    }

    public void updateComponent(Component item){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        ComponentService service = restAdapter.create(ComponentService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                Log.d("UPDATE CART:", "OK PUT");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("UPDATE CART:", "ERROR PUT");
                Log.e("UPDATE CART:", error.getMessage());
            }
        };

        service.putComponent(item.getId(), item, callbackPost);
    }

    public void deleteComponent(Component item){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        ComponentService service = restAdapter.create(ComponentService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                Log.d("UPDATE CART:", "OK DELETE");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("UPDATE CART:", "ERROR DELETE");
                Log.e("UPDATE CART:", error.getMessage());
            }
        };

        service.deleteComponent(item.getId(), callbackPost);
    }
}
