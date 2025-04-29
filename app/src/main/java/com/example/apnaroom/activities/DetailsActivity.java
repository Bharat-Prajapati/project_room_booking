package com.example.apnaroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
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

        binding.bookNowBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, PersonalInfoActivity.class);
            startActivity(intent);
        });

    }

    private void setBack() {
       finish();
    }


    private void popBundle() {
        ItemsModel item = (ItemsModel) getIntent().getSerializableExtra("item");

        if (item != null){
            binding.titleText.setText(item.getName());
            binding.descriptionText.setText(item.getDescription());
            binding.selectedItemRatingText.setText("⭐ "+String.valueOf(item.getRating()));
            binding.ratingBar.setRating((float) item.getRating());
            binding.locationText.setText(item.getLocation());
            binding.reviewsText.setText("{"+item.getReviews()+" Reviews}");
            binding.pricePerNight.setText("$" + String.valueOf(item.getPrice_per_night())+"/Night");
            Glide.with(this).load(item.getImage_url()).into(binding.roomPic);
            setAmenities(item.getAmenities());
        }
    }

    private void setAmenities(ArrayList<String> amenities){
        binding.progressBar.setVisibility(View.GONE);
        binding.rvAmenities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        amenitiesAdapter = new AmenitiesAdapter(this, amenities);
        binding.rvAmenities.setAdapter(amenitiesAdapter);
    }
}