package com.example.apps.dataLayer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.apps.presentationLayer.MyDBHelper;
import com.example.apps.logicLayer.Customer;
import com.example.apps.logicLayer.Hotel;
import com.example.apps.logicLayer.Reservation;
import com.example.apps.logicLayer.Vendor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class  writerAndReader {
    Context context;
    MyDBHelper dbHelper;

    public writerAndReader(Context context) {
        this.context = context;
        this.dbHelper = new MyDBHelper(context);
    }

    public void insertCustomerDataIntoDatabase(Customer customer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", customer.getName());
        values.put("email", customer.getEmail());
        values.put("password", customer.getPassword());
        values.put("phoneno", customer.getPhoneNo());
        values.put("cnic", customer.getCNIC());
        values.put("accountno", customer.getAccountNo());
        values.put("address", customer.getAddress());

        long newRowId = db.insert("customers", null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error inserting customer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Customer inserted with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void getCustomersFromDatabase(Vector<Customer> customers) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id", "name", "email", "password", "phoneno", "cnic", "accountno", "address"
        };

        Cursor cursor = db.query("customers", projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            customers.add(new Customer(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phoneno")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cnic")),
                    cursor.getString(cursor.getColumnIndexOrThrow("accountno")),
                    ""
            ));
        }
        cursor.close();
    }

    public void insertVendorDataIntoDatabase(Vendor vendor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", vendor.getName());
        values.put("email", vendor.getEmail());
        values.put("password", vendor.getPassword());
        values.put("phoneno", vendor.getPhoneNo());
        values.put("cnic", vendor.getCNIC());
        values.put("accountno", vendor.getAccountNo());
        values.put("address", vendor.getAddress());

        long newRowId = db.insert("vendors", null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error inserting vendor", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Vendor inserted with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void getVendorsFromDatabase(Vector<Vendor> vendors) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id", "name", "email", "password", "phoneno", "cnic", "accountno", "address"
        };

        Cursor cursor = db.query("vendors", projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            vendors.add(new Vendor(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phoneno")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cnic")),
                    cursor.getString(cursor.getColumnIndexOrThrow("accountno")),
                    ""
            ));
        }
        cursor.close();
    }

    public void insertHotelIntoDatabase(Hotel hotel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hotel.getName());
        values.put("address", hotel.getAddress());
        values.put("location", hotel.getLocation());
        values.put("single_rooms", hotel.getSingleRooms());
        values.put("double_rooms", hotel.getDoubleRooms());
        values.put("single_room_price", hotel.getSingleRoomPrice());
        values.put("double_room_price", hotel.getDoubleRoomPrice());
        values.put("registered_by", hotel.getRegistered_by());

        long newRowId = db.insert("hotels", null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error inserting hotel", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hotel inserted with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void getHotelsFromDatabase(Vector<Hotel> hotels) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id", "name", "address", "location", "single_rooms", "double_rooms", "single_room_price", "double_room_price", "registered_by"
        };

        Cursor cursor = db.query("hotels", projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            hotels.add(new Hotel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    cursor.getString(cursor.getColumnIndexOrThrow("location")),
                    cursor.getString(cursor.getColumnIndexOrThrow("single_rooms")),
                    cursor.getString(cursor.getColumnIndexOrThrow("double_rooms")),
                    cursor.getString(cursor.getColumnIndexOrThrow("single_room_price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("double_room_price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("registered_by"))
            ));
        }
        cursor.close();
    }

    public void insertReservationIntoDatabase(Reservation reservation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hotel_name", reservation.getHotelName());
        values.put("hotel_location", reservation.getHotelLocation());
        values.put("total_rooms", reservation.getTotalRooms());
        values.put("room_numbers", reservation.getRoomNumbers());
        values.put("total_price", reservation.getTotalPrice());
        values.put("check_in_date", reservation.getCheckInDate());
        values.put("check_out_date", reservation.getCheckOutDate());
        values.put("reserved_by", reservation.getCustomerEmail());

        long newRowId = db.insert("reservations", null, values);
        if (newRowId == -1) {
            Toast.makeText(context, "Error inserting reservation", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Reservation inserted with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public void getReservationsFromDatabase(Vector<Hotel> hotels) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "hotel_name", "hotel_location", "total_rooms", "room_numbers", "total_price", "check_in_date", "check_out_date", "reserved_by"
        };

        Cursor cursor = db.query("reservations", projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String hotel_name = cursor.getString(cursor.getColumnIndexOrThrow("hotel_name"));
            String hotel_location = cursor.getString(cursor.getColumnIndexOrThrow("hotel_location"));

            for (Hotel hotel : hotels) {
                if (hotel.getName().equals(hotel_name) && hotel.getLocation().equals(hotel_location)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate checkInDate = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("check_in_date")), formatter);
                    LocalDate checkOutDate = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("check_out_date")), formatter);

                    hotel.getReservations().add(new Reservation(
                            hotel_name,
                            hotel_location,
                            cursor.getString(cursor.getColumnIndexOrThrow("total_rooms")),
                            cursor.getString(cursor.getColumnIndexOrThrow("room_numbers")),
                            cursor.getString(cursor.getColumnIndexOrThrow("total_price")),
                            checkInDate.toString(),
                            checkOutDate.toString(),
                            cursor.getString(cursor.getColumnIndexOrThrow("reserved_by"))
                    ));
                }
            }
        }
        cursor.close();
    }
}