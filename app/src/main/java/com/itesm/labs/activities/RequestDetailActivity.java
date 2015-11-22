package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.R;
import com.itesm.labs.adapters.RequestItemModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.CartClient;
import com.itesm.labs.rest.clients.DetailHistoryClient;
import com.itesm.labs.rest.clients.UserClient;
import com.itesm.labs.rest.models.Cart;
import com.itesm.labs.rest.models.CartItem;
import com.itesm.labs.rest.models.User;
import com.itesm.labs.utils.NfcHandler;
import com.itesm.labs.utils.NfcHandler.UidCallback;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestDetailActivity extends LabsAppBaseActivity implements UidCallback {

    private final static String TAG = RequestDetailActivity.class.getSimpleName();

    public NfcHandler nfcHandler;

    @Bind(R.id.activity_request_detail_user_name)
    TextView userNameTextView;

    @Bind(R.id.activity_request_detail_user_id)
    TextView userIdTextView;

    @Bind(R.id.activity_request_detail_list)
    ListView userRequestList;

    @Bind(R.id.activity_request_detail_fab)
    FloatingActionButton mFab;

    @Inject
    CartClient mCartClient;

    @Inject
    UserClient mUserClient;

    @Inject
    DetailHistoryClient mHistoryClient;

    private boolean mRequestStatus;
    private String userName;
    private String userId;
    private long UID;

    private RequestItemModelAdapter mAdapter;

    private ArrayList<CartItem> mCartItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        ButterKnife.bind(this);

        setupFromIntent(getIntent());

        setupUser();

        setupStatusBar();

        setupFab();

        setupNfcReader();

        setupRequestList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableReader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableReader();

        getCartOfUser();
    }

    private void setupFromIntent(Intent callingIntent) {
        userName = callingIntent.getStringExtra("USERNAME");
        userId = callingIntent.getStringExtra("USERID");
        mRequestStatus = callingIntent.getBooleanExtra("REQUESTSTATUS", false);
    }

    private void setupUser() {
        userNameTextView.setText(userName);
        userIdTextView.setText(userId);
    }

    private void setupStatusBar() {
        getWindow().setStatusBarColor(getColor(R.color.primary_dark));
    }

    private void setupFab() {
        mFab.setImageResource(mRequestStatus ? R.drawable.ic_uid_white : R.drawable.ic_done_white);
    }

    private void setupNfcReader() {
        try {
            nfcHandler = new NfcHandler(this);
            enableReader();
        } catch (Exception ex) {
            Toast.makeText(this, "No NFC adapter available", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRequestList() {
        mAdapter = new RequestItemModelAdapter(mContext, mCartItemsList);
        userRequestList.setAdapter(mAdapter);
    }

    @OnClick(R.id.activity_request_detail_fab)
    void onClick() {
        if (mRequestStatus) validateRequest();
        else readyRequest();
    }

    /**
     * Get the user information.
     */
    private void getUserInfo() {
        mUserClient.getUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                userId)
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get user completed");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get user error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(User user) {
                        if (user == null)
                            throw new NullPointerException("User is null");

                        mAppGlobals.setUser(user);
                    }
                });
    }

    /**
     * Get the cart for the user.
     */
    private void getCartOfUser() {
        mCartClient.getCartOf(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mAppGlobals.getCart())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cart>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get cart completed");

                        mAdapter.refresh(mCartItemsList);

                        getUserInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get cart error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Cart cart) {
                        if (cart == null)
                            throw new NullPointerException("CartItems is null");

                        mCartItemsList = cart.getCartList();
                    }
                });
    }

    /**
     * Updates a cart to make an user cart ready.
     */
    private void readyRequest() {
        mCartClient.updateCart(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mAppGlobals.getCart())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task update cart started");

                        for (CartItem item : mAppGlobals.getCart().getCartList()) {
                            item.setReady(true);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task update cart completed");

                        Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_updated), Snackbar.LENGTH_SHORT)
                                .setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        super.onDismissed(snackbar, event);

                                        finish();
                                    }
                                }).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task update cart error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_update_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    /**
     * Validate an user cart with the UID of the user credential.
     */
    private void validateRequest() {

        if (UID == mAppGlobals.getUser().getUserUid()) updateHistory();
        else
            Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_invalid_id), Snackbar.LENGTH_SHORT)
                    .show();
    }

    /**
     * Create the history for the user.
     */
    private void updateHistory() {
        mHistoryClient.postNewCartHistory(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mAppGlobals.getCart())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task post history started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task post history completed");

                        deleteCart();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task post history error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_update_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    /**
     * Delete this cart.
     */
    private void deleteCart() {
        mCartClient.deleteCart(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mAppGlobals.getCart())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task delete cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task delete cart completed");

                        Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_verified), Snackbar.LENGTH_SHORT)
                                .setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        super.onDismissed(snackbar, event);
                                        finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task delete cart error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_request_detail), getString(R.string.requests_update_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    private void enableReader() {
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.enableReaderMode(activity, nfcHandler, NfcAdapter.FLAG_READER_NFC_A, null);
    }

    private void disableReader() {
        Activity activity = this;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdapter != null)
            nfcAdapter.disableReaderMode(activity);
    }

    @Override
    public void getUid(long uid) {
        this.UID = uid;
    }
}
