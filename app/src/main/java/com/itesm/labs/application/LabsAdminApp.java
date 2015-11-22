package com.itesm.labs.application;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by mgradob on 11/2/15.
 */
public class LabsAdminApp extends Application {

    private static LabsAdminApp mContext;
    private ObjectGraph mObjectGraph;

    public static LabsAdminApp get() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = (LabsAdminApp) getApplicationContext();

        setupObjectGraph();
    }

    private void setupObjectGraph() {
        Object[] modules = Modules.modulesForApp(this);
        mObjectGraph = ObjectGraph.create(modules);
    }

    public void inject(Object obj) {
        mObjectGraph.inject(obj);
    }
}
