package com.example.apnaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apnaroom.R;
import com.example.apnaroom.adapters.FavouriteAdapter;
import com.example.apnaroom.databinding.FragmentFavouriteBinding;
import com.example.apnaroom.viewmodel.MainViewmodel;


public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    private FavouriteAdapter favouriteAdapter;
    private MainViewmodel viewmodel = new MainViewmodel(getContext());

    public FavouriteFragment() {
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
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpFavList();

    }

    private void setUpFavList() {
        binding.favProgressBar.setVisibility(View.VISIBLE);
        viewmodel.loadFavData().observe(getActivity(), itemList->{
            binding.favProgressBar.setVisibility(View.GONE);
            binding.rvFavList.setLayoutManager(new LinearLayoutManager(getContext()));
            favouriteAdapter = new FavouriteAdapter(itemList, getContext());
            binding.rvFavList.setAdapter(favouriteAdapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}