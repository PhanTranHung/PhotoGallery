package com.example.photogallery;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.photogallery.Adapters.TabImagesAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailsActivity<V> extends AppCompatActivity {

    ArrayList listElements;
    RelativeLayout toolBarTop;
    LinearLayout toolBarBottom;
    ImageButton backBtn, optionBtn, editBtn, shareBtn, deleteBtn, uploadBtn;
    ViewPager tabImage;

    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    boolean isVisible = true;
    int position;

    String CHILD_IMAGE = "image";
    String ACTION_CAN_UPLOAD = "OK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listElements = (ArrayList) getIntent().getSerializableExtra("listFiles");
        position = getIntent().getIntExtra("position", 0);

        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        bin();
        canUploaded(getIntent().getAction());
    }

    private void canUploaded(String action){
        if (action != ACTION_CAN_UPLOAD)
            hidentBtnUpload();
    }

    private void hidentBtnUpload(){
        uploadBtn.setVisibility(View.INVISIBLE);
    }

    void bin(){
        mapViews();

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

        tabImage.setAdapter(new TabImagesAdapter(this, listElements));
        tabImage.setCurrentItem(position, true);

        tabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibleToolBars();
                Toast.makeText(getBaseContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgToFirebase(listElements.get(tabImage.getCurrentItem()).toString());
            }
        });
    }

    void mapViews(){
        backBtn = findViewById(R.id.button_back);
        optionBtn = findViewById(R.id.button_option);
        editBtn = findViewById(R.id.button_edit);
        shareBtn = findViewById(R.id.button_share);
        deleteBtn = findViewById(R.id.button_delete);
        tabImage = findViewById(R.id.tab_images);
        uploadBtn = findViewById(R.id.imageButtonUpload);
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

    void uploadImgToFirebase(String path){

        Uri file = Uri.fromFile(new File(path));
        Log.i("ssssgetLastPathSegment", file.getLastPathSegment());
        final StorageReference riversRef = storageReference.child("images/" + Calendar.getInstance().getTimeInMillis() + file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ssssonFailure", exception.getMessage());
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                taskSnapshot.getMetadata();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mDatabase.child(CHILD_IMAGE).push().setValue(uri.toString(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError != null){
                                    Toast.makeText(DetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetailsActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
