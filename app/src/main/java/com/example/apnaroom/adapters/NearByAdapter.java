package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderNearbyBinding;
import com.example.apnaroom.interfaces.OnClickItemListener;

import java.util.ArrayList;

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.NearByViewHolder> implements OnClickItemListener {

    private ArrayList<ItemsModel> neaByList;
    private Context context;

    public NearByAdapter(ArrayList<ItemsModel> neaByList, Context context) {
        this.neaByList = neaByList;
        this.context = context;
    }

    @NonNull
    @Override
    public NearByAdapter.NearByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderNearbyBinding binding = ViewholderNearbyBinding.inflate(LayoutInflater.from(context), parent, false);
        return new NearByViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NearByAdapter.NearByViewHolder holder, int position) {
        ItemsModel item = neaByList.get(position);

        holder.binding.nearByTitle.setText(item.getName());
        Glide.with(context).load(item.getImage_url()).into(holder.binding.nearByPic);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });

    }

    @Override
    public int getItemCount() {
        return neaByList.size();
    }

    @Override
    public void onClick(ItemsModel items) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", items);
        context.startActivity(intent);
    }

    public class NearByViewHolder extends RecyclerView.ViewHolder {
        ViewholderNearbyBinding binding;
        public NearByViewHolder(@NonNull ViewholderNearbyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
