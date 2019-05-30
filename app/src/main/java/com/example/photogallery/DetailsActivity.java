package com.example.photogallery;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class DetailsActivity extends AppCompatActivity {

    ArrayList listElements;
    RelativeLayout toolBarTop;
    LinearLayout toolBarBottom;
    ImageButton backBtn, optionBtn, editBtn, shareBtn, deleteBtn, uploadBtn;
    ViewPager tabImage;
    TextView coordinate;
    ProgressBar progressBarUpload;

    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    boolean isVisible = true;
    int position;
    float xCoordinateActionDown = 0, yCoordinateActionDown = 0;

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    private static int MIN_SWIPE_DISTANCE_FOR_CLICK = 4;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    String CHILD_IMAGE = "image";
    String ACTION_CAN_UPLOAD = "OK";

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        tabImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        setCoordinateActionDown(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        detectAction(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        coordinate.setText("x:" + event.getX() + "  y:" + event.getY());
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Toast.makeText(getBaseContext(), "ACTION_CANCEL", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        Toast.makeText(getBaseContext(), "ACTION_OUTSIDE", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUploadClicked();
                uploadImgToFirebase(listElements.get(tabImage.getCurrentItem()).toString());
            }
        });
    }

    void setCoordinateActionDown(MotionEvent event){
        xCoordinateActionDown = event.getX();
        yCoordinateActionDown = event.getY();
    }

    void detectAction(MotionEvent event){

        float deltaX = event.getX() - xCoordinateActionDown;
        float deltaY = event.getY() - yCoordinateActionDown;

        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        if (deltaXAbs < MIN_SWIPE_DISTANCE_FOR_CLICK) setVisibleToolBars();
        else if (deltaXAbs < MIN_SWIPE_DISTANCE_X && deltaYAbs >= MIN_SWIPE_DISTANCE_Y)
            if (deltaY > 0) finish();
            else Toast.makeText(this, "Swipe to up", Toast.LENGTH_SHORT).show();
    }

    void mapViews(){
        backBtn = findViewById(R.id.button_back);
        optionBtn = findViewById(R.id.button_option);
        editBtn = findViewById(R.id.button_edit);
        shareBtn = findViewById(R.id.button_share);
        deleteBtn = findViewById(R.id.button_delete);
        tabImage = findViewById(R.id.tab_images);
        uploadBtn = findViewById(R.id.imageButtonUpload);
        toolBarTop = findViewById(R.id.tool_bar_top);
        toolBarBottom = findViewById(R.id.tool_bar_bottom);
        coordinate = findViewById(R.id.text_view_coordinate);
        progressBarUpload = findViewById(R.id.progress_bar_upload);
    }

    void setVisibleToolBars(){
        if(isVisible){
            toolBarTop.setVisibility(View.INVISIBLE);
            toolBarBottom.setVisibility(View.INVISIBLE);
            isVisible = false;
        } else {
            toolBarTop.setVisibility(View.VISIBLE);
            toolBarBottom.setVisibility(View.VISIBLE);
            isVisible = true;
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
                                    upLoadSuccessfully();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    void btnUploadClicked(){
        uploadBtn.setVisibility(View.INVISIBLE);
        progressBarUpload .setVisibility(View.VISIBLE);
    }

    void upLoadSuccessfully(){
        Toast.makeText(DetailsActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
        progressBarUpload .setVisibility(View.INVISIBLE);
        uploadBtn.setVisibility(View.VISIBLE);
    }
}
