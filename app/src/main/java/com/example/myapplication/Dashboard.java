package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navview;
    private ImageView menuicon;

    private BottomNavigationView bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        drawerLayout =findViewById(R.id.drawer_layout);
        navview =findViewById(R.id.navview);
        menuicon =findViewById(R.id.menuicon);
        bottom =findViewById(R.id.bottom);

        menuicon.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navview.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home_item) {
                Intent intent = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent);

            } else if (id == R.id.product_item) {
                Intent intent = new Intent(Dashboard.this, Products.class);
                startActivity(intent);

            } else if (id == R.id.category_item) {
                Intent intent = new Intent(Dashboard.this, Categories.class);
                startActivity(intent);

            } else if (id == R.id.supply_item) {
                Intent intent = new Intent(Dashboard.this, Suppliers.class);
                startActivity(intent);

            } else if (id == R.id.Purchase_item) {
                Intent intent = new Intent(Dashboard.this, Purchases.class);
                startActivity(intent);

            } else if (id == R.id.sales_item) {
                Intent intent = new Intent(Dashboard.this, Sales.class);
                startActivity(intent);

            } else if (id == R.id.Customer_item) {
                Intent intent = new Intent(Dashboard.this, Customers.class);
                startActivity(intent);

            } else if (id == R.id.stock_item) {
                Intent intent = new Intent(Dashboard.this, Stock.class);
                startActivity(intent);

            } else if (id == R.id.report_item) {
                Intent intent = new Intent(Dashboard.this, Reports.class);
                startActivity(intent);

            } else if (id == R.id.setting_item) {
                Intent intent = new Intent(Dashboard.this, Settings.class);
                startActivity(intent);


            } else if (id == R.id.logout_item) {

                new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Logout")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Dashboard.this, LogIn.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            };
            return false;
        });

        bottom.setOnItemSelectedListener(item ->{
            int id = item.getItemId();
            if(id==R.id.home){
                startActivity(new Intent(this, Dashboard.class));
        return true;
            }
            if(id==R.id.product){
                startActivity(new Intent(this, Products.class));
                return true;
            }
            if(id==R.id.add){
                startActivity(new Intent(this, Add.class));
                return true;
            }
            if(id==R.id.sales){
                startActivity(new Intent(this, Sales.class));
                return true;
            }
            if(id==R.id.more){
                PopupMenu popupMenu=new PopupMenu(this, bottom);
                popupMenu.getMenu().add("Settings");
                popupMenu.getMenu().add("Logout");
                popupMenu.setOnMenuItemClickListener(menuItem -> {

                    if(menuItem.getTitle().equals("Settings")){
                        startActivity(new Intent(this, Settings.class));
                        return true;
                    }
                    if(menuItem.getTitle().equals("Logout")) {
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

