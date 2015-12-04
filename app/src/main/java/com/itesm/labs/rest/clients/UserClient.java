package com.itesm.labs.rest.clients;

import com.itesm.labs.rest.models.Admin;
import com.itesm.labs.rest.models.LoginAdmin;
import com.itesm.labs.rest.models.TokenWrapper;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.rest.services.UserService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 11/3/15.
 */
public class UserClient {

    private UserService mUserService;

    public UserClient(UserService mUserService) {
        this.mUserService = mUserService;
    }

    public Observable<String> loginAdmin(final LoginAdmin admin) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                TokenWrapper wrapper = mUserService.loginAdmin(admin);

                subscriber.onNext(wrapper.getToken());
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Admin> getAdmin(final String token, final String adminId) {
        return Observable.create(new Observable.OnSubscribe<Admin>() {
            @Override
            public void call(Subscriber<? super Admin> subscriber) {
                Admin admin = mUserService.getAdmin(token, adminId);

                subscriber.onNext(admin);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<User> getUser(final String token, final String userId) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                User user = mUserService.getUser(token, userId);

                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<ArrayList<User>> getUsers(final String token) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<User>>() {
            @Override
            public void call(Subscriber<? super ArrayList<User>> subscriber) {
                ArrayList<User> users = mUserService.getUsers(token);

                subscriber.onNext(users);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postUser(final String token, final User newUser) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mUserService.postNewUser(token, newUser);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> editUser(final String token, final String userId, final User newUserInfo) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mUserService.editUser(token, userId, newUserInfo);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteUser(final String token, final String userId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mUserService.deleteUser(token, userId);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
