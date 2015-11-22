package com.itesm.labs.rest.clients;

import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.services.LaboratoryService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/4/15.
 */
public class LaboratoryClient {

    private LaboratoryService mLaboratoryService;

    public LaboratoryClient(LaboratoryService mLaboratoryService) {
        this.mLaboratoryService = mLaboratoryService;
    }

    public Observable<Laboratory> getLaboratory(final String token, final String link) {
        return Observable.create(new Observable.OnSubscribe<Laboratory>() {
            @Override
            public void call(Subscriber<? super Laboratory> subscriber) {
                Laboratory laboratory = mLaboratoryService.getLaboratory(token, link);

                subscriber.onNext(laboratory);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<ArrayList<Laboratory>> getLaboratories(final String token) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Laboratory>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Laboratory>> subscriber) {
                ArrayList<Laboratory> laboratories = mLaboratoryService.getLaboratories(token);

                subscriber.onNext(laboratories);
                subscriber.onCompleted();
            }
        });
    }
}
