package com.itesm.labs.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.R;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.async_tasks.GetComponentsInfo;
import com.itesm.labs.rest.models.Component;

import java.util.ArrayList;


public class InventoryDetailActivity extends ActionBarActivity {

    private View mBackground;
    private ImageView mCategoryIcon;
    private TextView mCategoryName;
    private ListView mCategoryList;
    private Context mContext;
    private String ENDPOINT;
    private ArrayList<Component> componentsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        mBackground = findViewById(R.id.activity_inventory_detail_background);
        mCategoryIcon = (ImageView) findViewById(R.id.activity_inventory_detail_image);
        mCategoryName = (TextView) findViewById(R.id.activity_inventory_detail_name);
        mCategoryList = (ListView) findViewById(R.id.activity_inventory_detail_list);
        mContext = getApplicationContext();
        ENDPOINT = getIntent().getStringExtra("ENDPOINT");

        mCategoryIcon.setImageDrawable(getResources().getDrawable(
                getIntent().getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category)
        ));
        mCategoryName.setText(getIntent().getStringExtra("CATEGORYTITLE"));

        Bitmap iconBitmap = BitmapFactory.decodeResource(
                getResources(),
                getIntent().getIntExtra("CATEGORYICON", R.drawable.ic_dummy_category)
        );
        Palette palette = Palette.generate(iconBitmap);
        mBackground.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        Window window = getWindow();
        window.setStatusBarColor(palette.getDarkVibrantColor(getResources().getColor(R.color.primary_dark)));

        GetComponentsInfo componentsInfo = new GetComponentsInfo() {
            @Override
            protected void onPostExecute(ArrayList<Component> components) {
                super.onPostExecute(components);
                componentsData = components;
                mCategoryList.setAdapter(new ComponentModelAdapter(mContext, componentsData));
            }
        };
        componentsInfo.execute(ENDPOINT);
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
}
