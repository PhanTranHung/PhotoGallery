package com.example.photogallery.Loading;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.photogallery.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class LoadingResource extends AsyncTask<Void, Integer, ArrayList> {

    Context context;
    ArrayList<File> arrayFiles;
    ArrayList<File> arrayAlbums;


    public LoadingResource(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        arrayFiles = new ArrayList<>();
        arrayAlbums = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(ArrayList s) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("listFiles", arrayFiles);
        intent.putExtra("listAlbums", arrayAlbums);
        context.startActivity(intent);
    }

    @Override
    protected ArrayList doInBackground(Void... strings) {
        listFile(Environment.getExternalStorageDirectory());
        return null;
    }

    public void listFile(File file){
        boolean flag = false;
        for(File child: file.listFiles())
            if (!child.isHidden()) {
                if (child.isDirectory()) {
                    listFile(child);
                } else if (child.getName().trim().endsWith(".jpg") || child.getName().trim().endsWith(".png")) {
                    arrayFiles.add(child);
                    if (!flag) {
                        arrayAlbums.add(file);
                        flag = true;
                    }
                }
            }
    }

    public static ArrayList listFileImage (File file){
        ArrayList<File> list = new ArrayList<>();
        for(File child: file.listFiles())
            if (!child.isHidden()) {
                if (child.getName().trim().endsWith(".jpg") || child.getName().trim().endsWith(".png")) {
                    list.add(child);
                }
            }
        return list;
    }
}
