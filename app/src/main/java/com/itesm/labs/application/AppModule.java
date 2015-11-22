package com.itesm.labs.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.labs.activities.AddCategoryActivity;
import com.itesm.labs.activities.AddComponentActivity;
import com.itesm.labs.activities.AddUserActivity;
import com.itesm.labs.activities.DashboardActivity;
import com.itesm.labs.activities.InventoryDetailActivity;
import com.itesm.labs.activities.LaboratoriesActivity;
import com.itesm.labs.activities.LoginActivity;
import com.itesm.labs.activities.RequestDetailActivity;
import com.itesm.labs.activities.UserDetailActivity;
import com.itesm.labs.bases.LabsAppBaseActivity;
import com.itesm.labs.bases.LabsAppBaseFragment;
import com.itesm.labs.fragments.InventoryFragment;
import com.itesm.labs.fragments.ReportsFragment;
import com.itesm.labs.fragments.RequestsFragment;
import com.itesm.labs.fragments.UsersFragment;
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

/**
 * Created by mgradob on 11/2/15.
 */
@Module(
        complete = false,
        library = true,
        injects = {
                LabsAdminApp.class,
                LabsAppBaseActivity.class,
                LabsAppBaseFragment.class,
                LoginActivity.class,
                LaboratoriesActivity.class,
                DashboardActivity.class,
                RequestsFragment.class,
                RequestDetailActivity.class,
                InventoryFragment.class,
                InventoryDetailActivity.class,
                AddCategoryActivity.class,
                AddComponentActivity.class,
                ReportsFragment.class,
                UsersFragment.class,
                UserDetailActivity.class,
                AddUserActivity.class,
                CartClient.class,
                CategoryClient.class,
                ComponentClient.class,
                DetailHistoryClient.class,
                LaboratoryClient.class,
                UserClient.class
        }
)
public class AppModule {

    private Context mContext;

    public AppModule(LabsAdminApp app) {
        super();
        mContext = app.getApplicationContext();
    }

    //region Global Variables
    @Provides
    @Singleton
    Context providesApplicationContext() {
        return mContext;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(AppConstants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AppGlobals providesAppGlobals() {
        return new AppGlobals();
    }
    //endregion

    //region Rest Clients
    @Provides
    @Singleton
    CartClient providesCartClient(CartService cartService, CategoryService categoryService, ComponentService componentService) {
        return new CartClient(cartService, categoryService, componentService);
    }

    @Provides
    @Singleton
    CategoryClient providesCategoryClient(CategoryService categoryService) {
        return new CategoryClient(categoryService);
    }

    @Provides
    @Singleton
    ComponentClient providesComponentClient(ComponentService componentService) {
        return new ComponentClient(componentService);
    }

    @Provides
    @Singleton
    DetailHistoryClient providesDetailHistoryClient(DetailHistoryService detailHistoryService, CategoryService categoryService, ComponentService componentService) {
        return new DetailHistoryClient(detailHistoryService, categoryService, componentService);
    }

    @Provides
    @Singleton
    LaboratoryClient providesLaboratoryClient(LaboratoryService laboratoryService) {
        return new LaboratoryClient(laboratoryService);
    }

    @Provides
    @Singleton
    UserClient providesUserClient(UserService userService) {
        return new UserClient(userService);
    }
    //endregion
}
