package com.example.photogallery.Loading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;


public class LoadingImage extends AsyncTask<Object, Object, Bitmap>{

    ArrayList<Bitmap> listImage;
    ImageView imageView;

    public LoadingImage(ArrayList<Bitmap> listImage, ImageView imageView) {
        this.listImage = listImage;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Object... objects) {

        File file = (File) objects[0];
        int position = (int) objects[1];
        Bitmap imageLoaded;

        if (file == null){
            imageLoaded = BitmapFactory.decodeResource(((Context)objects[2]).getResources(), R.drawable.blankimg);
            listImage.add(position, imageLoaded);
            return imageLoaded;
        }

        int thumbnailSize = 100;
        imageLoaded = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageLoaded = ThumbnailUtils.extractThumbnail(imageLoaded, thumbnailSize, thumbnailSize);
        listImage.add(position, imageLoaded);
        return imageLoaded;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
