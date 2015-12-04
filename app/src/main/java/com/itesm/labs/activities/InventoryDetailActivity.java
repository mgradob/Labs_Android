package com.itesm.labs.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.application.AppConstants;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.rest.clients.ComponentClient;
import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class InventoryDetailActivity extends LabsAppBaseActivity {

    private final static String TAG = InventoryDetailActivity.class.getSimpleName();
    private final static int ADD_COMPONENT_REQUEST = 1;

    @Bind(R.id.activity_inventory_detail_image)
    ImageView mDetailImage;
    @Bind(R.id.activity_inventory_detail_name)
    TextView mDetailName;
    @Bind(R.id.activity_inventory_detail_background)
    LinearLayout mDetailBackground;
    @Bind(R.id.activity_inventory_detail_list)
    ListView mDetailList;
    @Bind(R.id.activity_inventory_detail_fab)
    FloatingActionButton mDetailFab;

    @Inject
    ComponentClient mComponentClient;

    private String mCategoryName;
    private int mCategoryId;

    private ComponentModelAdapter mAdapter;

    private ArrayList<Component> mComponentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        ButterKnife.bind(this);

        setupWithIntent(getIntent());

        setupDetailList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getComponents();
    }

    private void setupWithIntent(Intent callingIntent) {
        mCategoryId = callingIntent.getIntExtra("CATEGORYID", 0);

        mDetailImage.setImageDrawable(getDrawable(callingIntent.getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category)));

        mCategoryName = callingIntent.getStringExtra("CATEGORYTITLE");
        mDetailName.setText(mCategoryName);

        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), callingIntent.getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category));
        Palette palette = Palette.from(iconBitmap).generate();

        getWindow().setStatusBarColor(palette.getDarkVibrantColor(getResources().getColor(R.color.primary)));

        mDetailBackground.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));

        mDetailFab.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.accent)));
    }

    private void setupDetailList() {
        mAdapter = new ComponentModelAdapter(mContext);
        mDetailList.setAdapter(mAdapter);
    }

    @OnClick(R.id.activity_inventory_detail_fab)
    void onFabClick() {
        Intent intent = new Intent(mContext, AddComponentActivity.class);
        intent.putExtra("INDEX", mComponentsList.get(mComponentsList.size() - 1).getId() + 1);
        intent.putExtra("ISEDIT", false);
        intent.putExtra("COMPONENTCATEGORY", mCategoryName);
        startActivityForResult(intent, ADD_COMPONENT_REQUEST);
    }

    @OnItemLongClick(R.id.activity_inventory_detail_list)
    boolean onComponentLongClick(int position) {
        Intent intent = new Intent(mContext, AddComponentActivity.class);
        intent.putExtra("INDEX", mComponentsList.get(position).getId());
        intent.putExtra("ISEDIT", true);
        intent.putExtra("COMPONENTNAME", mComponentsList.get(position).getName());
        intent.putExtra("COMPONENTNOTE", mComponentsList.get(position).getNote());
        intent.putExtra("COMPONENTTOTAL", mComponentsList.get(position).getTotal());
        intent.putExtra("COMPONENTAV", mComponentsList.get(position).getAvailable());
        intent.putExtra("COMPONENTCATEGORY", mCategoryName);
        startActivityForResult(intent, ADD_COMPONENT_REQUEST);

        return true;
    }

    private void getComponents() {
        mComponentClient.getComponents(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mCategoryId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Component>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get components started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get components completed");

                        mAdapter.refresh(mComponentsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get components error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ArrayList<Component> components) {
                        if (components == null)
                            throw new NullPointerException("Components is null");

                        mComponentsList = components;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getComponents();
    }
}
