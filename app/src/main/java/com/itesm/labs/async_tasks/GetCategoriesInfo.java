package com.itesm.labs.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.R;
import com.itesm.labs.adapters.CategoriesModelAdapter;
import com.itesm.labs.rest.models.Category;
import com.itesm.labs.rest.service.CategoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 1/29/15.
 */
public class GetCategoriesInfo extends AsyncTask<String, Void, ArrayList<Category>> {

    @Override
    protected ArrayList<Category> doInBackground(String... ENDPOINT) {
        ArrayList<Category> categoriesData;

        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT[0])
                .setConverter(new GsonConverter(gson))
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        categoriesData = service.getCategories();

        for (Category category : categoriesData) {
            if (category.id == 0) category.setImageResource(R.drawable.ic_resistencias);
            else if (category.id == 1) category.setImageResource(R.drawable.ic_capacitores);
            else if (category.id == 2) category.setImageResource(R.drawable.ic_equipo);
            else if (category.id == 3) category.setImageResource(R.drawable.ic_kits);
            else if (category.id == 4) category.setImageResource(R.drawable.ic_cables);
            else if (category.id == 5) category.setImageResource(R.drawable.ic_integrados);
            else if (category.id == 6) category.setImageResource(R.drawable.ic_diodos);
            else if (category.id == 7) category.setImageResource(R.drawable.ic_herramientas);
            else if (category.id == 8) category.setImageResource(R.drawable.ic_switches);
            else if (category.id == 9) category.setImageResource(R.drawable.ic_adaptadores);
            else if (category.id == 10) category.setImageResource(R.drawable.ic_displays);
            else if (category.id == 11) category.setImageResource(R.drawable.ic_inductores);
            else if (category.id == 12) category.setImageResource(R.drawable.ic_sensores);
            else if (category.id == 13) category.setImageResource(R.drawable.ic_motores);
            else if (category.id == 14) category.setImageResource(R.drawable.ic_potenciometro);
            else if (category.id == 15) category.setImageResource(R.drawable.ic_transformadores);
            else if (category.id == 16) category.setImageResource(R.drawable.ic_transistores);
        }

        return categoriesData;
    }
}
