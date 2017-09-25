package com.smule.test.ui.search.fullimage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.smule.test.App;
import com.smule.test.R;
import com.smule.test.data.network.model.Image;
import com.smule.test.databinding.FullScreenImageDialogBinding;
import com.smule.test.di.component.DaggerFullScreenImageDialogComponent;
import com.smule.test.di.component.FullScreenImageDialogComponent;
import com.smule.test.di.module.SearchActivityModule;
import com.smule.test.ui.base.BaseDialog;
import com.smule.test.ui.details.DetailsActivity;
import com.smule.test.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenImageDialog extends BaseDialog implements FullScreenImageCallback {

    private static final String TAG = "FullScreenImageDialog";

    @Inject
    FullScreenImageDialogViewModel mFullScreenImageDialogViewModel;

    private FullScreenImageDialogComponent mFullScreenImageDialogComponent;

    @BindView(R.id.photo)
    public ImageView mPhotoView;

    @BindView(R.id.title)
    public TextView mTitleTextView;

    public static FullScreenImageDialog newInstance(Image image) {
        FullScreenImageDialog fragment = new FullScreenImageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.KEY_PHOTO_URL, image.getLink());
        bundle.putString(AppConstants.KEY_TITLE, image.getTitle());
        bundle.putString(AppConstants.KEY_WEB_URL, image.getImageInfo().getContextLink());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FullScreenImageDialogBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.full_screen_image_dialog, container, false);
        View view = binding.getRoot();

        ButterKnife.bind(this, view);

        performDependencyInjection();

        binding.setViewModel(mFullScreenImageDialogViewModel);

        mFullScreenImageDialogViewModel.setNavigator(this);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
    }

    private void bindView() {
        Bundle b = getArguments();

        String photoUrl = b.getString(AppConstants.KEY_PHOTO_URL);

        String title = b.getString(AppConstants.KEY_TITLE);
        mFullScreenImageDialogViewModel.setIsLoading(true);
        Glide.with(mPhotoView.getContext())
                .load(photoUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        getBaseActivity().onError(new Throwable("Can not load image"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mFullScreenImageDialogViewModel.setIsLoading(false);
                        return false;
                    }
                })
                .crossFade()
                .dontTransform()
                .into(mPhotoView);

        mTitleTextView.setText(title);

        // show title as link and make it clickable
        mTitleTextView.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) mTitleTextView.getText();
        ClickableSpan clickSpan = new ClickableSpan() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseActivity(), DetailsActivity.class);
                intent.putExtra(AppConstants.KEY_WEB_URL, b.getString(AppConstants.KEY_WEB_URL));
                getBaseActivity().startActivity(intent);
            }
        };
        spans.setSpan(clickSpan, 0, spans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public void performDependencyInjection() {
        mFullScreenImageDialogComponent = DaggerFullScreenImageDialogComponent.builder()
                .searchActivityModule(new SearchActivityModule(getBaseActivity()))
                .applicationComponent(((App) getBaseActivity().getApplication()).getComponent())
                .build();

        mFullScreenImageDialogComponent.inject(this);
    }
}
