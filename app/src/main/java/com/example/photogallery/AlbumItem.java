package com.example.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.photogallery.Adapters.GridViewAdapter;

import java.io.File;
import java.util.ArrayList;

public class AlbumItem extends AppCompatActivity {

    ArrayList<File> listImages;
    GridViewAdapter gridViewAdapter;
    GridView gridView;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_item);
        listImages = (ArrayList<File>) getIntent().getSerializableExtra("listImages");
        gridViewAdapter = new GridViewAdapter(this, R.layout.image_item, listImages);

        bin();

        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file= (File) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("listFiles", listImages);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    void bin() {
        backBtn = findViewById(R.id.button_back);
        gridView = findViewById(R.id.grid_view_photo);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
