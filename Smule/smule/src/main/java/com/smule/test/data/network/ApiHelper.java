package com.smule.test.data.network;

import com.smule.test.data.network.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  Interface holding signatures for network APIs
 *
 */

public interface ApiHelper {

    /**
     * rest api to search images from https://www.googleapis.com/customsearch/v1.
     *
     * @param apiKey api key for accessing data from
     * @param cx custom search engine ID
     * @param query   search term
     * @param searchType type of context to search
     * @param offset   offset for the result page
     * @return data in {@link SearchResponse} form
     */

    @GET("v1")
    Observable<SearchResponse> searchImages(@Query("key") String apiKey,
                                            @Query("cx") String cx,
                                            @Query("q") String query,
                                            @Query("searchType") String searchType,
                                            @Query("startIndex") int offset);
}
