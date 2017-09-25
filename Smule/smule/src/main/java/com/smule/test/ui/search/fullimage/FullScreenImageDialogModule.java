package com.smule.test.ui.search.fullimage;

import com.smule.test.data.DataManager;
import com.smule.test.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class FullScreenImageDialogModule {

    @Provides
    FullScreenImageDialogViewModel provideRateUsViewModel(DataManager dataManager,
                                                          SchedulerProvider schedulerProvider) {
        return new FullScreenImageDialogViewModel(dataManager, schedulerProvider);
    }

}
