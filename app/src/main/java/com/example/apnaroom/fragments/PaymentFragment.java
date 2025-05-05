package com.example.apnaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apnaroom.Domains.ItemsModel;
import com.example.apnaroom.R;
import com.example.apnaroom.databinding.FragmentPaymentBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentFragment extends Fragment {
FragmentPaymentBinding binding;

    public PaymentFragment() {
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
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemsModel item = (ItemsModel) getActivity().getIntent().getSerializableExtra("item");

        if (item != null) {
            binding.totalPrice.setText("Rs."+item.getPrice_per_night());
        }

        binding.backBtn.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.bookRoomBtn.setOnClickListener(v->{
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String uId = currentUser.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Booking");

            reference.child(uId).push().setValue(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Room booked. Thank you!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e-> AndroidUtils.logError(e.getMessage()));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}