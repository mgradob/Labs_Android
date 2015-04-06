package com.itesm.labs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;
import com.itesm.labs.R;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.service.CategoryService;
import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddCategoryActivity extends ActionBarActivity {

    private String ENDPOINT;
    private Activity mActivity;
    private TextView newCategoryTitleTextView;
    private MaterialEditText newCategoryNameEditText;
    private Boolean isEditting;
    private String newCategoryName;
    private Integer newCategoryId;
    private RecyclerView newCategoryIconList;
    private FloatingActionButton addCategoryFab;
    private SnackBar.OnMessageClickListener postSnackbarClickListener, putSnackbarClickListener, deleteSnackbarClickListener;
    private Button deleteCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mActivity = this;

        Intent callingIntent = getIntent();
        ENDPOINT = callingIntent.getStringExtra("ENDPOINT");
        newCategoryId = callingIntent.getIntExtra("INDEX", -1);
        isEditting = callingIntent.getBooleanExtra("ISEDIT", false);

        newCategoryNameEditText = (MaterialEditText) findViewById(R.id.activity_add_category_name);
        deleteCategoryButton = (Button) findViewById(R.id.activity_add_category_delete);
        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDelete();
            }
        });
        if (!isEditting.equals("ISEDIT")) deleteCategoryButton.setVisibility(View.INVISIBLE);
        newCategoryTitleTextView = (TextView) findViewById(R.id.activity_add_category_title);
        addCategoryFab = (FloatingActionButton) findViewById(R.id.activity_add_category_fab);
        addCategoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditting)
                    doEdit();
                else
                    doCreate();
            }
        });

        newCategoryName = callingIntent.getStringExtra("CATEGORYNAME");
        if (newCategoryName != null) {
            newCategoryNameEditText.setText(newCategoryName);
            newCategoryTitleTextView.setText("Editar");
        }

        postSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doCreate();
            }
        };
        putSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doEdit();
            }
        };
        deleteSnackbarClickListener = new SnackBar.OnMessageClickListener() {
            @Override
            public void onMessageClick(Parcelable parcelable) {
                doDelete();
            }
        };
    }

    private void doDelete() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Callback<Response> callbackDelete = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                new SnackBar.Builder(mActivity)
                        .withMessage(newCategoryName + " fué eliminada.")
                        .withTextColorId(R.color.primary_text_light)
                        .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                            @Override
                            public void onShow(int i) {

                            }

                            @Override
                            public void onHide(int i) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void failure(RetrofitError error) {
                new SnackBar.Builder(mActivity)
                        .withMessage("Error al eliminar " + newCategoryName)
                        .withTextColorId(R.color.primary_text_light)
                        .withActionMessage("Retry")
                        .withOnClickListener(deleteSnackbarClickListener)
                        .withStyle(SnackBar.Style.ALERT)
                        .show();
            }
        };

        service.deleteCategory(newCategoryId.toString(), callbackDelete);
    }

    private void doEdit() {
        final Category newCategory = new Category();
        newCategory.setId(newCategoryId);
        newCategory.setName(newCategoryNameEditText.getText().toString());

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Callback<Response> callbackPut = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                new SnackBar.Builder(mActivity)
                        .withMessage(newCategory.getName() + " fué editada.")
                        .withTextColorId(R.color.primary_text_light)
                        .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                            @Override
                            public void onShow(int i) {

                            }

                            @Override
                            public void onHide(int i) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void failure(RetrofitError error) {
                new SnackBar.Builder(mActivity)
                        .withMessage("Error al editar " + newCategory.getName())
                        .withTextColorId(R.color.primary_text_light)
                        .withActionMessage("Retry")
                        .withOnClickListener(putSnackbarClickListener)
                        .withStyle(SnackBar.Style.ALERT)
                        .show();
            }
        };

        service.editCategory(newCategoryId.toString(), newCategory, callbackPut);
    }

    private void doCreate() {
        final Category newCategory = new Category();
        newCategory.setId(newCategoryId);
        newCategory.setName(newCategoryNameEditText.getText().toString());

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString("dsp-server:6842ldCC$".getBytes(), Base64.NO_WRAP));
            }
        };

        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(requestInterceptor)
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Callback<Response> callbackPost = new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {
                new SnackBar.Builder(mActivity)
                        .withMessage(newCategory.getName() + " fué agregada.")
                        .withTextColorId(R.color.primary_text_light)
                        .withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener() {
                            @Override
                            public void onShow(int i) {

                            }

                            @Override
                            public void onHide(int i) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void failure(RetrofitError error) {
                new SnackBar.Builder(mActivity)
                        .withMessage("Error al agregar " + newCategory.getName())
                        .withTextColorId(R.color.primary_text_light)
                        .withActionMessage("Retry")
                        .withOnClickListener(postSnackbarClickListener)
                        .withStyle(SnackBar.Style.ALERT)
                        .show();
            }
        };

        service.postNewCategory(newCategory, callbackPost);
    }
}
