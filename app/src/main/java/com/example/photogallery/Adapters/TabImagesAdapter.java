package com.example.photogallery.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.photogallery.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class TabImagesAdapter extends PagerAdapter {

    Context context;
    ArrayList listElements;
    int position;
    LayoutInflater inflater;

    public TabImagesAdapter(Context context, ArrayList listFiles) {
        this.context = context;
        this.listElements = listFiles;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listElements.size();
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
        View view = inflater.inflate(R.layout.fragment_sliding_images, container, false);

        assert view != null;
        final ImageView imageView = view.findViewById(R.id.image);
        container.addView(view);

        Uri uriImg = Uri.parse(listElements.get(position).toString());
        if  (uriImg.isRelative() && !uriImg.isAbsolute())
            imageView.setImageURI(Uri.parse(listElements.get(position).toString()));
        else
            Picasso.get().load(Uri.parse(listElements.get(position).toString()))
            .placeholder(R.drawable.blankimg)
            .error(R.drawable.logo)
            .into(imageView);

        return view;
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
