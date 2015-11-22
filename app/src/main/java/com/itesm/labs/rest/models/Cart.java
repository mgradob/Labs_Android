package com.itesm.labs.rest.models;

import java.util.ArrayList;

/**
 * Created by mgradob on 4/6/15.
 */
public class Cart {

    private String userName;
    private String userId;
    private String cartDate;
    private ArrayList<CartItem> cartList;
    private boolean isReady;

    public Cart() {
    }

    public Cart(String userName, String userId, String cartDate, ArrayList<CartItem> cartList, boolean isReady) {
        this.userName = userName;
        this.userId = userId;
        this.cartDate = cartDate;
        this.cartList = cartList;
        this.isReady = isReady;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCartDate() {
        return cartDate;
    }

    public void setCartDate(String cartDate) {
        this.cartDate = cartDate;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<CartItem> cartList) {
        this.cartList = cartList;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", cartDate='" + cartDate + '\'' +
                ", cartList=" + cartList +
                ", isReady=" + isReady +
                '}';
    }
}
