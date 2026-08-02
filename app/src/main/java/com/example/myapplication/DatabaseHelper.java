package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Products table
    public static final String TABLE_PRODUCTS = "products";
    public static final String COL_PROD_ID = "id";
    public static final String COL_PROD_NAME = "name";
    public static final String COL_PROD_CATEGORY = "category";
    public static final String COL_PROD_SUPPLIER = "supplier";
    public static final String COL_PROD_PRICE = "price";
    public static final String COL_PROD_QUANTITY = "quantity";

    // Suppliers table
    public static final String TABLE_SUPPLIERS = "suppliers";
    public static final String COL_SUPP_ID = "id";
    public static final String COL_SUPP_NAME = "name";
    public static final String COL_SUPP_CONTACT = "contact";
    public static final String COL_SUPP_ADDRESS = "address";

    // Purchases table
    public static final String TABLE_PURCHASES = "purchases";
    public static final String COL_PURCH_ID = "id";
    public static final String COL_PURCH_PROD_NAME = "product_name";
    public static final String COL_PURCH_QUANTITY = "quantity";
    public static final String COL_PURCH_DATE = "date";
    public static final String COL_PURCH_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_PROD_ID + " TEXT PRIMARY KEY, " +
                COL_PROD_NAME + " TEXT, " +
                COL_PROD_CATEGORY + " TEXT, " +
                COL_PROD_SUPPLIER + " TEXT, " +
                COL_PROD_PRICE + " REAL, " +
                COL_PROD_QUANTITY + " INTEGER)";

        String createSuppliersTable = "CREATE TABLE " + TABLE_SUPPLIERS + " (" +
                COL_SUPP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SUPP_NAME + " TEXT, " +
                COL_SUPP_CONTACT + " TEXT, " +
                COL_SUPP_ADDRESS + " TEXT)";

        String createPurchasesTable = "CREATE TABLE " + TABLE_PURCHASES + " (" +
                COL_PURCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PURCH_PROD_NAME + " TEXT, " +
                COL_PURCH_QUANTITY + " INTEGER, " +
                COL_PURCH_DATE + " TEXT, " +
                COL_PURCH_TOTAL + " REAL)";

        db.execSQL(createProductsTable);
        db.execSQL(createSuppliersTable);
        db.execSQL(createPurchasesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASES);
        onCreate(db);
    }

    // Product methods
    public boolean addProduct(String id, String name, String category, String supplier, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PROD_ID, id);
        values.put(COL_PROD_NAME, name);
        values.put(COL_PROD_CATEGORY, category);
        values.put(COL_PROD_SUPPLIER, supplier);
        values.put(COL_PROD_PRICE, price);
        values.put(COL_PROD_QUANTITY, quantity);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        return result != -1;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    // Supplier methods
    public boolean addSupplier(String name, String contact, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SUPP_NAME, name);
        values.put(COL_SUPP_CONTACT, contact);
        values.put(COL_SUPP_ADDRESS, address);

        long result = db.insert(TABLE_SUPPLIERS, null, values);
        return result != -1;
    }

    public Cursor getAllSuppliers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SUPPLIERS, null);
    }

    // Purchase methods
    public boolean addPurchase(String productName, int quantity, String date, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PURCH_PROD_NAME, productName);
        values.put(COL_PURCH_QUANTITY, quantity);
        values.put(COL_PURCH_DATE, date);
        values.put(COL_PURCH_TOTAL, total);

        long result = db.insert(TABLE_PURCHASES, null, values);
        return result != -1;
    }

    public Cursor getAllPurchases() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PURCHASES, null);
    }
}