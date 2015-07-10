package com.itesm.labs.rest.queries;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.service.CartService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/11/15.
 */
public class CartQuery {

    private String ENDPOINT;

    public CartQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public ArrayList<CartItem> getCartOf(String userId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        CartService service = restAdapter.create(CartService.class);

        return service.getCartItemsOf(userId);
    }

    public void updateCartItem(CartItem cartItem) {
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

        CartService service = restAdapter.create(CartService.class);

        Callback<Response> callbackPut = new Callback<Response>() {
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

        service.editCartItem(cartItem.getCartId(), cartItem, callbackPut);
    }

    public void deleteCart(int cartId) {
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

        CartService service = restAdapter.create(CartService.class);

        Callback<Response> callbackDelete = new Callback<Response>() {
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

        service.deleteCartItem(cartId, callbackDelete);
    }
}
