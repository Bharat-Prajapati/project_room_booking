package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.PopularModel;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderPopularBinding;
import com.example.apnaroom.interfaces.OnClickPopListener;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopViewHolder> implements OnClickPopListener {
    private Context context;
    private ArrayList<PopularModel> popularItems;

    public PopularAdapter(Context context, ArrayList<PopularModel> popularItems) {
        this.context = context;
        this.popularItems = popularItems;
    }

    @NonNull
    @Override
    public PopularAdapter.PopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPopularBinding binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PopViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopViewHolder holder, int position) {
        PopularModel item = popularItems.get(position);
        holder.binding.roomName.setText(item.getRoom_name());
        holder.binding.roomPrice.setText(String.format("$-"+item.getPrice_per_night()));

        Glide.with(context).load(item.getImageUrl()).into(holder.binding.picUrl);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return popularItems.size();
    }

    @Override
    public void onClick(PopularModel items) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", items);
        context.startActivity(intent);
    }

    public class PopViewHolder extends RecyclerView.ViewHolder {
        ViewholderPopularBinding binding;
        public PopViewHolder(@NonNull ViewholderPopularBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}