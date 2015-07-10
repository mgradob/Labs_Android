package com.itesm.labs.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.R;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.queries.CategoryQuery;

import java.util.ArrayList;

public class AddComponentActivity extends AppCompatActivity {

    private EditText mComponentName;
    private EditText mComponentNote;
    private EditText mComponentTotal;
    private EditText mComponentAv;
    private TextView mComponentId;
    private TextView mComponentCategory;
    private TextView mComponentAction;

    private String ENDPOINT;
    private Boolean isEditting;
    private int newComponentId;
    private int newComponentCatId;
    private String mCategoryName;
    private ArrayList<Category> mCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_component);

        mComponentName = (EditText) findViewById(R.id.activity_add_component_name);
        mComponentNote = (EditText) findViewById(R.id.activity_add_component_note);
        mComponentTotal = (EditText) findViewById(R.id.activity_add_component_total);
        mComponentAv = (EditText) findViewById(R.id.activity_add_component_av);
        mComponentId = (TextView) findViewById(R.id.activity_add_component_id);
        mComponentCategory = (TextView) findViewById(R.id.activity_add_component_category);
        mComponentAction = (TextView) findViewById(R.id.activity_add_component_action);

        Intent callingIntent = getIntent();
        ENDPOINT = callingIntent.getStringExtra("ENDPOINT");
        newComponentId = callingIntent.getIntExtra("INDEX", -1);
        isEditting = callingIntent.getBooleanExtra("ISEDIT", false);
        mCategoryName = callingIntent.getStringExtra("COMPONENTCATEGORY");

        mComponentId.setText("ID: " + newComponentId);
        mComponentCategory.setText("Categoría: " + mCategoryName);

        if(isEditting){
            mComponentAction.setText("Editar:");
            mComponentName.setText(callingIntent.getStringExtra("COMPONENTNAME"));
            mComponentNote.setText(callingIntent.getStringExtra("COMPONENTNOTE"));
            mComponentTotal.setText("" + callingIntent.getIntExtra("COMPONENTTOTAL", 0));
            mComponentAv.setText("" + callingIntent.getIntExtra("COMPONENTAV", 0));
        }

        getCategories();

        mComponentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mCategoryList.isEmpty()){
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
            }
        });
    }

    private void getCategories(){
        new AsyncTask<Void, Void, ArrayList<Category>>() {
            @Override
            protected ArrayList<Category> doInBackground(Void... params) {
                return new CategoryQuery(ENDPOINT).getCategories();
            }

            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                mCategoryList = categories;
            }
        }.execute();
    }
}
