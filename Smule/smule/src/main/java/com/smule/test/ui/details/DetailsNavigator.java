package com.smule.test.ui.details;

/**
 * interface defining operations for the view. It is implemented by view and used by view model
 * to communicate with view.
 */

public interface DetailsNavigator {

    void onError(Throwable error);

    void loadDataInWebView();

    void showLoading();

    void showErrorView(String error);

    void showErrorView(int resId);

    void hideErrorView();

    boolean isNetworkConnected();
}
