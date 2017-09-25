package com.smule.test;

import android.app.Application;

import com.smule.test.data.DataManager;
import com.smule.test.di.component.ApplicationComponent;
import com.smule.test.di.component.DaggerApplicationComponent;
import com.smule.test.di.module.ApplicationModule;
import com.smule.test.di.module.NetworkModule;

import javax.inject.Inject;


public class App extends Application {

    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).
                        networkModule(new NetworkModule(BuildConfig.BASE_URL)).
                        build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Required to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
