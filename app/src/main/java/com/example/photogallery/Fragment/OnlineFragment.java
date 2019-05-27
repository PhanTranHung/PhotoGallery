package com.example.photogallery.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.photogallery.Adapters.GridViewAdapterForOnlineFragment;
import com.example.photogallery.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class OnlineFragment extends Fragment {

    GridView gridView;
    GridViewAdapterForOnlineFragment gridViewAdapter;

    private DatabaseReference mDatabase;

    ArrayList<String> dowloadUrlImage;

    String CHILD_NAME = "image";

    public OnlineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        dowloadUrlImage = new ArrayList<>();
        gridViewAdapter = new GridViewAdapterForOnlineFragment(getContext(), R.layout.image_item, dowloadUrlImage);

        getListLinkImgs();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online, container, false);

        gridView = view.findViewById(R.id.grid_view_photo);
        gridView.setAdapter(gridViewAdapter);
        return view;
    }

    void getListLinkImgs(){
        mDatabase.child(CHILD_NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dowloadUrlImage.add(dataSnapshot.getValue().toString());
                gridViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
