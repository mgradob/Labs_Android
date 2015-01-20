package com.itesm.labs.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.ComponentModelAdapter;
import com.itesm.labs.models.ComponentModel;
import com.itesm.labs.rest.deserializers.ComponentDeserializer;
import com.itesm.labs.rest.models.Component;
import com.itesm.labs.rest.models.ComponentWrapper;
import com.itesm.labs.rest.service.ComponentService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class InventoryDetailActivity extends ActionBarActivity {

    private View mBackground;
    private ImageView mCategoryIcon;
    private TextView mCategoryName;
    private ListView mCategoryList;
    private Context mContext;
    private String ENDPOINT;
    private ArrayList<ComponentModel> componentsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        mBackground = (View) findViewById(R.id.activity_inventory_detail_background);
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

        new getComponentInfo().execute(0);
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

    private class getComponentInfo extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ComponentWrapper.class, new ComponentDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            ComponentService service = restAdapter.create(ComponentService.class);

            //ComponentWrapper componentWrapper = service.getComponents(params[0]);
            ComponentWrapper componentWrapper = service.getComponents();

            componentsData = new ArrayList<>();

            for (Component component : componentWrapper.componentList) {
                ComponentModel mComponentModel = new ComponentModel(
                        component.id,
                        component.name,
                        component.note,
                        component.total,
                        component.available,
                        R.drawable.rounded_letter
                );

                /*if (category.name.equals("Resistencia")) mCategoryModel.setImageResource(R.drawable.ic_resistencia);
                else if (category.name.equals("Capacitor")) mCategoryModel.setImageResource(R.drawable.ic_capacitores);
                else if (category.name.equals("Equipo")) mCategoryModel.setImageResource(R.drawable.ic_equipo);
                else if (category.name.equals("Kit")) mCategoryModel.setImageResource(R.drawable.ic_kits);*/

                componentsData.add(mComponentModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mCategoryList.setAdapter(new ComponentModelAdapter(mContext, componentsData));
        }
    }
}
