package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Add extends AppCompatActivity {
    private BottomNavigationView bottom;
    private EditText etProductName, etProductID,etPrice, etQuantity;
    private ImageView imgQRCode;
    private Button btnGenerateQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        bottom =findViewById(R.id.bottom);
        etProductName=findViewById(R.id.etProductName);
        etProductID=findViewById(R.id.etProductID);
        etPrice=findViewById(R.id.etPrice);
        etQuantity=findViewById(R.id.etQuantity);

        imgQRCode=findViewById(R.id.imgQRCode);
        btnGenerateQR=findViewById(R.id.btnGenerateQR);

        btnGenerateQR.setOnClickListener(v -> generateQRCode());

    }

    private void generateQRCode() {

        String productData =

                "Product: " + etProductName.getText().toString() +

                        "\nID: " + etProductID.getText().toString() +

                        "\nPrice: " + etPrice.getText().toString() +

                        "\nQuantity: " + etQuantity.getText().toString();

        if (productData.trim().isEmpty()) {

            Toast.makeText(this, "Enter product details first", Toast.LENGTH_SHORT).show();

            return;

        }

        try {

            MultiFormatWriter writer = new MultiFormatWriter();

            BitMatrix matrix = writer.encode(

                    productData,

                    BarcodeFormat.QR_CODE,

                    500,

                    500

            );

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

            imgQRCode.setImageBitmap(bitmap);

        } catch (Exception e) {

            Toast.makeText(this,

                    "QR Generation Failed",

                    Toast.LENGTH_SHORT).show();

        }



        bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(Add.this, Dashboard.class));
                return true;
            }
            if (id == R.id.product) {
                startActivity(new Intent(Add.this, Products.class));
                return true;
            }
            if (id == R.id.add) {
                startActivity(new Intent(Add.this, Add.class));
                return true;
            }
            if (id == R.id.sales) {
                startActivity(new Intent(Add.this, Sales.class));
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