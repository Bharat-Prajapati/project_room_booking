package com.example.apnaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.activities.BookingActivity;
import com.example.apnaroom.databinding.FragmentPersonalInfoBinding;

public class PersonalInfoFragment extends Fragment {

    FragmentPersonalInfoBinding binding;

    public PersonalInfoFragment() {
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
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.continueBtn.setOnClickListener(v->{
//            FragmentManager fm = getParentFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.personal_frag_container, new PaymentFragment())
//                    .addToBackStack(null)
//                    .commit();
            if (getActivity() instanceof BookingActivity){
                ((BookingActivity) getActivity()).loadFragment(new PaymentFragment());
            }
        });

        ItemsModel item = (ItemsModel) getActivity().getIntent().getSerializableExtra("item");

        if (item != null) {
            binding.priceText.setText("$"+item.getPrice_per_night()+"/night");
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}