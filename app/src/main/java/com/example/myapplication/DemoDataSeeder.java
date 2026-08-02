package com.example.myapplication;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DemoDataSeeder {

    /**
     * Seeds the database with demonstration data for Categories, Products, Suppliers, 
     * Customers, and Purchases. This is intended for presentation purposes.
     * 
     * @param context The application context.
     */
    public static void seedData(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        // Only seed if the database is considered empty (no products and no customers)
        if (!dbHelper.isDatabaseEmpty()) {
            return;
        }

        // 1. Seed Categories (10 requested categories)
        dbHelper.addCategory("Smartphones", "CAT001", "Latest mobile devices", null, 1);
        dbHelper.addCategory("Laptops", "CAT002", "Portable computers", null, 1);
        dbCategoryAdd(dbHelper, "Tablets", "CAT003", "Large screen mobile devices");
        dbHelper.addCategory("Accessories", "CAT004", "Computer and phone accessories", null, 1);
        dbHelper.addCategory("Audio Devices", "CAT005", "Audio output devices", null, 1);
        dbHelper.addCategory("Printers", "CAT006", "Printing and scanning devices", null, 1);
        dbHelper.addCategory("Networking", "CAT007", "Network equipment", null, 1);
        dbHelper.addCategory("Storage Devices", "CAT008", "Hard drives and flash drives", null, 1);
        dbHelper.addCategory("Smart Watches", "CAT009", "Wearable technology", null, 1);
        dbHelper.addCategory("Computer Components", "CAT010", "PC parts and components", null, 1);

        // 2. Seed Suppliers (8 suppliers)
        dbHelper.addSupplier("Samsung Ghana", "0244111222", "Airport Residential Area, Accra");
        dbHelper.addSupplier("Apple Distributor", "0209333444", "Osu, Accra");
        dbHelper.addSupplier("HP Ghana", "0557555666", "North Ridge, Accra");
        dbHelper.addSupplier("Dell Technologies", "0278777888", "Tema Community 1");
        dbHelper.addSupplier("JBL Harman", "0244999000", "East Legon, Accra");
        dbHelper.addSupplier("Logitech Africa", "0209111333", "Cantonments, Accra");
        dbHelper.addSupplier("Canon Central", "0557222444", "Adabraka, Accra");
        dbHelper.addSupplier("TP-Link Solutions", "0209555777", "Dansoman, Accra");

        // 3. Seed Products (20-25 items connected to categories and suppliers)
        // Smartphones
        dbHelper.addProduct("PRD001", "Samsung Galaxy A56", "Smartphones", "Samsung Ghana", 4200.00, 25);
        dbHelper.addProduct("PRD002", "iPhone 15 Pro", "Smartphones", "Apple Distributor", 12500.00, 12);
        dbHelper.addProduct("PRD003", "Samsung Galaxy S23", "Smartphones", "Samsung Ghana", 7500.00, 0); // Out of Stock
        
        // Laptops
        dbHelper.addProduct("PRD004", "HP Pavilion 15", "Laptops", "HP Ghana", 9800.00, 8);
        dbHelper.addProduct("PRD005", "Dell Inspiron 3520", "Laptops", "Dell Technologies", 8900.00, 10);
        dbHelper.addProduct("PRD006", "MacBook Pro 14", "Laptops", "Apple Distributor", 22000.00, 2); // Low Stock
        
        // Audio Devices
        dbHelper.addProduct("PRD007", "JBL Flip 6", "Audio Devices", "JBL Harman", 1850.00, 18);
        dbHelper.addProduct("PRD008", "Sony WH-1000XM5", "Audio Devices", "JBL Harman", 4800.00, 9);
        dbHelper.addProduct("PRD009", "AirPods Pro 2", "Audio Devices", "Apple Distributor", 3500.00, 15);
        
        // Accessories
        dbHelper.addProduct("PRD010", "Logitech MX Master 3S", "Accessories", "Logitech Africa", 1450.00, 20);
        dbHelper.addProduct("PRD011", "Apple MagSafe Charger", "Accessories", "Apple Distributor", 850.00, 30);
        
        // Printers
        dbHelper.addProduct("PRD012", "Canon PIXMA G3430", "Printers", "Canon Central", 3100.00, 6);
        dbHelper.addProduct("PRD013", "HP LaserJet Pro", "Printers", "HP Ghana", 4500.00, 4); // Low Stock
        
        // Networking
        dbHelper.addProduct("PRD014", "TP-Link Archer AX55", "Networking", "TP-Link Solutions", 1200.00, 15);
        dbHelper.addProduct("PRD015", "Netgear Nighthawk", "Networking", "TP-Link Solutions", 2500.00, 7);
        
        // Tablets
        dbHelper.addProduct("PRD016", "iPad Air M2", "Tablets", "Apple Distributor", 8500.00, 10);
        dbHelper.addProduct("PRD017", "Samsung Galaxy Tab S9", "Tablets", "Samsung Ghana", 7200.00, 5); // Low Stock
        
        // Smart Watches
        dbHelper.addProduct("PRD018", "Apple Watch Series 9", "Smart Watches", "Apple Distributor", 5500.00, 8);
        dbHelper.addProduct("PRD019", "Samsung Galaxy Watch 6", "Smart Watches", "Samsung Ghana", 3800.00, 12);
        
        // Storage
        dbHelper.addProduct("PRD020", "Samsung T7 1TB SSD", "Storage Devices", "Samsung Ghana", 1600.00, 20);
        dbHelper.addProduct("PRD021", "SanDisk Extreme 512GB", "Storage Devices", "Logitech Africa", 750.00, 50);

        // 4. Seed Customers (10 items)
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

        // 5. Seed Purchases (15 items linked to products)
        dbHelper.addPurchase("Samsung Galaxy A56", 10, today, 42000.00);
        dbHelper.addPurchase("iPhone 15 Pro", 5, today, 62500.00);
        dbHelper.addPurchase("HP Pavilion 15", 3, today, 29400.00);
        dbHelper.addPurchase("JBL Flip 6", 20, today, 37000.00);
        dbHelper.addPurchase("Logitech MX Master 3S", 10, today, 14500.00);
        dbHelper.addPurchase("iPad Air M2", 5, today, 42500.00);
        dbHelper.addPurchase("Apple Watch Series 9", 2, today, 11000.00);
        dbHelper.addPurchase("Samsung T7 1TB SSD", 15, today, 24000.00);
        dbHelper.addPurchase("TP-Link Archer AX55", 10, today, 12000.00);
        dbHelper.addPurchase("Canon PIXMA G3430", 5, today, 15500.00);
    }

    private static void dbCategoryAdd(DatabaseHelper db, String name, String code, String desc) {
        db.addCategory(name, code, desc, null, 1);
    }
}
