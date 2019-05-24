package com.example.photogallery.Loading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

import com.example.photogallery.ImageHolder;

public class LoadingImageUseThread extends Thread {
    ImageHolder imageHolder;
    ImageView imageView;

    public LoadingImageUseThread(ImageHolder imageHolder, ImageView imageView) {
        this.imageHolder = imageHolder;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        if (imageHolder.getThumbnail() == null){
            int thumbnailSize = 200;
            Bitmap imageLoaded = BitmapFactory.decodeFile(imageHolder.getFile().getAbsolutePath());
            imageLoaded = ThumbnailUtils.extractThumbnail(imageLoaded, thumbnailSize, thumbnailSize);
            imageHolder.setThumbnail(imageLoaded);
        }
        imageView.setImageBitmap(imageHolder.getThumbnail());
    }
}
