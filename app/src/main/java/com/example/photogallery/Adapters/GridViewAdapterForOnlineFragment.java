package com.example.photogallery.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.photogallery.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class GridViewAdapterForOnlineFragment extends BaseAdapter {

    int layout;
    ArrayList<RequestCreator> listImages;
    ArrayList<String> listLinkImgs;
    LayoutInflater mInflater;

    public GridViewAdapterForOnlineFragment(Context context, int layout, ArrayList<String> listLinkImgs) {
        this.layout = layout;
        this.listImages = new ArrayList<>();
        this.listLinkImgs = listLinkImgs;
        this.mInflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        ImageView imageItemView;
    }

    @Override
    public int getCount() {
        return listLinkImgs.size();
    }

    @Override
    public Object getItem(int position) {
        return listLinkImgs.get(position);
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
            listImages.get(position).into(viewHolder.imageItemView);
        } catch (Exception e){
            RequestCreator requestCreator = Picasso.get()
                    .load(listLinkImgs.get(position))
                    .placeholder(R.drawable.blankimg)
                    .error(R.drawable.logo);
            requestCreator.into(viewHolder.imageItemView);

            listImages.add(position, requestCreator);
        }

        return convertView;
    }

}
