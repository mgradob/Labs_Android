package com.itesm.labs.rest.clients;

import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.History;
import com.itesm.labs.rest.services.CategoryService;
import com.itesm.labs.rest.services.ComponentService;
import com.itesm.labs.rest.services.DetailHistoryService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/5/15.
 */
public class DetailHistoryClient {

    private DetailHistoryService mDetailHistoryService;
    private CategoryService mCategoryService;
    private ComponentService mComponentService;

    public DetailHistoryClient(DetailHistoryService mDetailHistoryService, CategoryService mCategoryService, ComponentService mComponentService) {
        this.mDetailHistoryService = mDetailHistoryService;
        this.mCategoryService = mCategoryService;
        this.mComponentService = mComponentService;
    }

    public Observable<ArrayList<History>> getDetailHistoryOf(final String token, final String lab, final String userId) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<History>>() {
            @Override
            public void call(Subscriber<? super ArrayList<History>> subscriber) {
                ArrayList<History> histories = mDetailHistoryService.getDetailHistoryOf(token, lab, userId);

                for (History history : histories) {
                    Component component = mComponentService.getComponent(token, lab, history.getComponentId());
                    Category category = mCategoryService.getCategory(token, lab, component.getCategory());

                    history.setCategoryName(category.getName());
                    history.setComponentNameNote(component.getName() + component.getNote());
                }

                subscriber.onNext(histories);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postNewCartHistory(final String token, final String lab, final Cart cart) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (CartItem item : cart.getCartList()) {
                    History history = new History();
                    history.setStudentId(cart.getUserId());
                    history.setComponentId(item.getComponentId());
                    history.setQuantity(item.getQuantity());

                    mDetailHistoryService.postDetailHistoryItem(token, lab, history);
                }

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postDetailHistoryItem(final String token, final String lab, final History item) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mDetailHistoryService.postDetailHistoryItem(token, lab, item);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> putDetailHistoryItem(final String token, final String lab, final int historyId, final History item) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mDetailHistoryService.putDetailHistoryItem(token, lab, historyId, item);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
