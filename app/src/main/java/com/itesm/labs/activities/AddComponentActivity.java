package com.itesm.labs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.R;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.CategoryClient;
import com.itesm.labs.rest.models.Category;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddComponentActivity extends LabsAppBaseActivity {

    private final static String TAG = AddComponentActivity.class.getSimpleName();

    @Bind(R.id.activity_add_category_name)
    EditText mComponentName;
    @Bind(R.id.activity_add_component_note)
    EditText mComponentNote;
    @Bind(R.id.activity_add_component_total)
    EditText mComponentTotal;
    @Bind(R.id.activity_add_component_av)
    EditText mComponentAv;
    @Bind(R.id.activity_add_component_category)
    TextView mComponentCategory;
    @Bind(R.id.activity_add_component_action)
    TextView mComponentAction;
    @Bind(R.id.activity_add_component_id)
    TextView mComponentId;

    @Inject
    CategoryClient mCategoryClient;

    private Boolean isEditting;
    private int newComponentId;
    private int newComponentCatId;
    private String mCategoryName;

    private ArrayList<Category> mCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_component);

        ButterKnife.bind(this);

        Intent callingIntent = getIntent();
        newComponentId = callingIntent.getIntExtra("INDEX", -1);
        isEditting = callingIntent.getBooleanExtra("ISEDIT", false);
        mCategoryName = callingIntent.getStringExtra("COMPONENTCATEGORY");

        mComponentId.setText("ID: " + newComponentId);
        mComponentCategory.setText("Categoría: " + mCategoryName);

        if (isEditting) {
            mComponentAction.setText("Editar:");
            mComponentName.setText(callingIntent.getStringExtra("COMPONENTNAME"));
            mComponentNote.setText(callingIntent.getStringExtra("COMPONENTNOTE"));
            mComponentTotal.setText("" + callingIntent.getIntExtra("COMPONENTTOTAL", 0));
            mComponentAv.setText("" + callingIntent.getIntExtra("COMPONENTAV", 0));
        }

        getCategories();
    }

    @OnClick(R.id.activity_add_component_category)
    void OnClick() {
        getCategories();
    }

    private void getCategories() {
        mCategoryClient.getCategories(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Category>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get categories started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get categories completed");

                        new MaterialDialog.Builder(getApplicationContext())
                                .title("Categorias")
                                .items((CharSequence[]) mCategoryList.toArray())
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                        Log.d("SELECTION", "" + mCategoryList.get(i).getId());
                                        newComponentCatId = mCategoryList.get(i).getId();
                                        mComponentCategory.setText(mCategoryList.get(i).getName());
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get categories error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ArrayList<Category> categories) {
                        if (categories.size() == 0)
                            throw new ArrayIndexOutOfBoundsException("Categories is empty");
                        else if (categories == null)
                            throw new NullPointerException("Categories is null");

                        mCategoryList = categories;
                    }
                });
    }

    // TODO: 11/15/15 post put delete for component.
}
