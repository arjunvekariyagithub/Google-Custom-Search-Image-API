package com.smule.test.di.module;


import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.smule.test.R;
import com.smule.test.ViewModelProviderFactory;
import com.smule.test.data.DataManager;
import com.smule.test.data.network.model.Image;
import com.smule.test.di.ActivityContext;
import com.smule.test.ui.search.ImageListAdapter;
import com.smule.test.ui.search.SearchViewModel;
import com.smule.test.ui.search.fullimage.FullScreenImageDialogViewModel;
import com.smule.test.utils.rx.AppSchedulerProvider;
import com.smule.test.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 *  Dagger dependency provider for {@link com.smule.test.ui.search.SearchActivity}
 */
@Module
public class SearchActivityModule {

    private AppCompatActivity mActivity;

    public SearchActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new SearchViewModel(dataManager, schedulerProvider);
    }

    @Provides
    FullScreenImageDialogViewModel provideFullScreenImageDialogViewModel(DataManager dataManager,
                                                          SchedulerProvider schedulerProvider) {
        return new FullScreenImageDialogViewModel(dataManager, schedulerProvider);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    ViewModelProvider.Factory searchViewModelProvider(SearchViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }

    @Provides
    ImageListAdapter provideNewsListAdapter(AppCompatActivity activity) {
        return new ImageListAdapter(new ArrayList<Image>());
    }

    @Provides
    StaggeredGridLayoutManager provideStaggeredGridLayoutManager(AppCompatActivity activity) {
        return new StaggeredGridLayoutManager(activity.getResources().getInteger(R.integer.column_cnt), 1);
    }
}
