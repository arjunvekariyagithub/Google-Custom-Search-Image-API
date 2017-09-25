package com.smule.test.di.component;

import android.app.Application;
import android.content.Context;

import com.smule.test.App;
import com.smule.test.data.DataManager;
import com.smule.test.di.ApplicationContext;
import com.smule.test.di.module.ApplicationModule;
import com.smule.test.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *  Dagger component for {@link App}
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}