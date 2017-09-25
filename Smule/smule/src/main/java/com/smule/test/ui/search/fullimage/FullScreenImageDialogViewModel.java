package com.smule.test.ui.search.fullimage;


import com.smule.test.data.DataManager;
import com.smule.test.ui.base.BaseViewModel;
import com.smule.test.utils.rx.SchedulerProvider;

public class FullScreenImageDialogViewModel extends BaseViewModel<FullScreenImageCallback> {



    public FullScreenImageDialogViewModel(DataManager dataManager,
                                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        getNavigator().dismissDialog();
    }

}
