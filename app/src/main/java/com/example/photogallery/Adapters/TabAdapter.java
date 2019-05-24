package com.example.photogallery.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.photogallery.Fragment.AlbumsFragment;
import com.example.photogallery.Fragment.OnlineFragment;
import com.example.photogallery.Fragment.PhotosFragment;

public class TabAdapter extends FragmentPagerAdapter {

    Context context;
    String[] title = new String[]{"Photos", "Albums", "Online"};

    AlbumsFragment albumsFragment;
    PhotosFragment photosFragment;
    OnlineFragment onlineFragment;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        albumsFragment = new AlbumsFragment();
        photosFragment = new PhotosFragment();
        onlineFragment = new OnlineFragment();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return photosFragment;
            case 1:
                return albumsFragment;
            case 2:
                return onlineFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
