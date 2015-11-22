package com.itesm.labs.application;

import com.itesm.labs.BuildConfig;
import com.itesm.labs.rest.ApiModule;

/**
 * Created by mgradob on 11/2/15.
 */
public class Modules {

    private static Modules modules;

    private Modules() {
    }

    public static Modules instance() {
        if (modules == null) {
            modules = new Modules();
        }

        return modules;
    }

    public static Object[] modulesForApp(LabsAdminApp app) {
        return new Object[]{
                new ApiModule(BuildConfig.BASE_API_URL),
                new AppModule(app)
        };
    }
}
