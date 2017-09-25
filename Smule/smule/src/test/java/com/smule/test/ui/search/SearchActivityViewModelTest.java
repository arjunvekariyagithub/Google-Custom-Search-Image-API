package com.smule.test.ui.search;

import com.smule.test.BuildConfig;
import com.smule.test.data.DataManager;
import com.smule.test.data.network.QueryBuilder;
import com.smule.test.data.network.model.Image;
import com.smule.test.data.network.model.SearchResponse;
import com.smule.test.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityViewModelTest {

    @Mock
    SearchNavigator mSearchCallback;
    @Mock
    DataManager mMockDataManager;

    private SearchViewModel mSearchViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mSearchViewModel = new SearchViewModel(
                mMockDataManager,
                testSchedulerProvider);
        mSearchViewModel.onViewCreated();
        mSearchViewModel.setNavigator(mSearchCallback);
    }

    @Test
    public void testFetchImagesSuccess() {

        String query = "nature";
        int offset = 1;

        SearchResponse searchResponse = new SearchResponse();

        List<Image> images = new ArrayList<Image>();
        images.add(new Image());

        searchResponse.setImages(images);

        doReturn(Observable.just(searchResponse))
                .when(mMockDataManager)
                .searchImages(BuildConfig.API_KEY, BuildConfig.CX_ID, query, QueryBuilder.PARAM_SEARCH_TYPE, offset);

        mSearchViewModel.fetchImages(query, offset);

        mTestScheduler.triggerActions();

        verify(mSearchCallback).setAdapter(images);
    }

    @Test
    public void testFetchImagesFail() {

        String query = "nature";
        int offset = 1;

        SearchResponse searchResponse = new SearchResponse();

        doReturn(Observable.just(searchResponse))
                .when(mMockDataManager)
                .searchImages(BuildConfig.API_KEY, BuildConfig.CX_ID, query, QueryBuilder.PARAM_SEARCH_TYPE, offset);

        mSearchViewModel.fetchImages(query, offset);

        mTestScheduler.triggerActions();

        verify(mSearchCallback).onError(null);
    }

    @After
    public void tearDown() throws Exception {
        mSearchViewModel.onDestroyView();
    }

}
