package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.ActivityProductsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ActivityProductsBinding binding;
    private DatabaseHelper dbHelper;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);
        binding.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(productList, this);
        binding.recyclerViewProducts.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        setupBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        productList.clear();
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor.moveToFirst()) {
            do {
                productList.add(new Product(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_SUPPLIER)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_QUANTITY))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();
        for (Product item : productList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                item.getId().toLowerCase().contains(text.toLowerCase()) ||
                item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    @Override
    public void onEdit(Product product) {
        Intent intent = new Intent(this, Add.class);
        intent.putExtra("EDIT_PRODUCT", product);
        startActivity(intent);
    }

    @Override
    public void onDelete(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete " + product.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (dbHelper.deleteProduct(product.getId())) {
                        loadProducts();
                        Snackbar.make(binding.getRoot(), "Product deleted", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setupBottomNav() {
        binding.bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, Dashboard.class));
                return true;
            }
            if (id == R.id.product) {
                return true;
            }
            if (id == R.id.add) {
                startActivity(new Intent(this, Add.class));
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
                .setMessage("Exit app?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(this, LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}