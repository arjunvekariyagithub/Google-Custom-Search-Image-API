package com.smule.test.di.component;

import com.smule.test.di.PerActivity;
import com.smule.test.di.module.SearchActivityModule;
import com.smule.test.ui.search.fullimage.FullScreenImageDialog;

import dagger.Component;

/**
 *  Dagger component for {@link FullScreenImageDialog}
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchActivityModule.class)
public interface FullScreenImageDialogComponent {

    void inject(FullScreenImageDialog dialog);

}
