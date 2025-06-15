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
import com.example.apnaroom.R;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderMostUniqueBinding;
import com.example.apnaroom.databinding.ViewholderSelectedCatListBinding;
import com.example.apnaroom.interfaces.OnClickItemListener;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> implements OnClickItemListener {
    private ArrayList<ItemsModel> uniqueList;
    private Context context;

    public ViewPagerAdapter(ArrayList<ItemsModel> uniqueList, Context context) {
        this.uniqueList = uniqueList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderMostUniqueBinding binding = ViewholderMostUniqueBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.MyViewHolder holder, int position) {
        ItemsModel item = uniqueList.get(position);
        holder.binding.selectedItemReviews.setText(item.getReviews()+" reviews");
        holder.binding.selectedItemLocation.setText(item.getLocation());
        holder.binding.selectedItemTitle.setText(item.getName());
        holder.binding.selectedItemPrice.setText("$ "+item.getPrice_per_night());
        holder.binding.selectedItemRatingText.setText("⭐"+item.getRating());
        Glide.with(context).load(item.getImage_url()).placeholder(R.drawable.placeholder).into(holder.binding.selectedItemPic);

        holder.itemView.setOnClickListener(v->{
                onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return uniqueList.size();
    }

    @Override
    public void onClick(ItemsModel items) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", items);
        context.startActivity(intent);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ViewholderMostUniqueBinding binding;
        public MyViewHolder(@NonNull ViewholderMostUniqueBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
