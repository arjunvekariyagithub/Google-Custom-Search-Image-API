package com.smule.test.di.module;


import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.smule.test.ViewModelProviderFactory;
import com.smule.test.data.DataManager;
import com.smule.test.di.ActivityContext;
import com.smule.test.ui.details.DetailsViewModel;
import com.smule.test.utils.rx.AppSchedulerProvider;
import com.smule.test.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 *  Dagger dependency provider for {@link com.smule.test.ui.details.DetailsActivity}
 */
@Module
public class DetailsActivityModule {

    private AppCompatActivity mActivity;

    public DetailsActivityModule(AppCompatActivity activity) {
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
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    DetailsViewModel provideDetailsViewModel(DataManager dataManager,
                                            SchedulerProvider schedulerProvider) {
        return new DetailsViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory detailsViewModelProvider(DetailsViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }
}
