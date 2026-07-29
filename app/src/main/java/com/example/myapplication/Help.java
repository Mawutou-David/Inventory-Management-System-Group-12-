package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Help extends AppCompatActivity {
    private BottomNavigationView bottom;
    private Button btnContact;
    private TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        bottom = findViewById(R.id.bottom);
        btnContact = findViewById(R.id.btnContact);
        txtEmail= findViewById(R.id.txtEmail);

        btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+233 271544334"));
            startActivity(intent);

        });

        txtEmail.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:iamafei68@gmail.com"));

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                }
    });

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
}