package com.example.apnaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.databinding.FragmentBookingDetailsBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.example.apnaroom.viewmodel.MainViewmodel;

import java.util.ArrayList;

public class BookingDetailsFragment extends Fragment {
    private MainViewmodel viewmodel = new MainViewmodel(getContext());
    private FragmentBookingDetailsBinding binding;

    private ArrayList<ItemsModel> bookingList = new ArrayList<>();
    private int currentIndex = 0;

    public BookingDetailsFragment() {
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
        binding = FragmentBookingDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel.loadBookingDetails().observe(getViewLifecycleOwner(), itemsModels -> {
           bookingList = itemsModels;
           currentIndex = 0;
           showBooking(currentIndex);
        });

        binding.backBtn.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });

        binding.forwardBtn.setOnClickListener(v->{
            if (currentIndex < bookingList.size()-1){
                currentIndex++;
                showBooking(currentIndex);
            }
        });

        binding.backwardBtn.setOnClickListener(v->{
            if (currentIndex != 0){
                currentIndex--;
                showBooking(currentIndex);
            }
            if (currentIndex == 0){
                AndroidUtils.showToast(getContext(), "This is first item");
            }
        });
    }

    private void showBooking(int index) {
        if (bookingList != null && index >= 0 && index <= bookingList.size() -1) {
            ItemsModel item = bookingList.get(index);
            binding.BookTitleTxt.setText(item.getName());
            binding.bookLocationTxt.setText(item.getLocation());
            Glide.with(getContext()).load(item.getImage_url()).placeholder(R.drawable.placeholder).into(binding.bookPicView);
        }
    }
}