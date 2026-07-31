package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class Settings extends AppCompatActivity {
    private BottomNavigationView bottom;
    private LinearLayout account, change, help,about;
    private Switch switchDarkMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settings);

        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        bottom =findViewById(R.id.bottom);
        account=findViewById(R.id.account);
        switchDarkMode=findViewById(R.id.switchDarkMode);
        change=findViewById(R.id.change);
        help=findViewById(R.id.help);
        about=findViewById(R.id.about);

        account.setOnClickListener(v ->{
            Intent intent=new Intent(Settings.this,Account.class);
            startActivity(intent);
        });


        change.setOnClickListener(v ->{
            Intent intent=new Intent(Settings.this,password.class);
            startActivity(intent);
        });

        about.setOnClickListener(v ->{
            Intent intent=new Intent(Settings.this,About.class);
            startActivity(intent);
        });

        help.setOnClickListener(v ->{
            Intent intent=new Intent(Settings.this,Help.class);
            startActivity(intent);
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );
            }else {
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
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



        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(Settings.this)
                    .setTitle("Logout")
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) ->{

                            Intent intent = new Intent(Settings.this, LogIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();

        });

    };
}