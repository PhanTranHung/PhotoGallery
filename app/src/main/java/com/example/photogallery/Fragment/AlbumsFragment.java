package com.example.photogallery.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.photogallery.Adapters.AlbumsAdapter;
import com.example.photogallery.AlbumItem;
import com.example.photogallery.Loading.LoadingResource;
import com.example.photogallery.MainActivity;
import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;

public class AlbumsFragment extends Fragment {

    ArrayList<File> arrayAlbums;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        arrayAlbums = mainActivity.getListAlbums();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        GridView gridView = view.findViewById(R.id.grid_view_album);
        gridView.setAdapter(new AlbumsAdapter(getContext(), R.layout.album_items, arrayAlbums));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) parent.getItemAtPosition(position);
//                Toast.makeText(getContext(), file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AlbumItem.class);
                intent.putExtra("listImages", LoadingResource.listFileImage(file));
                startActivity(intent);
            }
        });
        return view;
    }
}