package com.smule.test.ui.search;

import android.databinding.ObservableField;
import android.util.Log;

import com.smule.test.BuildConfig;
import com.smule.test.data.DataManager;
import com.smule.test.data.network.QueryBuilder;
import com.smule.test.data.network.model.Image;
import com.smule.test.data.network.model.SearchResponse;
import com.smule.test.ui.base.BaseViewModel;
import com.smule.test.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    public final ObservableField<String> appVersion = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userProfilePicUrl = new ObservableField<>();

    public SearchResponse mSearchResponse;

    public int mAction = NO_ACTION;
    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    public SearchViewModel(DataManager dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }

    /**
     *  fetch news articles from {@link BuildConfig#BASE_URL}
     *
     * @param query search term
     * @param offset offset for pagination
     */

    public void fetchImages(String query, final int offset) {
        getCompositeDisposable().add(getDataManager()
                .searchImages(BuildConfig.API_KEY, BuildConfig.CX_ID, query, QueryBuilder.PARAM_SEARCH_TYPE , offset)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SearchResponse>() {
                    @Override
                    public void accept(@NonNull SearchResponse searchResponse)
                            throws Exception {

                        mSearchResponse = searchResponse;

                        Log.d("MYLOGS" , "mSearchResponse: " + mSearchResponse);

                        List<Image> images = new ArrayList<Image>();

                        if (searchResponse == null) {
                            getNavigator().setAdapter(images);
                            return;
                        }

                        if (searchResponse.getImages() != null && searchResponse.getImages().size() > 0) {
                            images =  searchResponse.getImages();
                        } else {
                            getNavigator().onError(null);
                        }
                        getNavigator().setAdapter(images);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable t)
                            throws Exception {

                        Log.d("MYLOGS" , "Throwable: " + t.toString());

                        getNavigator().setAdapter(new ArrayList<Image>());
                        getNavigator().onError(t);
                    }
                }));
    }

    public int getNextPageOffset() {
        if (mSearchResponse == null || mSearchResponse.getQueries() == null ||
                mSearchResponse.getQueries().getNextPage() == null || mSearchResponse.getQueries().getNextPage().isEmpty()) {
            return -1;
        }
        return mSearchResponse.getQueries().getNextPage().get(0).getStartIndex();
    }
}
