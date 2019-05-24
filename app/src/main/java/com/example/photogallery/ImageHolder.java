package com.example.photogallery;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;

public class ImageHolder implements Serializable {
    File file;
    Bitmap thumbnail;

    public ImageHolder(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
