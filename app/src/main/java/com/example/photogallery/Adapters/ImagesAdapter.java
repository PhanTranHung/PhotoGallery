package com.example.photogallery.Adapters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.photogallery.ImageHolder;
import com.example.photogallery.Loading.LoadingImage;
import com.example.photogallery.R;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesAdapterViewHolder>{

    ArrayList<Bitmap> listImages;

    public ImagesAdapter(ArrayList<Bitmap> listImages) {
        this.listImages = listImages;
    }

    @NonNull
    @Override
    public ImagesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.image_item, viewGroup, false);
        return new ImagesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapterViewHolder holder, int position) {
        holder.imageItemView.setImageResource(R.drawable.blankimg);
        new LoadingImage(listImages, holder.imageItemView).execute();
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public class ImagesAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageItemView;
        public ImagesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItemView = itemView.findViewById(R.id.img_item);
        }
    }

    public class LayoutManager extends RecyclerView.LayoutManager{

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
    }

}
