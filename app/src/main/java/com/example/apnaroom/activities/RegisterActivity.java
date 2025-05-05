package com.example.apnaroom.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apnaroom.Domains.Users;
import com.example.apnaroom.R;
import com.example.apnaroom.databinding.ActivityRegisterBinding;
import com.example.apnaroom.utills.AndroidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initFirebase();

        binding.dobText.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    String dob = selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear;
                    binding.dobText.setText(dob);
                }
            }, year, month, day);

            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        binding.registerBtn.setOnClickListener(v->{
            String email = binding.regEmailText.getText().toString();
            String password = binding.regPassText.getText().toString();
            String name = binding.nameText.getText().toString();
            String dob = binding.dobText.getText().toString();

            binding.regCoverView.setVisibility(View.VISIBLE);
            binding.regProgressBar.setVisibility(View.VISIBLE);

            if (validateInput(name, email, password, dob)) {
                registerWithEmailAndPassword(email, password, name, dob);
            }
        });
    }

    private boolean validateInput(String name, String email, String password, String dob) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()){
            binding.regProgressBar.setVisibility(View.GONE);
            binding.regCoverView.setVisibility(View.GONE);
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            binding.regProgressBar.setVisibility(View.GONE);
            binding.regCoverView.setVisibility(View.GONE);
            Toast.makeText(this, "Password must be at least 6 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.regProgressBar.setVisibility(View.GONE);
            binding.regCoverView.setVisibility(View.GONE);
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerWithEmailAndPassword(String email, String password, String name, String dob) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser != null){
                    String uId = currentUser.getUid();
                    Users user = new Users(name, email, password, dob);

                    databaseReference.child(uId).setValue(user)
                            .addOnCompleteListener(this::onUserDataInsertComplete)
                            .addOnFailureListener(e-> AndroidUtils.logError("Database error " + e.getMessage()));
                    }
                }
                else {
                    binding.regCoverView.setVisibility(View.GONE);
                    binding.regProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "sign up failed", Toast.LENGTH_SHORT).show();
                }
            }

            private void onUserDataInsertComplete(Task<Void> task) {
                if (task.isSuccessful()){
                    binding.regCoverView.setVisibility(View.GONE);
                    binding.regProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    Intent signUpIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(signUpIntent);
                }
                else {
                    binding.regCoverView.setVisibility(View.GONE);
                    binding.regProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnFailureListener(e-> {
            binding.regCoverView.setVisibility(View.GONE);
            binding.regProgressBar.setVisibility(View.GONE);
            AndroidUtils.showToast(RegisterActivity.this ,"Authentication error " + e.getMessage());
        });
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}