package com.example.photogallery.Adapters;

import android.net.Uri;

import java.io.File;
import java.io.FileReader;

public class UriC {

    public static <V> Uri getUri(V fileOrPath){
        return fileOrPath.equals(FileReader.class) ? Uri.fromFile((File) fileOrPath) : Uri.parse(fileOrPath.toString());
    }
}
