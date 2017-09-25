package com.smule.test.di.component;

import com.smule.test.di.PerActivity;
import com.smule.test.di.module.DetailsActivityModule;
import com.smule.test.ui.details.DetailsActivity;

import dagger.Component;

/**
 *  Dagger component for {@link DetailsActivity}
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = DetailsActivityModule.class)
public interface DetailsActivityComponent {

    void inject(DetailsActivity activity);
}
