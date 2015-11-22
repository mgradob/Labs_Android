package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.CategoryClient;
import com.itesm.labs.rest.models.Category;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddCategoryActivity extends LabsAppBaseActivity {

    private static String TAG = AddCategoryActivity.class.getSimpleName();

    @Bind(R.id.activity_add_category_title)
    TextView newCategoryTitleTextView;
    @Bind(R.id.activity_add_category_name)
    EditText newCategoryNameEditText;
    @Bind(R.id.activity_add_category_fab)
    FloatingActionButton addCategoryFab;
    @Bind(R.id.activity_add_category_delete)
    Button deleteCategoryButton;

    @Inject
    CategoryClient mCategoryClient;

    private Boolean isEditting;
    private String newCategoryName;
    private Integer newCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ButterKnife.bind(this);

        setupInfoWithIntent(getIntent());

        setupDeleteButton();
    }

    private void setupInfoWithIntent(Intent callingIntent) {
        newCategoryId = callingIntent.getIntExtra("INDEX", -1);
        isEditting = callingIntent.getBooleanExtra("ISEDIT", false);
        newCategoryName = callingIntent.getStringExtra("CATEGORYNAME");
        if (newCategoryName != null) {
            newCategoryNameEditText.setText(newCategoryName);
            newCategoryTitleTextView.setText("Editar:");
        }
    }

    private void setupDeleteButton() {
        if (isEditting) deleteCategoryButton.setVisibility(View.VISIBLE);
        else deleteCategoryButton.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.activity_add_category_fab)
    void onClickFab() {
        if (isEditting) doEdit();
        else doCreate();
    }

    @OnClick(R.id.activity_add_category_delete)
    void onClickDelete() {
        doDelete();
    }

    private void doDelete() {
        mCategoryClient.deleteCategory(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), newCategoryId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task post category started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task post category completed");

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_delete_ok), Snackbar.LENGTH_SHORT)
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
                        Log.d(TAG, "Task post category error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_delete_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    private void doEdit() {
        final Category newCategory = new Category();
        newCategory.setId(newCategoryId);
        newCategory.setName(newCategoryNameEditText.getText().toString());

        mCategoryClient.editCategory(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), newCategory.getId(), newCategory)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task post category started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task post category completed");

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_put_ok), Snackbar.LENGTH_SHORT)
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
                        Log.d(TAG, "Task post category error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_put_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    private void doCreate() {
        final Category newCategory = new Category();
        newCategory.setId(newCategoryId);
        newCategory.setName(newCategoryNameEditText.getText().toString());

        mCategoryClient.postNewCategory(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), newCategory)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task post category started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task post category completed");

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_post_ok), Snackbar.LENGTH_SHORT)
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
                        Log.d(TAG, "Task post category error: " + e.getMessage());

                        Snackbar.make(findViewById(R.id.activity_add_category), getString(R.string.add_category_post_error), Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
}
