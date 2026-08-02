package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityAddBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Add extends AppCompatActivity {
    private ActivityAddBinding binding;
    private DatabaseHelper dbHelper;
    private Product productToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("EDIT_PRODUCT")) {
            productToEdit = (Product) getIntent().getSerializableExtra("EDIT_PRODUCT");
            if (productToEdit != null) {
                binding.tvTitle.setText("Edit Product");
                binding.etProductName.setText(productToEdit.getName());
                binding.etProductID.setText(productToEdit.getId());
                binding.etProductID.setEnabled(false);
                binding.actCategory.setText(productToEdit.getCategory());
                binding.etSupplier.setText(productToEdit.getSupplier());
                binding.etPrice.setText(String.valueOf(productToEdit.getPrice()));
                binding.etQuantity.setText(String.valueOf(productToEdit.getQuantity()));
                binding.btnSave.setVisibility(View.GONE);
                binding.btnUpdate.setVisibility(View.VISIBLE);
            }
        }

        binding.btnGenerateQR.setOnClickListener(v -> generateQRCode());
        binding.btnSave.setOnClickListener(v -> saveProduct());
        binding.btnUpdate.setOnClickListener(v -> updateProduct());
        binding.btnCancel.setOnClickListener(v -> finish());

        setupBottomNav();
    }

    private void saveProduct() {
        String name = binding.etProductName.getText().toString();
        String id = binding.etProductID.getText().toString();
        String category = binding.actCategory.getText().toString();
        String supplier = binding.etSupplier.getText().toString();
        String priceStr = binding.etPrice.getText().toString();
        String quantityStr = binding.etQuantity.getText().toString();

        if (name.isEmpty() || id.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        boolean success = dbHelper.addProduct(id, name, category, supplier, price, quantity);
        if (success) {
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProduct() {
        String name = binding.etProductName.getText().toString();
        String category = binding.actCategory.getText().toString();
        String supplier = binding.etSupplier.getText().toString();
        String priceStr = binding.etPrice.getText().toString();
        String quantityStr = binding.etQuantity.getText().toString();

        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        boolean success = dbHelper.updateProduct(productToEdit.getId(), name, category, supplier, price, quantity);
        if (success) {
            Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateQRCode() {
        String productData = "Product: " + binding.etProductName.getText().toString() +
                "\nID: " + binding.etProductID.getText().toString() +
                "\nPrice: " + binding.etPrice.getText().toString() +
                "\nQuantity: " + binding.etQuantity.getText().toString();

        if (productData.trim().isEmpty()) {
            Toast.makeText(this, "Enter product details first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(productData, BarcodeFormat.QR_CODE, 500, 500);
            Bitmap bitmap = new BarcodeEncoder().createBitmap(matrix);
            binding.imgQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(this, "QR Generation Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupBottomNav() {
        binding.bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            }
            if (id == R.id.product) {
                startActivity(new Intent(this, Products.class));
                return true;
            }
            if (id == R.id.add) {
                return true;
            }
            if (id == R.id.sales) {
                startActivity(new Intent(this, Sales.class));
                return true;
            }
            if (id == R.id.more) {
                PopupMenu popupMenu = new PopupMenu(this, binding.bottom);
                popupMenu.getMenu().add("Settings");
                popupMenu.getMenu().add("Logout");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getTitle().equals("Settings")) {
                        startActivity(new Intent(this, Settings.class));
                        return true;
                    }
                    if (menuItem.getTitle().equals("Logout")) {
                        logout();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            }
            return false;
        });
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(this, LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}