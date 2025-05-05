package com.example.apnaroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apnaroom.R;
import com.example.apnaroom.databinding.ActivityLoginBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import kotlin.Result;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initFirebase();

        binding.newAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loginCoverView.setVisibility(View.VISIBLE);
                binding.loginProgressBar.setVisibility(View.VISIBLE);
                String email = binding.loginEmailText.getText().toString();
                String password = binding.loginPassText.getText().toString();

                signWithEmailAndPassword(email, password);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleImg.setOnClickListener(v->{
            signInIntent();
        });
    }

    private void signInIntent() {
        Intent singInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(singInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == RC_SIGN_IN){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    if (account == null){
                        return;
                    }
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    AndroidUtils.logError("Google sign in failed " + e.getMessage());
                    AndroidUtils.showToast(this, "Google sign in failed");
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        AndroidUtils.customTag("Token", idToken);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                binding.loginProgressBar.setVisibility(View.GONE);
                binding.loginCoverView.setVisibility(View.GONE);
                FirebaseUser currentUser = auth.getCurrentUser();
                AndroidUtils.showToast(LoginActivity.this, "Sign in successful " + currentUser.getDisplayName());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            else {
                AndroidUtils.showToast(LoginActivity.this, "Authentication Failed");
                binding.loginProgressBar.setVisibility(View.GONE);
                binding.loginCoverView.setVisibility(View.GONE);
            }
        });
    }

    private void signWithEmailAndPassword(String email, String password) {
        if (email.isEmpty()){
            binding.loginProgressBar.setVisibility(View.GONE);
            binding.loginCoverView.setVisibility(View.GONE);
            binding.loginEmailText.setError("Please fill email");
        }
        else if (password.isEmpty()){
            binding.loginProgressBar.setVisibility(View.GONE);
            binding.loginCoverView.setVisibility(View.GONE);
            binding.loginPassText.setError("Please fill password");
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this::onCompleteLogin)
                    .addOnFailureListener(error -> {
                        binding.loginProgressBar.setVisibility(View.GONE);
                        binding.loginCoverView.setVisibility(View.GONE);
                        AndroidUtils.showToast(this, error.getMessage());
                    });
        }
    }

    private void onCompleteLogin(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()){
            binding.loginProgressBar.setVisibility(View.GONE);
            binding.loginCoverView.setVisibility(View.GONE);
            AndroidUtils.showToast(this, "Login successful");
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }
        else {
            binding.loginCoverView.setVisibility(View.GONE);
            binding.loginProgressBar.setVisibility(View.GONE);
        }
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}