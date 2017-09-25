package com.smule.test.di.module;

import android.app.Application;
import android.content.Context;

import com.smule.test.BuildConfig;
import com.smule.test.data.AppDataManager;
import com.smule.test.data.DataManager;
import com.smule.test.data.preferences.AppPreferencesHelper;
import com.smule.test.data.preferences.PreferencesHelper;
import com.smule.test.di.ApiInfo;
import com.smule.test.di.ApplicationContext;
import com.smule.test.di.PreferenceInfo;
import com.smule.test.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *  Dagger dependency provider for {@link com.smule.test.App}
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
