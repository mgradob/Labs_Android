package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.CartItem;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgradob on 2/28/15.
 */
public interface CartService {

    @GET("/detailcart/")
    ArrayList<CartItem> getCartsItems();

    @GET("/detailcart/")
    ArrayList<CartItem> getCartItemsOf(@Query("id_student_fk") String userId);

    @PUT("/detailcart/{id_cart}/")
    void editCartItem(@Path("id_cart") int cartId, @Body CartItem cartItemBody, Callback<Response> cb);

    @DELETE("/detailcart/{id_cart}/")
    void deleteCartItem(@Path("id_cart") int cartId, Callback<Response> cb);
}
