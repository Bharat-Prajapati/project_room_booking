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
import com.example.apnaroom.databinding.ViewholderSelectedCatListBinding;
import com.example.apnaroom.interfaces.OnClickItemListener;

import java.util.ArrayList;

public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.CsViewHolder> implements OnClickItemListener {
    private Context context;
    private ArrayList<ItemsModel> itemList;

    public SelectedCategoryAdapter(Context context, ArrayList<ItemsModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SelectedCategoryAdapter.CsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderSelectedCatListBinding binding = ViewholderSelectedCatListBinding.inflate(LayoutInflater.from(context), parent,false);
        return new CsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCategoryAdapter.CsViewHolder holder, int position) {
        ItemsModel item = itemList.get(position);
        holder.binding.selectedItemTitle.setText(item.getName());
        holder.binding.selectedItemLocation.setText(item.getLocation());
        holder.binding.selectedItemPrice.setText("$ "+String.valueOf(item.getPrice_per_night())+"/night");
        holder.binding.selectedItemRatingText.setText("⭐"+String.valueOf(item.getRating()));
//        holder.binding.selectedItemReviews.setText(item.getReviews()+" reviews");
//        holder.binding.selectedItemPrice.setText("$ "+String.valueOf(item.getPrice_per_month()));

        Glide.with(context).load(item.getImage_url()).into(holder.binding.selectedItemPic);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(ArrayList<ItemsModel> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(ItemsModel itemsModel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", itemsModel);
        context.startActivity(intent);
    }

    public class CsViewHolder extends RecyclerView.ViewHolder {
        ViewholderSelectedCatListBinding binding;

        public CsViewHolder(@NonNull ViewholderSelectedCatListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
