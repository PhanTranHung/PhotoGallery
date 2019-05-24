package com.example.photogallery.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;

public class TabImagesAdapter extends PagerAdapter {

    Context context;
    ArrayList<File> listFiles;
    int position;
    LayoutInflater inflater;

    public TabImagesAdapter(Context context, ArrayList<File> listFiles) {
        this.context = context;
        this.listFiles = listFiles;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listFiles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        unbindDrawables((View) object);
        object = null;
//        container.getChildAt(position).;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.fragment_sliding_images, container, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);
        imageView.setImageURI(Uri.fromFile(listFiles.get(position).getAbsoluteFile()));
        container.addView(imageLayout);
        return imageLayout;
    }

    protected void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
