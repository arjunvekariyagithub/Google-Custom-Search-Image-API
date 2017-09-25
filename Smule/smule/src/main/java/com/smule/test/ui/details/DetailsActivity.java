package com.smule.test.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smule.test.App;
import com.smule.test.BR;
import com.smule.test.R;
import com.smule.test.databinding.ActivityDetailsBinding;
import com.smule.test.di.component.DaggerDetailsActivityComponent;
import com.smule.test.di.component.DetailsActivityComponent;
import com.smule.test.di.module.DetailsActivityModule;
import com.smule.test.ui.base.BaseActivity;
import com.smule.test.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailsActivity extends BaseActivity<ActivityDetailsBinding, DetailsViewModel> implements DetailsNavigator {


    private String mCurrentUrl = null;

    @BindView(R.id.web_view)
    public WebView mWebView;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.error_layout)
    public LinearLayout mErrorLayout;

    @BindView(R.id.error_txt_what)
    public TextView mTextErrorWhat;

    @BindView(R.id.error_txt_cause)
    public TextView mTextError;

    @BindView(R.id.error_btn_retry)
    public Button mRetryButton;

    @Inject
    ViewModelProvider.Factory mDetailsViewModelFactory;

    private DetailsActivityComponent mDetailsActivityComponent;

    ActivityDetailsBinding mDetailsActivityBinding;

    private DetailsViewModel mDetailsViewModel;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ButterKnife.bind(this);
        mDetailsActivityBinding = getViewDataBinding();
        mDetailsViewModel.setNavigator(this);
        setUp();
    }

    @Override
    public void performDependencyInjection() {
        mDetailsActivityComponent = DaggerDetailsActivityComponent.builder()
                .detailsActivityModule(new DetailsActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();

        mDetailsActivityComponent.inject(this);
    }

    protected void setUp() {
        showLoading();
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        setTitle(getWebUrl());
        mTextErrorWhat.setTextColor(getResources().getColor(R.color.colorBlack));
        mTextErrorWhat.setTextColor(getResources().getColor(R.color.colorBlack));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        mWebView.setWebViewClient(new BrowserClient());

        mDetailsViewModel.onViewInitialized();
    }

    @OnClick(R.id.error_btn_retry)
    void onRetryClick() {
        mDetailsViewModel.onViewInitialized();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    // Manages the behavior when URLs are loaded
    private class BrowserClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideLoading();
        }
    }

    @Override
    public void loadDataInWebView() {
        hideErrorView();
        mWebView.loadUrl(getWebUrl());
    }

    @Override
    public void showErrorView(String error) {
        mErrorLayout.setVisibility(View.VISIBLE);
        if (mTextError != null)
            mTextError.setText(error);
        super.onError(error);
    }

    @Override
    public void showErrorView(int resId) {
        showErrorView(getString(resId));
    }

    @Override
    public void hideErrorView() {
        mErrorLayout.setVisibility(View.GONE);
    }

    public String getWebUrl() {
        if (mCurrentUrl == null) {
            Intent i = getIntent();
            mCurrentUrl = i.getStringExtra(AppConstants.KEY_WEB_URL);
        }
        return mCurrentUrl;
    }

    @Override
    protected void onDestroy() {
        mWebView.freeMemory();
        super.onDestroy();
    }

    /**
     * get view model for this view (Activity) from {@link ViewModelProviders}. This view model
     * services when activity gets restarted on config change operations hence it is the best place
     * to hold/store view's data.
     *
     * @return {@link DetailsViewModel}, a view model for this view
     */
    @Override
    public DetailsViewModel getViewModel() {
        mDetailsViewModel = ViewModelProviders.of(this, mDetailsViewModelFactory).get(DetailsViewModel.class);
        return mDetailsViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }
}
