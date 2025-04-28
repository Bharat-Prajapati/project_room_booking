package com.example.apnaroom.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.BannerAdapter;
import com.example.apnaroom.adapters.CategoryAdapter;
import com.example.apnaroom.adapters.PopularAdapter;
import com.example.apnaroom.adapters.RecommendedAdapter;
import com.example.apnaroom.databinding.ActivityMainBinding;
import com.example.apnaroom.viewmodel.MainViewmodel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BannerAdapter bannerAdapter;
    CategoryAdapter categoryAdapter;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    private MainViewmodel viewmodel = new MainViewmodel(this);
    Handler handler = new Handler();
    Runnable runnable;
    private int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setBanner();
        setCategory();
        setPopular();
        setRecommended();

    }

    private void setBanner() {
        binding.banProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadBannerData().observe(this, itemList->{
            binding.banProgressBar.setVisibility(View.GONE);
            bannerAdapter = new BannerAdapter(itemList, this);
            binding.viewBanner.setAdapter(bannerAdapter);

            runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentImage == itemList.size()){
                        currentImage = 0;
                    }
                    binding.viewBanner.setCurrentItem(currentImage++, true);
                    handler.postDelayed(runnable, 2000);
                }
            };
            handler.postDelayed(runnable, 2000);
            binding.viewBanner.setOffscreenPageLimit(3);
        });
    }

    private void setPopular() {
        binding.popProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadPopularData().observe(this, itemsList->{
            binding.popProgressBar.setVisibility(View.GONE);
            binding.popRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            popularAdapter = new PopularAdapter(this, itemsList);
            binding.popRecyclerView.setAdapter(popularAdapter);
        });
    }

    private void setCategory() {
        binding.catProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadCategoryData().observe(this, itemsList->{
            binding.catProgressBar.setVisibility(View.GONE);
            binding.catRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            categoryAdapter = new CategoryAdapter(itemsList, this);
            binding.catRecyclerView.setAdapter(categoryAdapter);
        });
    }

    private void setRecommended() {
        binding.recProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadRecommendedData().observe(this, itemList->{
            binding.recProgressBar.setVisibility(View.GONE);
            binding.recRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recommendedAdapter = new RecommendedAdapter(this, itemList);
            binding.recRecyclerView.setAdapter(recommendedAdapter);

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }
}