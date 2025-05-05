package com.example.apnaroom.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apnaroom.R;
import com.example.apnaroom.activities.BookingActivity;
import com.example.apnaroom.activities.DetailsActivity;
import com.example.apnaroom.activities.LoginActivity;
import com.example.apnaroom.activities.MainActivity;
import com.example.apnaroom.databinding.FragmentProfileBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    public ProfileFragment() {
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUserDetails();

        binding.logout.setOnClickListener(v->{
            logout();
        });

        binding.myBooking.setOnClickListener(v->{
           if (getActivity() instanceof MainActivity){
               ((MainActivity) getActivity()).loadFragment(new BookingDetailsFragment());
           }
        });
    }

    private void setUpUserDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            Uri photoUrl = currentUser.getPhotoUrl();
            String userName = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();

            if (photoUrl != null && userEmail != null && userName != null) {
                Glide.with(getContext()).load(photoUrl).placeholder(R.drawable.ic_account_24).into(binding.profilePicView);
                binding.usernameTxt.setText(userName);
                binding.userEmailTxt.setText(userEmail);
            }
        }
    }

    private void logout() {
        GoogleSignInClient client = GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_warning_24)
                .setMessage("Are you sure you want to log out")
                .setPositiveButton("Yes", (dialog1, which) -> client.signOut().addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Sign out successful", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }).addOnFailureListener(e-> AndroidUtils.showToast(getContext(), e.getMessage())))
                .setNeutralButton("No", (dialog2, which) -> dialog2.cancel()).show();
        dialog.setCancelable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}