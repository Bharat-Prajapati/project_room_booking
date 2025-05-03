package com.example.apnaroom.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.apnaroom.R;
import com.example.apnaroom.adapters.BannerAdapter;
import com.example.apnaroom.adapters.CategoryAdapter;
import com.example.apnaroom.adapters.PopularAdapter;
import com.example.apnaroom.adapters.RecommendedAdapter;
import com.example.apnaroom.databinding.FragmentHomeBinding;
import com.example.apnaroom.viewmodel.MainViewmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    BannerAdapter bannerAdapter;
    CategoryAdapter categoryAdapter;
    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    private MainViewmodel viewmodel = new MainViewmodel(getContext());
    Handler handler = new Handler();
    Runnable runnable;
    private int currentImage = 0;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBanner();
        setCategory();
        setPopular();
        setRecommended();
        setUpUserDetails();
    }

    private void setUpUserDetails() {
        FirebaseUser user = auth.getCurrentUser();
        Uri photoUrl = user.getPhotoUrl();
        String username = user.getDisplayName();

        if (user != null && user.getPhotoUrl() != null){
            Glide.with(this).load(photoUrl).placeholder(R.drawable.ic_account_24).into(binding.userPic);
        }
        if (user != null && username != null)
            binding.userNameText.setText("Hello, "+username+"!");
    }

    private void setBanner() {
        binding.banProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadBannerData().observe(getActivity(), itemList->{
            binding.banProgressBar.setVisibility(View.GONE);
            bannerAdapter = new BannerAdapter(itemList, getContext());
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
        });
    }

    private void setPopular() {
        binding.popProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadPopularData().observe(getActivity(), itemsList->{
            binding.popProgressBar.setVisibility(View.GONE);
            binding.popRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            popularAdapter = new PopularAdapter(getContext(), itemsList);
            binding.popRecyclerView.setAdapter(popularAdapter);
        });
    }

    private void setCategory    () {
        binding.catProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadCategoryData().observe(getActivity(), itemsList->{
            binding.catProgressBar.setVisibility(View.GONE);
            binding.catRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            categoryAdapter = new CategoryAdapter(itemsList, getContext());
            binding.catRecyclerView.setAdapter(categoryAdapter);
        });
    }

    private void setRecommended() {
        binding.recProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadRecommendedData().observe(getActivity(), itemList->{
            binding.recProgressBar.setVisibility(View.GONE);
            binding.recRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recommendedAdapter = new RecommendedAdapter(getContext(), itemList);
            binding.recRecyclerView.setAdapter(recommendedAdapter);
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}