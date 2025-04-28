package com.example.apnaroom.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.PopularModel;
import com.example.apnaroom.Domains.RecommendedModel;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.AmenitiesAdapter;
import com.example.apnaroom.databinding.ActivityDetailsBinding;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    AmenitiesAdapter amenitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        popBundle();
        binding.backBtn.setOnClickListener(v->{
            setBack();
        });

    }

    private void setBack() {
       finish();
    }


    private void popBundle() {
        PopularModel item = (PopularModel) getIntent().getSerializableExtra("item");

        if (item != null){
            binding.titleText.setText(item.getRoom_name());
            binding.descriptionText.setText(item.getDescription());

            Glide.with(this).load(item.getImageUrl()).into(binding.roomPic);
            setAmenities(item.getAmenities());
        }
    }

    private void setAmenities(ArrayList<String> amenities){
        binding.rvAmenities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        amenitiesAdapter = new AmenitiesAdapter(this, amenities);
        binding.rvAmenities.setAdapter(amenitiesAdapter);
    }
}