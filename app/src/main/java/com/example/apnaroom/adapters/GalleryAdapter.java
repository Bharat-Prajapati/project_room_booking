package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderGalleryBinding;
import com.example.apnaroom.fragments.GalleryFragment;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ItemsModel> galleryList;

    public GalleryAdapter(Context context, ArrayList<ItemsModel> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderGalleryBinding binding = ViewholderGalleryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemsModel photo = galleryList.get(position);

        if (photo != null){
            Glide.with(context).load(photo.getImage_url()).into(holder.binding.galleryView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imageUrl", photo.getImage_url());

                    GalleryFragment galleryFragment = new GalleryFragment();
                    galleryFragment.setArguments(bundle);

                    ((DetailsActivity)context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.DetailsMain, galleryFragment)
                            .commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewholderGalleryBinding binding;
        public MyViewHolder(@NonNull ViewholderGalleryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
