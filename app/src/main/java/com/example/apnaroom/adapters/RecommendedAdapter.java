package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.RecommendedModel;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.databinding.ViewholderRecommendedBinding;
import com.example.apnaroom.interfaces.OnClickRecListener;
import java.util.ArrayList;


public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecViewHolder> implements OnClickRecListener {
    private Context context;
    private ArrayList<RecommendedModel> recItems;

    public RecommendedAdapter(Context context, ArrayList<RecommendedModel> recItems) {
        this.context = context;
        this.recItems = recItems;
    }
    @NonNull
    @Override
    public RecommendedAdapter.RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderRecommendedBinding binding = ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RecViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.RecViewHolder holder, int position) {
        RecommendedModel item = recItems.get(position);
        holder.binding.roomName.setText(item.getRoom_name());
        holder.binding.roomPrice.setText(String.format("$-"+item.getPrice_per_night()));

        Glide.with(context).load(item.getImageUrl()).into(holder.binding.picUrl);

        holder.itemView.setOnClickListener(v->{
            onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return recItems.size();
    }

    @Override
    public void onClick(RecommendedModel items) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", items);
        context.startActivity(intent);
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        ViewholderRecommendedBinding binding;
        public RecViewHolder(@NonNull ViewholderRecommendedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

