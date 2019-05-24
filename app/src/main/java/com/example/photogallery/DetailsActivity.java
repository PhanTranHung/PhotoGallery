package com.example.photogallery;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.photogallery.Adapters.TabImagesAdapter;

import java.io.File;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ImageButton backBtn, optionBtn, editBtn, shareBtn, deleteBtn;
    ViewPager tabImage;
    int position;
    ArrayList<File> listFile;
    RelativeLayout toolBarTop;
    LinearLayout toolBarBottom;
    boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        listFile = (ArrayList<File>) getIntent().getSerializableExtra("listFiles");
        position = getIntent().getIntExtra("position", 0);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        bin();
    }

    void bin(){
        backBtn = findViewById(R.id.button_back);
        optionBtn = findViewById(R.id.button_option);
        editBtn = findViewById(R.id.button_edit);
        shareBtn = findViewById(R.id.button_share);
        deleteBtn = findViewById(R.id.button_delete);
        tabImage = findViewById(R.id.tab_images);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "OPTION", Toast.LENGTH_SHORT).show();
            }
        });

        tabImage.setAdapter(new TabImagesAdapter(this, listFile));
        tabImage.setCurrentItem(position, true);

        tabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleToolBars();
                Toast.makeText(getBaseContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getBaseContext(), "click", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

    void setVisibleToolBars(){
        if(isVisible){
            toolBarTop.setVisibility(View.INVISIBLE);
            toolBarBottom.setVisibility(View.INVISIBLE);
        } else {
            toolBarTop.setVisibility(View.VISIBLE);
            toolBarBottom.setVisibility(View.VISIBLE);
        }
    }
}
