package com.itesm.labs.rest.clients;

import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.services.ComponentService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/5/15.
 */
public class ComponentClient {

    private ComponentService mComponentService;

    public ComponentClient(ComponentService mComponentService) {
        this.mComponentService = mComponentService;
    }

    public Observable<ArrayList<Component>> getAllComponents(final String token, final String lab) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Component>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Component>> subscriber) {
                ArrayList<Component> components = mComponentService.getAllComponents(token, lab);

                subscriber.onNext(components);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<ArrayList<Component>> getComponents(final String token, final String lab, final int category) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Component>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Component>> subscriber) {
                ArrayList<Component> components = mComponentService.getComponents(token, lab, category);

                subscriber.onNext(components);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Component> getComponent(final String token, final String lab, final int componentId) {
        return Observable.create(new Observable.OnSubscribe<Component>() {
            @Override
            public void call(Subscriber<? super Component> subscriber) {
                Component component = mComponentService.getComponent(token, lab, componentId);

                subscriber.onNext(component);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postComponent(final String token, final String lab, final Component item) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mComponentService.postComponent(token, lab, item);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> putComponent(final String token, final String lab, final int componentId, final Component body) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mComponentService.putComponent(token, lab, componentId, body);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteComponent(final String token, final String lab, final int componentId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mComponentService.deleteComponent(token, lab, componentId);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
