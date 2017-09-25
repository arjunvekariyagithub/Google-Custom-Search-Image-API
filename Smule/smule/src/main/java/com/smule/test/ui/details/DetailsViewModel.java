package com.smule.test.ui.details;

import com.smule.test.R;
import com.smule.test.data.DataManager;
import com.smule.test.ui.base.BaseViewModel;
import com.smule.test.utils.rx.SchedulerProvider;

public class DetailsViewModel extends BaseViewModel<DetailsNavigator> {

    public DetailsViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onViewInitialized() {
        if (getNavigator().isNetworkConnected()) {
            getNavigator().showLoading();
            getNavigator().loadDataInWebView();
        } else {
            getNavigator().showErrorView(R.string.error_msg_no_internet);
        }
    }
}