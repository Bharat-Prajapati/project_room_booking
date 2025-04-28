package com.example.apnaroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnaroom.databinding.ViewholderAmenitiesBinding;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.AmeViewHolder> {
    private Context context;
    private ArrayList<String> amenityList;

    public AmenitiesAdapter(Context context, ArrayList<String> amenityList) {
        this.context = context;
        this.amenityList = amenityList;
    }

    @NonNull
    @Override
    public AmenitiesAdapter.AmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderAmenitiesBinding binding = ViewholderAmenitiesBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AmeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesAdapter.AmeViewHolder holder, int position) {
        String amenity = amenityList.get(position);
        holder.binding.amenityText.setText(amenity);
    }

    @Override
    public int getItemCount() {
        return amenityList.size();
    }

    public class AmeViewHolder extends RecyclerView.ViewHolder {
        ViewholderAmenitiesBinding binding;

        public AmeViewHolder(@NonNull ViewholderAmenitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
