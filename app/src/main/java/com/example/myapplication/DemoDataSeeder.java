package com.example.myapplication;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DemoDataSeeder {

    /**
     * Seeds the database with demonstration data for Products and Customers.
     * This is intended for presentation purposes.
     * 
     * @param context The application context.
     */
    public static void seedData(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        // Only seed if the database is considered empty (no products and no customers)
        if (!dbHelper.isDatabaseEmpty()) {
            return;
        }

        // 1. Seed Categories (to support Product associations)
        dbHelper.addCategory("Smartphones", "CAT001", "Latest mobile devices", null, 1);
        dbHelper.addCategory("Laptops", "CAT002", "Portable computers", null, 1);
        dbHelper.addCategory("Speakers", "CAT003", "Audio output devices", null, 1);
        dbHelper.addCategory("Accessories", "CAT004", "Computer and phone accessories", null, 1);
        dbHelper.addCategory("Printers", "CAT005", "Printing and scanning devices", null, 1);
        dbHelper.addCategory("Headphones", "CAT006", "Personal audio devices", null, 1);
        dbHelper.addCategory("Networking", "CAT007", "Network equipment", null, 1);

        // 2. Seed Suppliers (to support Product associations)
        dbHelper.addSupplier("Samsung Ghana", "0244111222", "Airport Residential Area, Accra");
        dbHelper.addSupplier("Apple Distributor", "0209333444", "Osu, Accra");
        dbHelper.addSupplier("HP Ghana", "0557555666", "North Ridge, Accra");
        dbHelper.addSupplier("Dell Technologies", "0278777888", "Tema Community 1");
        dbHelper.addSupplier("JBL", "0244999000", "East Legon, Accra");
        dbHelper.addSupplier("Logitech", "0209111333", "Cantonments, Accra");
        dbHelper.addSupplier("Canon", "0557222444", "Adabraka, Accra");
        dbHelper.addSupplier("Sony", "0278333555", "Labone, Accra");
        dbHelper.addSupplier("Lenovo", "0244444666", "Spintex Road, Accra");
        dbHelper.addSupplier("TP-Link", "0209555777", "Dansoman, Accra");

        // 3. Seed Products (10-15 items as requested)
        dbHelper.addProduct("PRD001", "Samsung Galaxy A56", "Smartphones", "Samsung Ghana", 4200.00, 25);
        dbHelper.addProduct("PRD002", "iPhone 15", "Smartphones", "Apple Distributor", 12500.00, 12);
        dbHelper.addProduct("PRD003", "HP Pavilion 15", "Laptops", "HP Ghana", 9800.00, 8);
        dbHelper.addProduct("PRD004", "Dell Inspiron 3520", "Laptops", "Dell Technologies", 8900.00, 10);
        dbHelper.addProduct("PRD005", "JBL Flip 6", "Speakers", "JBL", 1850.00, 18);
        dbHelper.addProduct("PRD006", "Logitech MX Master 3S", "Accessories", "Logitech", 1450.00, 20);
        dbHelper.addProduct("PRD007", "Canon PIXMA G3430", "Printers", "Canon", 3100.00, 6);
        dbHelper.addProduct("PRD008", "Sony WH-1000XM5", "Headphones", "Sony", 4800.00, 9);
        dbHelper.addProduct("PRD009", "Lenovo ThinkPad E16", "Laptops", "Lenovo", 11000.00, 5);
        dbHelper.addProduct("PRD010", "TP-Link Archer AX55", "Networking", "TP-Link", 1200.00, 15);
        
        // Variants for stock status indicators (Low stock and Out of stock)
        dbHelper.addProduct("PRD011", "MacBook Pro 14", "Laptops", "Apple Distributor", 22000.00, 2);  // Low stock
        dbHelper.addProduct("PRD012", "Samsung Galaxy S23", "Smartphones", "Samsung Ghana", 7500.00, 0); // Out of stock
        dbHelper.addProduct("PRD013", "Epson EcoTank", "Printers", "Canon", 2800.00, 3);                 // Low stock

        // 4. Seed Customers (8-10 items as requested)
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dbHelper.addCustomer("CUST001", "Kwame Asare", "0244123456", "kwame.asare@example.com", today);
        dbHelper.addCustomer("CUST002", "Ama Boateng", "0209654321", "ama.boateng@example.com", today);
        dbHelper.addCustomer("CUST003", "Kofi Mensah", "0557111222", "kofi.mensah@example.com", today);
        dbHelper.addCustomer("CUST004", "Akosua Owusu", "0278333444", "akosua.owusu@example.com", today);
        dbHelper.addCustomer("CUST005", "Michael Johnson", "0244555666", "m.johnson@example.com", today);
        dbHelper.addCustomer("CUST006", "Sarah Williams", "0209777888", "s.williams@example.com", today);
        dbHelper.addCustomer("CUST007", "Daniel Ofori", "0557999000", "d.ofori@example.com", today);
        dbHelper.addCustomer("CUST008", "Grace Adu", "0278111333", "g.adu@example.com", today);
        dbHelper.addCustomer("CUST009", "Richard Annan", "0244222444", "r.annan@example.com", today);
        dbHelper.addCustomer("CUST010", "Evelyn Appiah", "0209333555", "e.appiah@example.com", today);
    }
}
