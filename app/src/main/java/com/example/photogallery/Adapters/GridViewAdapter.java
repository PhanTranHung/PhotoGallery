package com.example.photogallery.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.photogallery.Loading.LoadingImage;
import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    int layout;
    ArrayList<Bitmap> listImages;
    ArrayList<File> listFile;
    LayoutInflater mInflater;

    public GridViewAdapter(Context context, int layout, ArrayList<File> listFile) {
        this.layout = layout;
        this.listImages = new ArrayList<>();
        this.listFile = listFile;
        this.mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        ImageView imageItemView;
    }

    @Override
    public int getCount() {
        return listFile.size();
    }

    @Override
    public Object getItem(int position) {
        return listFile.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            convertView = mInflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageItemView = convertView.findViewById(R.id.img_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.imageItemView.setImageBitmap(listImages.get(position));
        } catch (Exception e){
            viewHolder.imageItemView.setImageResource(R.drawable.blankimg);
            new LoadingImage(listImages, viewHolder.imageItemView).execute(listFile.get(position), position);
            e.getStackTrace();
        }

        return convertView;
    }

}
