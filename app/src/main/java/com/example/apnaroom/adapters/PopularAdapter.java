package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderPopularBinding;
import com.example.apnaroom.databinding.ViewholderRecommendedBinding;
import com.example.apnaroom.interfaces.OnClickItemListener;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopViewHolder> implements OnClickItemListener {
    private Context context;
    private ArrayList<ItemsModel> popularItems;

    public PopularAdapter(Context context, ArrayList<ItemsModel> popularItems) {
        this.context = context;
        this.popularItems = popularItems;
    }

    @NonNull
    @Override
    public PopularAdapter.PopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderRecommendedBinding binding = ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PopViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopViewHolder holder, int position) {
        ItemsModel item = popularItems.get(position);

        holder.binding.roomName.setText(item.getName());
        holder.binding.roomLocation.setText(item.getLocation());
        holder.binding.roomPrice.setText(String.format("$-"+item.getPrice_per_night()));

        Glide.with(context).load(item.getImage_url()).into(holder.binding.picUrl);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return popularItems.size();
    }

    @Override
    public void onClick(ItemsModel items) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", items);
        context.startActivity(intent);
    }

    public class PopViewHolder extends RecyclerView.ViewHolder {
        ViewholderRecommendedBinding binding;
        public PopViewHolder(@NonNull ViewholderRecommendedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}