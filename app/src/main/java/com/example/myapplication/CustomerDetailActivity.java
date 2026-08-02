package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityCustomerDetailBinding;

import java.util.Locale;

public class CustomerDetailActivity extends AppCompatActivity {

    private ActivityCustomerDetailBinding binding;
    private DatabaseHelper dbHelper;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("CUSTOMER")) {
            customer = (Customer) getIntent().getSerializableExtra("CUSTOMER");
            if (customer != null) {
                displayCustomer();
            }
        }

        binding.btnDelete.setOnClickListener(v -> deleteCustomer());
    }

    private void displayCustomer() {
        binding.tvName.setText(customer.getName());
        binding.tvID.setText("ID: " + customer.getId());
        binding.tvPhone.setText(customer.getPhone());
        binding.tvEmail.setText(customer.getEmail());
        binding.tvCompany.setText(customer.getCompany());
        binding.tvAddress.setText(customer.getAddress());
        binding.tvType.setText(customer.getType());
        binding.tvRegDate.setText(customer.getRegistrationDate());
        binding.tvTotalPurchases.setText(String.valueOf(customer.getTotalPurchases()));
        binding.tvNotes.setText(customer.getNotes().isEmpty() ? "No notes available." : customer.getNotes());
    }

    private void deleteCustomer() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Customer")
                .setMessage("Are you sure you want to delete " + customer.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (dbHelper.deleteCustomer(customer.getId())) {
                        Toast.makeText(this, "Customer deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to delete customer", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}