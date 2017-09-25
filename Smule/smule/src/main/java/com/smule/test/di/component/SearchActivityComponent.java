package com.smule.test.di.component;

import com.smule.test.di.PerActivity;
import com.smule.test.di.module.SearchActivityModule;
import com.smule.test.ui.search.SearchActivity;
import com.smule.test.ui.search.fullimage.FullScreenImageDialog;

import dagger.Component;

/**
 *  Dagger component for {@link SearchActivity}
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchActivityModule.class)
public interface SearchActivityComponent {

    void inject(SearchActivity activity);

    void inject(FullScreenImageDialog dialog);

}
