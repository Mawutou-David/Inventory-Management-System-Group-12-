package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        binding.btnRegister.setOnClickListener(v -> handleRegistration());
        binding.txtLoginLink.setOnClickListener(v -> finish());
    }

    private void handleRegistration() {
        String name = binding.etFullName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Invalid email format");
            return;
        }

        // Auto-generate Customer ID
        String customerId = "CUST" + (System.currentTimeMillis() % 10000);

        if (dbHelper.isCustomerDuplicate(customerId, phone)) {
            Toast.makeText(this, "Phone number already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save as a new customer
        boolean success = dbHelper.addCustomer(customerId, name, phone, email, "", "", "Retail", "", "", 0.0, 1);
        
        if (success) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LogIn.class));
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}