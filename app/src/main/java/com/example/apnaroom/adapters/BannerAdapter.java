package com.example.apnaroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.BannerModel;
import com.example.apnaroom.databinding.ViewholderBannerBinding;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private ArrayList<BannerModel> bannerList;
    private Context context;

    public BannerAdapter(ArrayList<BannerModel> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerAdapter.BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderBannerBinding binding = ViewholderBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.BannerViewHolder holder, int position) {
        BannerModel item = bannerList.get(position);
        Glide.with(context).load(item.getImageUrl()).into(holder.binding.bannerImageview);
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ViewholderBannerBinding binding;
        public BannerViewHolder(@NonNull ViewholderBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
