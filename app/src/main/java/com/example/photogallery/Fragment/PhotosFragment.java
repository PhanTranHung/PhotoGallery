package com.example.photogallery.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.photogallery.Adapters.GridViewAdapter;
import com.example.photogallery.Adapters.ImagesAdapter;
import com.example.photogallery.DetailsActivity;
import com.example.photogallery.MainActivity;
import com.example.photogallery.R;

import java.io.File;
import java.util.ArrayList;

public class PhotosFragment extends Fragment {
    ArrayList<File> listFile;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    ImagesAdapter imagesAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        listFile = mainActivity.getListFiles();
//        imagesAdapter = new ImagesAdapter(listImages);
        gridViewAdapter = new GridViewAdapter(getContext(), R.layout.image_item, listFile);
        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//        recyclerView = view.findViewById(R.id.recycler_view_photos);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(imagesAdapter);

        gridView = view.findViewById(R.id.grid_view_photo);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file= (File) parent.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("listFiles", listFile);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return view;
    }
}
