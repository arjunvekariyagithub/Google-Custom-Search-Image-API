package com.smule.test.ui.search;

import com.smule.test.data.network.model.Image;

import java.util.List;

public interface SearchNavigator {

    void setAdapter(List<Image> newsItems);

    void onError(Throwable error);
}
