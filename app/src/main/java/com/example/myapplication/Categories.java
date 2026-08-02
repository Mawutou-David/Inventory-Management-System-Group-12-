package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.ActivityCategoriesBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    private ActivityCategoriesBinding binding;
    private DatabaseHelper dbHelper;
    private CategoryAdapter adapter;
    private List<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        binding.recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(categoryList, this);
        binding.recyclerViewCategories.setAdapter(adapter);

        binding.fabAddCategory.setOnClickListener(v -> startActivity(new Intent(this, CategoryAddActivity.class)));

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
        loadCategories();
    }

    private void loadCategories() {
        categoryList.clear();
        Cursor cursor = dbHelper.getAllCategories();
        int activeCount = 0;
        
        if (cursor.moveToFirst()) {
            do {
                Category cat = new Category(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_IMAGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CAT_UPDATED_AT))
                );
                categoryList.add(cat);
                if (cat.getStatus() == 1) activeCount++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
        
        binding.tvEmptyState.setVisibility(categoryList.isEmpty() ? View.VISIBLE : View.GONE);
        updateStats(categoryList.size(), activeCount);
    }

    private void updateStats(int total, int active) {
        binding.tvTotalCount.setText(String.valueOf(total));
        binding.tvActiveCount.setText(String.valueOf(active));
        
        // Count low stock products across all categories for the third card
        int lowStockTotal = 0;
        Cursor pCursor = dbHelper.getAllProducts();
        if (pCursor.moveToFirst()) {
            do {
                int qty = pCursor.getInt(pCursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_QUANTITY));
                if (qty > 0 && qty <= 5) lowStockTotal++;
            } while (pCursor.moveToNext());
        }
        pCursor.close();
        binding.tvLowStockCount.setText(String.valueOf(lowStockTotal));
    }

    private void filter(String text) {
        List<Category> filteredList = new ArrayList<>();
        for (Category item : categoryList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getCode().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    @Override
    public void onView(Category category) {
        Toast.makeText(this, "Category: " + category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(Category category) {
        Intent intent = new Intent(this, CategoryAddActivity.class);
        intent.putExtra("EDIT_CATEGORY", category);
        startActivity(intent);
    }

    @Override
    public void onDelete(Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Category")
                .setMessage("Are you sure you want to delete " + category.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean success = dbHelper.deleteCategory(category.getId());
                    if (success) {
                        loadCategories();
                        Snackbar.make(binding.getRoot(), "Category deleted", Snackbar.LENGTH_SHORT).show();
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Cannot Delete")
                                .setMessage("This category has products assigned to it. Remove products first.")
                                .setPositiveButton("OK", null)
                                .show();
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
                startActivity(new Intent(this, Products.class));
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
                showMoreMenu();
                return true;
            }
            return false;
        });
    }

    private void showMoreMenu() {
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