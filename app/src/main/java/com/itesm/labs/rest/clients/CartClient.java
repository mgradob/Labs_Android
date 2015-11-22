package com.itesm.labs.rest.clients;

import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.services.CartService;
import com.itesm.labs.rest.services.CategoryService;
import com.itesm.labs.rest.services.ComponentService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/4/15.
 */
public class CartClient {

    private CartService mCartService;
    private CategoryService mCategoryService;
    private ComponentService mComponentService;

    public CartClient(CartService mCartService, CategoryService mCategoryService, ComponentService mComponentService) {
        this.mCartService = mCartService;
        this.mCategoryService = mCategoryService;
        this.mComponentService = mComponentService;
    }

    public Observable<ArrayList<CartItem>> getCarts(final String token, final String lab) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<CartItem>>() {
            @Override
            public void call(Subscriber<? super ArrayList<CartItem>> subscriber) {
                ArrayList<CartItem> cartItems = mCartService.getCartsItems(token, lab);

                subscriber.onNext(cartItems);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Cart> getCartOf(final String token, final String lab, final Cart cart) {
        return Observable.create(new Observable.OnSubscribe<Cart>() {
            @Override
            public void call(Subscriber<? super Cart> subscriber) {
                for (CartItem cartItem : cart.getCartList()) {
                    cartItem.setComponent(mComponentService.getComponent(token, lab, cartItem.getComponentId()));
                    cartItem.setCategory(mCategoryService.getCategory(token, lab, cartItem.getComponent().getCategory()));
                }

                subscriber.onNext(cart);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> updateCart(final String token, final String lab, final Cart cart) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (CartItem item : cart.getCartList()) {
                    mCartService.editCartItem(token, lab, item.getCartId(), item);
                }

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> editCartItem(final String token, final String lab, final int cardId, final CartItem body) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCartService.editCartItem(token, lab, cardId, body);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteCart(final String token, final String lab, final Cart cart) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (CartItem item : cart.getCartList()) {
                    mCartService.deleteCartItem(token, lab, item.getCartId());
                }

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteCartItem(final String token, final String lab, final int cartId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCartService.deleteCartItem(token, lab, cartId);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
