package com.smule.test.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**

 * {@link BindingAdapter} for list item data binding.
 */

public class CustomBindingAdapter {

    /**
     * Customized binding method for loading images into {@link ImageView}.
     * Using Glide library to handle image loading.
     * <p>
     *
     * @param imageView image view to load thumbnails into
     * @param url       url for thumbnail of particular product
     *
     */
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(final ImageView imageView, String url) {
        if (url == null) return;

        Glide.with(imageView.getContext()).load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .crossFade()
                .into(imageView);
    }
}