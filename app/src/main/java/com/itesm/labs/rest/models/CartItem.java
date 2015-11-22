package com.itesm.labs.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 4/6/15.
 */
public class CartItem {

    @SerializedName("id_cart")
    private int cartId;
    @SerializedName("id_student_fk")
    private String studentId;
    @SerializedName("id_component_fk")
    private int componentId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("checkout")
    private boolean checkout;
    @SerializedName("ready")
    private boolean ready;
    @SerializedName("date_checkout")
    private String dateCheckout;
    private Category category;
    private Component component;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCheckout() {
        return checkout;
    }

    public void setCheckout(boolean checkout) {
        this.checkout = checkout;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getDateCheckout() {
        return dateCheckout;
    }

    public void setDateCheckout(String dateCheckout) {
        this.dateCheckout = dateCheckout;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", studentId='" + studentId + '\'' +
                ", componentId=" + componentId +
                ", quantity=" + quantity +
                ", checkout=" + checkout +
                ", ready=" + ready +
                ", dateCheckout='" + dateCheckout + '\'' +
                ", category=" + category +
                ", component=" + component +
                '}';
    }
}
