package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public class Suppliers extends AppCompatActivity {
    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private SupplierAdapter adapter;
    private List<Supplier> supplierList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suppliers);

        dbHelper = new DatabaseHelper(this);
        supplierList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewSuppliers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSuppliers();

        bottom = findViewById(R.id.bottom);
        bottom.setOnItemSelectedListener(item -> {
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
                PopupMenu popupMenu = new PopupMenu(this, bottom);
                popupMenu.getMenu().add("Settings");
                popupMenu.getMenu().add("Logout");
                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    if (menuItem.getTitle().equals("Settings")) {
                        startActivity(new Intent(this, Settings.class));
                        return true;
                    }
                    if (menuItem.getTitle().equals("Logout")) {
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

    private void loadSuppliers() {
        supplierList.clear();
        Cursor cursor = dbHelper.getAllSuppliers();
        if (cursor.getCount() == 0) {
            // Add some mock data if empty
            dbHelper.addSupplier("Alpha Tech", "0201234567", "Accra, Ghana");
            dbHelper.addSupplier("Beta Electronics", "0247654321", "Kumasi, Ghana");
            cursor = dbHelper.getAllSuppliers();
        }

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUPP_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUPP_NAME));
                String contact = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUPP_CONTACT));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUPP_ADDRESS));
                supplierList.add(new Supplier(id, name, contact, address));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new SupplierAdapter(supplierList);
        recyclerView.setAdapter(adapter);
    }
}