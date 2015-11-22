package com.itesm.labs.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.rest.clients.CartClient;
import com.itesm.labs.rest.clients.CategoryClient;
import com.itesm.labs.rest.clients.ComponentClient;
import com.itesm.labs.rest.clients.DetailHistoryClient;
import com.itesm.labs.rest.clients.LaboratoryClient;
import com.itesm.labs.rest.clients.UserClient;
import com.itesm.labs.rest.services.CartService;
import com.itesm.labs.rest.services.CategoryService;
import com.itesm.labs.rest.services.ComponentService;
import com.itesm.labs.rest.services.DetailHistoryService;
import com.itesm.labs.rest.services.LaboratoryService;
import com.itesm.labs.rest.services.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 11/3/15.
 */
@Module(
        complete = false,
        library = true,
        injects = {
                UserClient.class,
                LaboratoryClient.class,
                DetailHistoryClient.class,
                ComponentClient.class,
                CategoryClient.class,
                CartClient.class
        }
)
public class ApiModule {

    private final String endpoint;

    public ApiModule(final String endpoint) {
        super();
        this.endpoint = endpoint;
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Provides
    @Singleton
    RestAdapter providesRestAdapter(Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides
    @Singleton
    UserService providesUserService(RestAdapter restAdapter) {
        return restAdapter.create(UserService.class);
    }

    @Provides
    @Singleton
    LaboratoryService providesLaboratoryService(RestAdapter restAdapter) {
        return restAdapter.create(LaboratoryService.class);
    }

    @Provides
    @Singleton
    CartService providesCartService(RestAdapter restAdapter) {
        return restAdapter.create(CartService.class);
    }

    @Provides
    @Singleton
    CategoryService providesCategoryService(RestAdapter restAdapter) {
        return restAdapter.create(CategoryService.class);
    }

    @Provides
    @Singleton
    ComponentService providesComponentService(RestAdapter restAdapter) {
        return restAdapter.create(ComponentService.class);
    }

    @Provides
    @Singleton
    DetailHistoryService providesDetailHistoryService(RestAdapter restAdapter) {
        return restAdapter.create(DetailHistoryService.class);
    }
}
