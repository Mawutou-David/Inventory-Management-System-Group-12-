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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class Customers extends AppCompatActivity {
    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private List<Customer> customerList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customers);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(customerList);
        recyclerView.setAdapter(adapter);

        loadCustomers();

        bottom =findViewById(R.id.bottom);
        
        // ... rest of the code ...
    }

    private void loadCustomers() {
        customerList.clear();
        Cursor cursor = dbHelper.getAllCustomers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                customerList.add(new Customer(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CUST_REG_DATE))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}
