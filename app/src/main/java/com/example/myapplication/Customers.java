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

import com.example.myapplication.databinding.ActivityCustomersBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customers extends AppCompatActivity implements CustomerAdapter.OnCustomerClickListener {

    private ActivityCustomersBinding binding;
    private DatabaseHelper dbHelper;
    private CustomerAdapter adapter;
    private List<Customer> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCustomersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);

        binding.recyclerViewCustomers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(customerList, this);
        binding.recyclerViewCustomers.setAdapter(adapter);

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

        binding.btnFilter.setOnClickListener(v -> showFilterMenu());

        setupBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCustomers();
    }

    private void loadCustomers() {
        customerList.clear();
        Cursor cursor = dbHelper.getAllCustomers();
        
        if (cursor.moveToFirst()) {
            do {
                Customer cust = new Customer(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_COMPANY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_TIN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_NOTES)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_BALANCE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_REG_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_LAST_PURCHASE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_TOTAL_PURCHASES))
                );
                customerList.add(cust);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();

        binding.tvEmptyState.setVisibility(customerList.isEmpty() ? View.VISIBLE : View.GONE);
        updateStats(customerList.size());
    }

    private void updateStats(int total) {
        binding.tvTotalCount.setText(String.valueOf(total));
    }

    private void filter(String text) {
        List<Customer> filteredList = new ArrayList<>();
        for (Customer item : customerList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                item.getId().toLowerCase().contains(text.toLowerCase()) ||
                item.getPhone().toLowerCase().contains(text.toLowerCase()) ||
                item.getCompany().toLowerCase().contains(text.toLowerCase()) ||
                item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

    private void showFilterMenu() {
        PopupMenu popup = new PopupMenu(this, binding.btnFilter);
        popup.getMenu().add("All");
        popup.getMenu().add("Retail");
        popup.getMenu().add("Wholesale");
        popup.getMenu().add("Active");
        popup.getMenu().add("Inactive");
        popup.getMenu().add("Sort by Name (A-Z)");
        popup.getMenu().add("Sort by Registration Date (Newest)");

        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            List<Customer> filtered = new ArrayList<>();
            if (title.equals("All")) {
                filtered = customerList;
            } else if (title.equals("Retail")) {
                for (Customer c : customerList) if (c.getType().equals("Retail")) filtered.add(c);
            } else if (title.equals("Wholesale")) {
                for (Customer c : customerList) if (c.getType().equals("Wholesale")) filtered.add(c);
            } else if (title.equals("Active")) {
                for (Customer c : customerList) if (c.getStatus() == 1) filtered.add(c);
            } else if (title.equals("Inactive")) {
                for (Customer c : customerList) if (c.getStatus() == 0) filtered.add(c);
            } else if (title.equals("Sort by Name (A-Z)")) {
                filtered.addAll(customerList);
                Collections.sort(filtered, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
            } else if (title.equals("Sort by Registration Date (Newest)")) {
                filtered.addAll(customerList);
                Collections.sort(filtered, (c1, c2) -> c2.getRegistrationDate().compareTo(c1.getRegistrationDate()));
            }
            adapter.updateList(filtered);
            return true;
        });
        popup.show();
    }

    @Override
    public void onView(Customer customer) {
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("CUSTOMER", customer);
        startActivity(intent);
    }

    @Override
    public void onDelete(Customer customer) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Customer")
                .setMessage("Are you sure you want to delete " + customer.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean success = dbHelper.deleteCustomer(customer.getId());
                    if (success) {
                        loadCustomers();
                        Snackbar.make(binding.getRoot(), "Customer deleted", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to delete customer", Toast.LENGTH_SHORT).show();
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