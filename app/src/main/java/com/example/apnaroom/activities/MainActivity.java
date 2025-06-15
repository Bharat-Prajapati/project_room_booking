package com.example.apnaroom.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.apnaroom.R;
import com.example.apnaroom.databinding.ActivityMainBinding;
import com.example.apnaroom.databinding.LockedDialogBinding;
import com.example.apnaroom.fragments.BookingDetailsFragment;
import com.example.apnaroom.fragments.FavouriteFragment;
import com.example.apnaroom.fragments.HomeFragment;
import com.example.apnaroom.fragments.ProfileFragment;

import java.util.concurrent.Executor;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private long againPressTime;
    private boolean launchedEnrollmentIntent = false;
    private SweetAlertDialog enrollDialog;

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

        BiometricManager biometricManager = BiometricManager.from(this);
        int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);
        if(canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS){
            showFingerPrintPrompt();
        } else if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            Toast.makeText(this, "Fingerprint feature is not available on this device", Toast.LENGTH_SHORT).show();
        } else if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE) {
            Toast.makeText(this, "This device does not support fingerprint feature", Toast.LENGTH_SHORT).show();
        } else if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
//            enrollDialogShow();
            showFingerPrintPrompt();
        }

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
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragmentContainerView, fragment)
                .commit();

        if (fragment instanceof BookingDetailsFragment){
            transaction.addToBackStack(null);
        }
    }

    public void showFingerPrintPrompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_CANCELED ||
                    errorCode == BiometricPrompt.ERROR_USER_CANCELED ||
                    errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setTitle("Booking app is locked");
                    dialog.setCancelable(false);
                    LockedDialogBinding lockedDialogBinding = LockedDialogBinding.inflate(getLayoutInflater());
                    dialog.setContentView(lockedDialogBinding.getRoot());
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();

                    lockedDialogBinding.unlockNowText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            showFingerPrintPrompt();
                        }
                    });
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock Booking App")
                .setSubtitle("Enter phone screen lock pattern, Pin, password or fingerprint")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL | BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void enrollDialogShow(){
        enrollDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        enrollDialog.setTitleText("Please enroll FingerPrint");
        enrollDialog.setCancelable(false);
        enrollDialog.show();

        enrollDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                launchedEnrollmentIntent = true;
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                intent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BiometricManager.Authenticators.BIOMETRIC_STRONG);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (launchedEnrollmentIntent){
            int canAuthenticate = BiometricManager.Authenticators.BIOMETRIC_STRONG;

            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS){
                launchedEnrollmentIntent = false;
                enrollDialog.dismiss();
                showFingerPrintPrompt();
            }
            else if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED){
                if (enrollDialog != null && enrollDialog.isShowing()){
                    enrollDialog.dismiss();
                }
                enrollDialogShow();
            }
            else {
                enrollDialog.dismiss();
                showFingerPrintPrompt();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragmentContainerView);
        fm.popBackStack();

        if (currentFragment instanceof HomeFragment || currentFragment instanceof FavouriteFragment || currentFragment instanceof ProfileFragment){
            if (System.currentTimeMillis() - againPressTime <= 2000){
                super.onBackPressed();
                finishAffinity();
            }else {
                againPressTime = System.currentTimeMillis();
                Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
            }
        }
    }
}