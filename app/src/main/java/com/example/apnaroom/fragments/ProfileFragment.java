package com.example.apnaroom.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apnaroom.R;
import com.example.apnaroom.activities.LoginActivity;
import com.example.apnaroom.activities.MainActivity;
import com.example.apnaroom.databinding.FragmentProfileBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.example.apnaroom.viewmodel.MainViewmodel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.Result;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private final int GALLERY_REQ_CODE = 100;
    private final int CAMERA_REQ_CODE = 101;
    private MainViewmodel viewmodel = new MainViewmodel(getContext());
    private AlertDialog dialog;
    private SweetAlertDialog authDialog;

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

        binding.logoutLayout.setOnClickListener(v->{
            logout();
        });

        binding.myBooking.setOnClickListener(v->{
           if (getActivity() instanceof MainActivity){
               ((MainActivity) getActivity()).loadFragment(new BookingDetailsFragment());
           }
        });

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), binding.addImage);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_items, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.gallery){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALLERY_REQ_CODE);
                        }
                        else if (itemId == R.id.camera){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQ_CODE);
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == GALLERY_REQ_CODE) {
                Uri imageUri = data.getData();
                binding.profilePicView.setImageURI(imageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    uploadImageToDatabase(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (requestCode == CAMERA_REQ_CODE) {
                Bundle extra = data.getExtras();
                if (extra != null){
                    Bitmap bitmap = (Bitmap) extra.get("data");
                    binding.profilePicView.setImageBitmap(bitmap);
                    uploadImageToDatabase(bitmap);
                }
            }
        }
        else {
            Toast.makeText(requireContext(), "Data is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToDatabase(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] imageBytes = out.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        ref.child("photoUrl").setValue(base64String);
    }

    private void setUpUserDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            showAuthDialog();
            return;
        }
        String userId = currentUser.getUid();
        Log.d("userId", userId + " "+currentUser);

        if (currentUser != null) {
            Uri photoUrl = currentUser.getPhotoUrl();
            String userName = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();

            if (photoUrl != null && userEmail != null && userName != null) {
                Glide.with(getContext()).load(photoUrl).placeholder(R.drawable.ic_account_24).into(binding.profilePicView);
                binding.usernameTxt.setText(userName);
                binding.userEmailTxt.setText(userEmail);
            }

//                loadBitmapFormRealtimeDatabase(bitmap -> {
//                    if (bitmap != null) {
//                        Glide.with(getContext()).load(bitmap).placeholder(R.drawable.placeholder).into(binding.profilePicView);
//                    }
//                });
        }

        viewmodel.loadUserDetails(userId).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String username = user.getName();
                String email = user.getEmail();
                Bitmap photo = loadBitmapFormRealtimeDatabase();
                Log.d("photo", photo+" ");
                if (username != null && email != null) {
                    binding.userEmailTxt.setText(email);
                    binding.usernameTxt.setText(username);
//                Glide.with(getContext()).load(photo).placeholder(R.drawable.placeholder).into(binding.profilePicView);
                }
            }
            else {
                showAuthDialog();
            }
        });

    }

    private void showAuthDialog() {
        authDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
        authDialog.setTitleText("Authentication Error");
        authDialog.show();
        authDialog.setConfirmButton("Login", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                authDialog.dismiss();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private Bitmap loadBitmapFormRealtimeDatabase() {
        final Bitmap[] bitmap = new Bitmap[1];
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("photoUrl");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String base64String = snapshot.getValue(String.class);
                if (base64String != null){
                    byte[] imageBitmap = Base64.decode(base64String, Base64.DEFAULT);
                    bitmap[0] = BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap.length);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return bitmap[0];
    }

    private void logout() {
        GoogleSignInClient client = GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());

        dialog = new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.logout)
                .setMessage("Are you sure you want to log out")
                .setPositiveButton("Yes", (dialog1, which) -> client.signOut().addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Sign out successful", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
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