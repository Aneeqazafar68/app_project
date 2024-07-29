package com.example.apps.dataLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel_reservation.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_VENDORS = "vendors";
    public static final String TABLE_HOTELS = "hotels";
    public static final String TABLE_RESERVATIONS = "reservations";

    // Common column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DP = "dp";

    // Customers table columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONENO = "phoneno";
    public static final String COLUMN_CNIC = "cnic";
    public static final String COLUMN_ACCOUNTNO = "accountno";
    public static final String COLUMN_ADDRESS = "address";

    // Vendors table columns (same as customers)
    // Hotels table columns
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_SINGLE_PRICE = "single_room_price";
    public static final String COLUMN_DOUBLE_PRICE = "double_room_price";
    public static final String COLUMN_SINGLE_ROOMS = "single_rooms";
    public static final String COLUMN_DOUBLE_ROOMS = "double_rooms";
    public static final String COLUMN_REGISTERED_BY = "registered_by";

    // Reservations table columns
    public static final String COLUMN_HOTEL_NAME = "hotel_name";
    public static final String COLUMN_HOTEL_LOCATION = "hotel_location";
    public static final String COLUMN_TOTAL_ROOMS = "total_rooms";
    public static final String COLUMN_ROOM_NUMBERS = "room_numbers";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_CHECKIN_DATE = "check_in_date";
    public static final String COLUMN_CHECKOUT_DATE = "check_out_date";
    public static final String COLUMN_RESERVED_BY = "reserved_by";

    // Table creation statements
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_PHONENO + " TEXT,"
            + COLUMN_CNIC + " TEXT,"
            + COLUMN_ACCOUNTNO + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_DP + " TEXT" + ")";

    private static final String CREATE_TABLE_VENDORS = "CREATE TABLE " + TABLE_VENDORS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_PHONENO + " TEXT,"
            + COLUMN_CNIC + " TEXT,"
            + COLUMN_ACCOUNTNO + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_DP + " TEXT" + ")";

    private static final String CREATE_TABLE_HOTELS = "CREATE TABLE " + TABLE_HOTELS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_SINGLE_ROOMS + " TEXT,"
            + COLUMN_DOUBLE_ROOMS + " TEXT,"
            + COLUMN_SINGLE_PRICE + " TEXT,"
            + COLUMN_DOUBLE_PRICE + " TEXT,"
            + COLUMN_REGISTERED_BY + " TEXT" + ")";

    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE " + TABLE_RESERVATIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_HOTEL_NAME + " TEXT,"
            + COLUMN_HOTEL_LOCATION + " TEXT,"
            + COLUMN_TOTAL_ROOMS + " TEXT,"
            + COLUMN_ROOM_NUMBERS + " TEXT,"
            + COLUMN_TOTAL_PRICE + " TEXT,"
            + COLUMN_CHECKIN_DATE + " TEXT,"
            + COLUMN_CHECKOUT_DATE + " TEXT,"
            + COLUMN_RESERVED_BY + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL(CREATE_TABLE_VENDORS);
        db.execSQL(CREATE_TABLE_HOTELS);
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }
}