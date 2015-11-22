package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.CartItem;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgradob on 2/28/15.
 */
public interface CartService {

    @GET("/{lab}/detailcart/")
    ArrayList<CartItem> getCartsItems(@Header("Authorization") String token,
                                      @Path("lab") String lab);

    @GET("/{lab}/detailcart/")
    ArrayList<CartItem> getCartItemsOf(@Header("Authorization") String token,
                                       @Path("lab") String lab,
                                       @Query("id_student_fk") String userId);

    @PUT("/{lab}/detailcart/{id_cart}/")
    void editCartItem(@Header("Authorization") String token,
                      @Path("lab") String lab,
                      @Path("id_cart") int cartId,
                      @Body CartItem cartItemBody);

    @DELETE("/{lab}/detailcart/{id_cart}/")
    void deleteCartItem(@Header("Authorization") String token,
                        @Path("lab") String lab,
                        @Path("id_cart") int cartId);
}
