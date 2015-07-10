package com.itesm.labs.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.async_tasks.GetComponentsInfo;
import com.itesm.labs.rest.models.Component;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class InventoryDetailActivity extends AppCompatActivity {

    private View mBackground;
    private ImageView mCategoryIcon;
    private TextView mCategoryName;
    private ListView mCategoryComponentList;
    private FloatingActionButton mFab;

    private Context mContext;

    private String ENDPOINT;
    private String CATEGORY_NAME;
    private int CATEGORY_ID;
    private ArrayList<Component> componentsData;

    private final static int ADD_COMPONENT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        mBackground = findViewById(R.id.activity_inventory_detail_background);
        mCategoryIcon = (ImageView) findViewById(R.id.activity_inventory_detail_image);
        mCategoryName = (TextView) findViewById(R.id.activity_inventory_detail_name);
        mCategoryComponentList = (ListView) findViewById(R.id.activity_inventory_detail_list);
        mFab = (FloatingActionButton) findViewById(R.id.activity_inventory_detail_fab);
        mFab.attachToListView(mCategoryComponentList);

        mContext = getApplicationContext();
        Intent callingIntent = getIntent();
        ENDPOINT = callingIntent.getStringExtra("ENDPOINT");

        mCategoryIcon.setImageDrawable(getResources().getDrawable(
                callingIntent.getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category)
        ));
        CATEGORY_NAME = callingIntent.getStringExtra("CATEGORYTITLE");
        mCategoryName.setText(CATEGORY_NAME);

        Bitmap iconBitmap = BitmapFactory.decodeResource(
                getResources(),
                callingIntent.getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category)
        );
        Palette palette = Palette.generate(iconBitmap);

        mBackground.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));

        mFab.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.accent)));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddComponentActivity.class);
                intent.putExtra("ENDPOINT", ENDPOINT);
                intent.putExtra("INDEX", componentsData.get(componentsData.size()-1).getId()+1);
                intent.putExtra("ISEDIT", false);
                intent.putExtra("COMPONENTCATEGORY", CATEGORY_NAME);
                startActivityForResult(intent, ADD_COMPONENT_REQUEST);
            }
        });

        Window window = getWindow();
        window.setStatusBarColor(palette.getDarkVibrantColor(getResources().getColor(R.color.primary_dark)));

        CATEGORY_ID = callingIntent.getIntExtra("CATEGORYID", -1);

        GetComponentsInfo componentsInfo = new GetComponentsInfo() {
            @Override
            protected void onPostExecute(ArrayList<Component> components) {
                super.onPostExecute(components);
                componentsData = components;
                mCategoryComponentList.setAdapter(new ComponentModelAdapter(mContext, componentsData));
            }
        };
        componentsInfo.execute(ENDPOINT, "" + CATEGORY_ID);
        mCategoryComponentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, AddComponentActivity.class);

                intent.putExtra("ENDPOINT", ENDPOINT);
                intent.putExtra("INDEX", componentsData.get(position).getId());
                intent.putExtra("ISEDIT", true);
                intent.putExtra("COMPONENTNAME", componentsData.get(position).getName());
                intent.putExtra("COMPONENTNOTE", componentsData.get(position).getNote());
                intent.putExtra("COMPONENTTOTAL", componentsData.get(position).getTotal());
                intent.putExtra("COMPONENTAV", componentsData.get(position).getAvailable());
                intent.putExtra("COMPONENTCATEGORY", CATEGORY_NAME);
                startActivityForResult(intent, ADD_COMPONENT_REQUEST);

                return true;
            }
        });

        window.getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mBackground.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_top));
                mFab.setVisibility(View.INVISIBLE);
                mCategoryComponentList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Animation fabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_bottom);
                fabAnim.setStartOffset(500);
                mFab.startAnimation(fabAnim);
                mFab.setVisibility(View.VISIBLE);
                mCategoryComponentList.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_from_top));
                mCategoryComponentList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        mFab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_bottom));
        mFab.setVisibility(View.INVISIBLE);
        Animation listAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_top);
        listAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCategoryComponentList.setVisibility(View.INVISIBLE);

                Animation bkgAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_top);
                bkgAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBackground.setVisibility(View.INVISIBLE);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mBackground.startAnimation(bkgAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCategoryComponentList.startAnimation(listAnim);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        GetComponentsInfo componentsInfo = new GetComponentsInfo() {
            @Override
            protected void onPostExecute(ArrayList<Component> components) {
                super.onPostExecute(components);
                componentsData = components;
                mCategoryComponentList.setAdapter(new ComponentModelAdapter(mContext, componentsData));
            }
        };
        componentsInfo.execute(ENDPOINT, "" + CATEGORY_ID);
    }
}
