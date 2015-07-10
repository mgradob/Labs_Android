package com.itesm.labs.rest.queries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.models.Laboratory;
import com.itesm.labs.rest.service.LaboratoryService;
import com.itesm.labs.rest.service.UserService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 7/9/15.
 */
public class LabQuery {

    private String ENDPOINT;

    public LabQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public LabQuery() {
        this.ENDPOINT = "http://labs.chi.itesm.mx:8080/labs/";
    }

    public Laboratory getLab(String link){
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        LaboratoryService service = restAdapter.create(LaboratoryService.class);

        return service.getLaboratory(link);
    }

    public ArrayList<Laboratory> getLabs(){
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        LaboratoryService service = restAdapter.create(LaboratoryService.class);

        return service.getLaboratories();
    }
}
