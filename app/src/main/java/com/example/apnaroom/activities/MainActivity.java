package com.example.apnaroom.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.CategoryModel;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.BannerAdapter;
import com.example.apnaroom.adapters.CategoryAdapter;
import com.example.apnaroom.adapters.PopularAdapter;
import com.example.apnaroom.adapters.RecommendedAdapter;
import com.example.apnaroom.databinding.ActivityMainBinding;
import com.example.apnaroom.fragments.FavouriteFragment;
import com.example.apnaroom.fragments.HomeFragment;
import com.example.apnaroom.fragments.ProfileFragment;
import com.example.apnaroom.viewmodel.MainViewmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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

        loadFragment(new HomeFragment());

        binding.bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                int tabId = newTab.getId();

               if (tabId == R.id.favourite){
                   loadFragment(new FavouriteFragment());
               } else if (tabId == R.id.home) {
                   loadFragment(new HomeFragment());
               }else if (tabId == R.id.profile){
                   loadFragment(new ProfileFragment());
               }
               else {
                   Intent intent = new Intent(Settings.ACTION_SETTINGS);
                   startActivity(intent);
               }

            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment)
                .commit();
    }



}