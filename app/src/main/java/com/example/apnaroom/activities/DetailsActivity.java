package com.example.apnaroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.AmenitiesAdapter;
import com.example.apnaroom.adapters.GalleryAdapter;
import com.example.apnaroom.databinding.ActivityDetailsBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.example.apnaroom.viewmodel.MainViewmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    AmenitiesAdapter amenitiesAdapter;
    GalleryAdapter galleryAdapter;
    MainViewmodel viewmodel = new MainViewmodel(this);
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Favourite");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        ItemsModel item = (ItemsModel) getIntent().getSerializableExtra("item");

        popBundle(item);

        binding.backBtn.setOnClickListener(v->{
            setBack();
        });

        binding.bookNowBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        binding.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavList(item);
            }
        });

        viewmodel.loadRecommendedData().observe(this, itemsModels -> {
            binding.galleryRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            galleryAdapter = new GalleryAdapter(this, itemsModels);
            binding.galleryRv.setAdapter(galleryAdapter);
        });
    }

    private void addToFavList(ItemsModel item) {
        if (item != null && auth.getCurrentUser() != null){
            String uId = auth.getCurrentUser().getUid();
            String pushKey = databaseReference.child(uId).push().getKey();

            item.setFirebaseKey(pushKey);

            databaseReference.child(uId).push().setValue(item)
                    .addOnSuccessListener(unused -> AndroidUtils.showToast(this, "Added to favourite!"))
                    .addOnFailureListener(e-> AndroidUtils.logError(e.getMessage()));
            binding.favouriteIcon.setVisibility(View.GONE);
            binding.addedToFav.setVisibility(View.VISIBLE);
        }
    }

    private void setBack() {
       finish();
    }

    private void popBundle(ItemsModel item) {
        if (item != null){
            binding.titleText.setText(item.getName());
            binding.descriptionText.setText(item.getDescription());
            binding.selectedItemRatingText.setText("⭐ "+String.valueOf(item.getRating()));
//            binding.ratingBar.setRating((float) item.getRating());
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