package com.example.photogallery.Loading;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.photogallery.R;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    int REQUEST_CODE_READ_EXTERNAL_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);   //error
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_READ_EXTERNAL_STORAGE);

        bin();
        checkRequestPermissions();
    }

    void bin(){
        progressBar = findViewById(R.id.progressBar);
        textView    = findViewById(R.id.text_view_loaded);
    }

    void checkRequestPermissions(){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            LoadingResource load = new LoadingResource(this);
            load.execute();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE){
            if (grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoadingResource load = new LoadingResource(this);
                    load.execute();
                }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_READ_EXTERNAL_STORAGE);
                    }else{
                        closeNow();
                    }
                }
            }
        }
    }

    private void closeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }
}
