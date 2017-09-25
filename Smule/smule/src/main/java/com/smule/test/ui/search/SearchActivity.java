package com.smule.test.ui.search;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.smule.test.App;
import com.smule.test.BR;
import com.smule.test.R;
import com.smule.test.data.network.model.Image;
import com.smule.test.databinding.ActivitySearchBinding;
import com.smule.test.di.component.DaggerSearchActivityComponent;
import com.smule.test.di.component.SearchActivityComponent;
import com.smule.test.di.module.SearchActivityModule;
import com.smule.test.ui.EndlessRecyclerViewScrollListener;
import com.smule.test.ui.base.BaseActivity;
import com.smule.test.ui.details.DetailsViewModel;
import com.smule.test.ui.search.fullimage.FullScreenImageDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements SearchNavigator, ImageListAdapter.Callback,
        SearchView.OnQueryTextListener {

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Inject
    ViewModelProvider.Factory mSearchViewModelFactory;

    private SearchActivityComponent mSearchActivityComponent;

    ActivitySearchBinding mSearchActivityBinding;

    private SearchViewModel mSearchViewModel;

    @Inject
    public ImageListAdapter mImageAdapter;

    @Inject
    public StaggeredGridLayoutManager mLayoutManager;

    private SearchView mSearchView;

    private Context mContext;

    private String mQuery = "nature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ButterKnife.bind(this);
        mSearchActivityBinding = getViewDataBinding();
        mSearchViewModel.setNavigator(this);
        setUp();
    }

    @Override
    public void performDependencyInjection() {
        mSearchActivityComponent = DaggerSearchActivityComponent.builder()
                .searchActivityModule(new SearchActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();

        mSearchActivityComponent.inject(this);
    }

    protected void setUp() {
        setSupportActionBar(mToolbar);
        setTitle(mQuery);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mImageAdapter.setCallback(this);
        showLoading();
        mSearchViewModel.fetchImages(mQuery, 1);

        mRecyclerView.addOnScrollListener(
                /**
                 * Listener to implement pagination.
                 *
                 * See {@link EndlessRecyclerViewScrollListener}
                 */
                new EndlessRecyclerViewScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        // Triggered only when new data needs to be appended to the list
//                        if (mSearchViewModel.getNextPageOffset() != -1) {
//                            mSearchViewModel.fetchImages(mQuery, mSearchViewModel.getNextPageOffset());
//                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.setQuery("", false);
            mSearchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLayoutManager.setSpanCount(mContext.getResources().getInteger(R.integer.column_cnt));
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(List<Image> newsItems) {
        hideLoading();

        mImageAdapter.addItems(newsItems);
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mImageAdapter);
        }
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
        hideLoading();
    }

    @Override
    public void onNewsEmptyViewRetryClick() {
        showLoading();
        //mSearchViewModel.fetchImages(mQuery, 1);
    }

    @Override
    public void onNewsArticleClick(Image image) {
        FullScreenImageDialog.newInstance(image).show(getSupportFragmentManager());
    }

    private void initSearchMenu(Menu menu) {
            SearchManager manager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
            mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
            mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mImageAdapter.clear();
        mQuery = query;
        setTitle(mQuery);
        mSearchViewModel.fetchImages(query, 1);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);

        initSearchMenu(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * get view model for this view (Activity) from {@link ViewModelProviders}. This view model
     * services when activity gets restarted on config change operations hence it is the best place
     * to hold/store view's data.
     *
     * @return {@link SearchViewModel}, a view model for this view
     */
    @Override
    public SearchViewModel getViewModel() {
        mSearchViewModel = ViewModelProviders.of(this, mSearchViewModelFactory).get(SearchViewModel.class);
        return mSearchViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }
}
