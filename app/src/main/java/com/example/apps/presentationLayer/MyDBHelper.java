package com.example.apps.presentationLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel_reservation_system.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONENO = "phoneno";
    public static final String COLUMN_CNIC = "cnic";
    public static final String COLUMN_ACCOUNTNO = "accountno";
    public static final String COLUMN_DP = "dp";

    public static final String TABLE_VENDORS = "vendors";
    public static final String TABLE_HOTELS = "hotels";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_SINGLE_ROOMS = "single_rooms";
    public static final String COLUMN_DOUBLE_ROOMS = "double_rooms";
    public static final String COLUMN_SINGLE_PRICE = "single_room_price";
    public static final String COLUMN_DOUBLE_PRICE = "double_room_price";
    public static final String COLUMN_REGISTERED_BY = "registered_by";

    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String COLUMN_CHECKIN = "check_in_date";
    public static final String COLUMN_CHECKOUT = "check_out_date";
    public static final String COLUMN_TOTALPRICE = "total_price";
    public static final String COLUMN_TOTALROOMS = "total_rooms";
    public static final String COLUMN_ROOMS = "room_numbers";
    public static final String COLUMN_RESERVEDBY = "reserved_by";

    private static final String TABLE_CREATE_CUSTOMERS =
            "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONENO + " TEXT, " +
                    COLUMN_CNIC + " TEXT, " +
                    COLUMN_ACCOUNTNO + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_DP + " TEXT);";

    private static final String TABLE_CREATE_VENDORS =
            "CREATE TABLE " + TABLE_VENDORS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONENO + " TEXT, " +
                    COLUMN_CNIC + " TEXT, " +
                    COLUMN_ACCOUNTNO + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_DP + " TEXT);";

    private static final String TABLE_CREATE_HOTELS =
            "CREATE TABLE " + TABLE_HOTELS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_LOCATION + " TEXT, " +
                    COLUMN_SINGLE_ROOMS + " TEXT, " +
                    COLUMN_DOUBLE_ROOMS + " TEXT, " +
                    COLUMN_SINGLE_PRICE + " TEXT, " +
                    COLUMN_DOUBLE_PRICE + " TEXT, " +
                    COLUMN_REGISTERED_BY + " TEXT);";

    private static final String TABLE_CREATE_RESERVATIONS =
            "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_LOCATION + " TEXT, " +
                    COLUMN_CHECKIN + " TEXT, " +
                    COLUMN_CHECKOUT + " TEXT, " +
                    COLUMN_TOTALPRICE + " TEXT, " +
                    COLUMN_TOTALROOMS + " TEXT, " +
                    COLUMN_ROOMS + " TEXT, " +
                    COLUMN_RESERVEDBY + " TEXT);";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_CUSTOMERS);
        db.execSQL(TABLE_CREATE_VENDORS);
        db.execSQL(TABLE_CREATE_HOTELS);
        db.execSQL(TABLE_CREATE_RESERVATIONS);
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