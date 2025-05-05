package com.example.apnaroom.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.R;
import com.example.apnaroom.activities.CategoryListActivity;
import com.example.apnaroom.databinding.ViewholderCatgoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {

    private ArrayList<CategoryModel> categoryItems;
    private Context context;
    private int lastSelectedPosition = -1;
    private int selectedPosition = -1;

    public CategoryAdapter(ArrayList<CategoryModel> categoryItems, Context context) {
        this.categoryItems = categoryItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCatgoryBinding binding = ViewholderCatgoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CatViewHolder holder, int position) {
        CategoryModel item = categoryItems.get(position);
        holder.binding.titleText.setText(item.getTitle());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, CategoryListActivity.class);
//                        intent.putExtra("id", item.getId());
                        intent.putExtra("title", item.getTitle());
                        context.startActivity(intent);
                    }
                }, 500);
            }
        });

        if (selectedPosition == position) {
            holder.binding.titleText.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.titleText.setBackgroundResource(R.drawable.cat_yellow_bg);
        } else {
            holder.binding.titleText.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.binding.titleText.setBackgroundResource(R.drawable.border_bg);
        }
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        ViewholderCatgoryBinding binding;

        public CatViewHolder(@NonNull ViewholderCatgoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
