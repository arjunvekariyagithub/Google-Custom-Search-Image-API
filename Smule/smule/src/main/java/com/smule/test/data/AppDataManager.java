package com.smule.test.data;


import android.content.Context;

import com.smule.test.data.network.ApiHelper;
import com.smule.test.data.network.model.SearchResponse;
import com.smule.test.data.preferences.PreferencesHelper;
import com.smule.test.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Query;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void saveState(String keyString, int val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, String val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, long val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public void saveState(String keyString, boolean val) {
        mPreferencesHelper.saveState(keyString, val);
    }

    @Override
    public String loadStringKey(String keyString) {
        return mPreferencesHelper.loadStringKey(keyString);
    }

    @Override
    public int loadIntKey(String keyString, int returnValue) {
        return mPreferencesHelper.loadIntKey(keyString, returnValue);
    }

    @Override
    public long loadLongKey(String keyString, long returnValue) {
        return mPreferencesHelper.loadLongKey(keyString, returnValue);
    }

    @Override
    public boolean loadBooleanKey(String keyString, boolean returnValue) {
        return mPreferencesHelper.loadBooleanKey(keyString, returnValue);
    }

    @Override
    public String loadSettingsStringKey(String keyString, String returnValue) {
        return mPreferencesHelper.loadSettingsStringKey(keyString, returnValue);
    }

    @Override
    public Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                                   @Query("cx") String cx,
                                                   @Query("q") String query,
                                                   @Query("searchType") String searchType,
                                                   @Query("startIndex") int offset) {
        return mApiHelper.searchImages(apiKey, cx, query, searchType, offset);
    }
}
