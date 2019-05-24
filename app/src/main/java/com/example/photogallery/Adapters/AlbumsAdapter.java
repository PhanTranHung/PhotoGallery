package com.example.photogallery.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photogallery.Loading.LoadingImage;
import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;

public class AlbumsAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Bitmap> listImages;
    ArrayList<File> listFile;
    LayoutInflater mInflater;

    public AlbumsAdapter(Context context, int layout, ArrayList<File> listFile) {
        this.context = context;
        this.layout = layout;
        this.listFile = listFile;
        this.mInflater = LayoutInflater.from(context);
        listImages = new ArrayList<>();
    }

    private class ViewHolder{
        ImageView imageItemView;
        TextView nameAlbum;
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
            viewHolder.imageItemView = convertView.findViewById(R.id.album_item);
            viewHolder.nameAlbum = convertView.findViewById(R.id.name_album);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.imageItemView.setImageBitmap(listImages.get(position));
        } catch (Exception e){
            viewHolder.imageItemView.setImageResource(R.drawable.blankimg);
            new LoadingImage(listImages, viewHolder.imageItemView).execute(getAnyImage(listFile.get(position)), position, context);
            e.getStackTrace();
        } finally {
            viewHolder.nameAlbum.setText(listFile.get(position).getName());
        }
        return convertView;
    }

    public File getAnyImage(File file){
        for(File child: file.listFiles())
            if (child.getName().trim().endsWith(".jpg") || child.getName().trim().endsWith(".png")) {
                return child;
            }
        return null;
    }
}
