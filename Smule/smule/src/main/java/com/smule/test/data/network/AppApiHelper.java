package com.smule.test.data.network;


import com.smule.test.data.network.model.SearchResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.http.Query;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    public AppApiHelper() {
    }

    @Override
    public Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                                   @Query("cx") String cx,
                                                   @Query("q") String query,
                                                   @Query("searchType") String searchType,
                                                   @Query("startIndex") int idx) {
        return null;
    }
}

