package com.itesm.labs.application;

import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.models.User;

/**
 * Created by mgradob on 11/3/15.
 */
public class AppGlobals {

    private Admin admin;
    private Laboratory laboratory;
    private User user;
    private Cart cart;
    private String labLink;

    public AppGlobals() {
        admin = new Admin();
        laboratory = new Laboratory();
        cart = new Cart();
        labLink = "";
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getLabLink() {
        return labLink;
    }

    public void setLabLink(String labEndpoint) {
        this.labLink = labEndpoint;
    }

    @Override
    public String toString() {
        return "AppGlobals{" +
                "admin=" + admin +
                ", laboratory=" + laboratory +
                ", user=" + user +
                ", cart=" + cart +
                ", labLink='" + labLink + '\'' +
                '}';
    }
}
