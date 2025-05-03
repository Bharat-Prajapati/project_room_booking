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
import com.example.apnaroom.databinding.ViewholderFavListBinding;
import com.example.apnaroom.interfaces.OnClickItemListener;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavViewHolder> implements OnClickItemListener {
    private ArrayList<ItemsModel> favList;
    private Context context;

    public FavouriteAdapter(ArrayList<ItemsModel> favList, Context context) {
        this.favList = favList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderFavListBinding binding = ViewholderFavListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new FavViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavViewHolder holder, int position) {
        ItemsModel item = favList.get(position);
        holder.binding.favRoomTitle.setText(item.getName());
        holder.binding.favRoomLocation.setText(item.getLocation());
        holder.binding.favRoomPrice.setText(item.getPrice_per_night()+"/night");

        Glide.with(context).load(item.getImage_url()).into(holder.binding.favPic);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    @Override
    public void onClick(ItemsModel item) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        ViewholderFavListBinding binding;

        public FavViewHolder(@NonNull ViewholderFavListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
