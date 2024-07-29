package com.example.apps.logicLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.apps.presentationLayer.*;
import com.example.apps.dataLayer.VolleyCallBack;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class HRS {
    private Vector<Customer> customers;
    private Vector<Hotel> hotels;
    private Vector<Vendor> vendors;
    private MyDBHelper dbHelper;
    private static HRS hrs;

    // constructor
    private HRS(Context context) {
        customers = new Vector<>();
        hotels = new Vector<>();
        vendors = new Vector<>();
        dbHelper = new MyDBHelper(context);
        getCustomersFromDatabase();
        getVendorsFromDatabase();
        getHotelsFromDatabase();
    }

    // getters
    public static HRS getInstance(Context context) {
        if (hrs == null) {
            hrs = new HRS(context);
        }
        return hrs;
    }

    public Vector<Customer> getCustomers() {
        return customers;
    }

    public Vector<Hotel> getHotels() {
        return hotels;
    }

    public Vector<Vendor> getVendors() {
        return vendors;
    }

    // function to check if customer with same email already exists or not
    public boolean validateCustomerEmail(String email) {
        for (Customer customer : customers) {
            if (email.equals(customer.getEmail())) {
                return false;
            }
        }
        return true;
    }

    // function to check if customer has logged in correctly or not
    public boolean validateCustomerAccount(String email, String pass) {
        for (Customer customer : customers) {
            if (email.equals(customer.getEmail()) && customer.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    // function to check if vendor with same email already exists or not
    public boolean validateVendorEmail(String email) {
        for (Vendor vendor : vendors) {
            if (email.equals(vendor.getEmail())) {
                return false;
            }
        }
        return true;
    }

    // function to check if vendor has logged in correctly or not
    public boolean validateVendorAccount(String email, String pass) {
        for (Vendor vendor : vendors) {
            if (email.equals(vendor.getEmail()) && vendor.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    // function to check if hotel with same name and location already exists or not
    public boolean validateHotel(String name, String loc) {
        for (Hotel hotel : hotels) {
            if (name.equals(hotel.getName()) && loc.equals(hotel.getLocation())) {
                return false;
            }
        }
        return true;
    }

    // function for customer registration
    public void registerCustomer(String name, String email, String pass,
                                 String add, String phone, String cnic, String accNo, Uri dp,
                                 VolleyCallBack volleyCallBack) {
        int ID = 0;

        for (Customer customer : customers) {
            if (customer.getID() > ID) {
                ID = customer.getID();
            }
        }
        ID++;

        Customer c = new Customer(ID, email, pass, name, add, phone, cnic, accNo, "");
        customers.add(c);
        insertCustomerIntoDatabase(c, dp, volleyCallBack);
    }

    // function for vendor registration
    public void registerVendor(String name, String email, String pass,
                               String add, String phone, String cnic, String accNo, Uri dp,
                               VolleyCallBack volleyCallBack) {
        int ID = 0;

        for (Vendor vendor : vendors) {
            if (vendor.getID() > ID) {
                ID = vendor.getID();
            }
        }
        ID++;

        Vendor v = new Vendor(ID, email, pass, name, add, phone, cnic, accNo, "");
        vendors.add(v);
        insertVendorIntoDatabase(v, dp, volleyCallBack);
    }

    // function for hotel registration
    public void registerHotel(String name, String add, String loc, String singleRooms, String doubleRooms,
                              String singleRoomPrice, String doubleRoomPrice, String registered_by) {
        int ID = 0;

        for (Hotel hotel : hotels) {
            if (hotel.getID() > ID) {
                ID = hotel.getID();
            }
        }
        ID++;

        Hotel h = new Hotel(ID, name, add, loc, singleRooms, doubleRooms, singleRoomPrice, doubleRoomPrice, registered_by);
        hotels.add(h);
        insertHotelIntoDatabase(h);
    }

    // function for hotel booking
    public Vector<Hotel> getHotels(String location, String noOfPersons, LocalDate checkInDate, String roomType, boolean both) {
        Vector<Hotel> searchedHotels = new Vector<>();
        for (Hotel hotel : hotels) {
            if (hotel.getLocation().equals(location)) {
                Hotel h1 = new Hotel();
                h1.setAddress(hotel.getAddress());
                h1.setName(hotel.getName());
                h1.setID(hotel.getID());
                h1.setRooms(hotel.getRooms());
                h1.setTotalRooms(hotel.getTotalRooms());
                h1.setLocation(hotel.getLocation());
                h1.setDoubleRoomPrice(hotel.getDoubleRoomPrice());
                h1.setDoubleRooms(hotel.getDoubleRooms());
                h1.setSingleRooms(hotel.getSingleRooms());
                h1.setSingleRoomPrice(hotel.getSingleRoomPrice());
                h1.setReservations(hotel.getReservations());
                Vector<Room> r;
                r = hotel.getRooms(noOfPersons, checkInDate, roomType, both);
                if (r != null) {
                    h1.setRooms(r);
                    searchedHotels.add(h1);
                }
            }
        }
        return searchedHotels;
    }

    // function for reserving room
    public void makeReservation(String email, Hotel h, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                Reservation reservation = h.reserveRoom(checkInDate, checkOutDate, customer, hotels);
                insertReservationIntoDatabase(reservation);
                break;
            }
        }
    }

    // SQLite Database Operations
    private void insertCustomerIntoDatabase(Customer customer, Uri dp, VolleyCallBack volleyCallBack) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_EMAIL, customer.getEmail());
        values.put(MyDBHelper.COLUMN_DP, dp.toString());
        db.insert(MyDBHelper.TABLE_CUSTOMERS, null, values);
        db.close();
        volleyCallBack.onSuccess();
    }

    private void getCustomersFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                MyDBHelper.COLUMN_ID,
                MyDBHelper.COLUMN_EMAIL,
                MyDBHelper.COLUMN_DP
        };

        Cursor cursor = db.query(
                MyDBHelper.TABLE_CUSTOMERS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_EMAIL));
            String dp = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_DP));

            customers.add(new Customer(id, email, "", "", "", "", "", "", dp));
        }
        cursor.close();
    }

    private void insertVendorIntoDatabase(Vendor vendor, Uri dp, VolleyCallBack volleyCallBack) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_EMAIL, vendor.getEmail());
        values.put(MyDBHelper.COLUMN_DP, dp.toString());
        db.insert(MyDBHelper.TABLE_VENDORS, null, values);
        db.close();
        volleyCallBack.onSuccess();
    }

    private void getVendorsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                MyDBHelper.COLUMN_ID,
                MyDBHelper.COLUMN_EMAIL,
                MyDBHelper.COLUMN_DP
        };

        Cursor cursor = db.query(
                MyDBHelper.TABLE_VENDORS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_EMAIL));
            String dp = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_DP));

            vendors.add(new Vendor(id, email, "", "", "", "", "", "", dp));
        }
        cursor.close();
    }

    private void insertHotelIntoDatabase(Hotel hotel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_NAME, hotel.getName());
        values.put(MyDBHelper.COLUMN_LOCATION, hotel.getLocation());
        values.put(MyDBHelper.COLUMN_SINGLE_PRICE, hotel.getSingleRoomPrice());
        values.put(MyDBHelper.COLUMN_DOUBLE_PRICE, hotel.getDoubleRoomPrice());
        db.insert(MyDBHelper.TABLE_HOTELS, null, values);
        db.close();
    }

    private void getHotelsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                MyDBHelper.COLUMN_ID,
                MyDBHelper.COLUMN_NAME,
                MyDBHelper.COLUMN_LOCATION,
                MyDBHelper.COLUMN_SINGLE_PRICE,
                MyDBHelper.COLUMN_DOUBLE_PRICE
        };

        Cursor cursor = db.query(
                MyDBHelper.TABLE_HOTELS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_NAME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_LOCATION));
            String singlePrice = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_SINGLE_PRICE));
            String doublePrice = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_DOUBLE_PRICE));

            hotels.add(new Hotel(id, name, "", location, "", "", singlePrice, doublePrice, ""));
        }
        cursor.close();
    }

    private void insertReservationIntoDatabase(Reservation reservation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COLUMN_NAME, reservation.getHotelName());
        values.put(MyDBHelper.COLUMN_LOCATION, reservation.getHotelLocation());
        values.put(MyDBHelper.COLUMN_CHECKIN, reservation.getCheckInDate());
        values.put(MyDBHelper.COLUMN_CHECKOUT, reservation.getCheckOutDate());
        values.put(MyDBHelper.COLUMN_TOTALPRICE, reservation.getTotalPrice());
        values.put(MyDBHelper.COLUMN_TOTALROOMS, reservation.getTotalRooms());
        values.put(MyDBHelper.COLUMN_ROOMS, reservation.getRoomNumbers());
        values.put(MyDBHelper.COLUMN_RESERVEDBY, reservation.getCustomerEmail());
        db.insert(MyDBHelper.TABLE_RESERVATIONS, null, values);
        db.close();
    }

    private void getReservationsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                MyDBHelper.COLUMN_NAME,
                MyDBHelper.COLUMN_LOCATION,
                MyDBHelper.COLUMN_CHECKIN,
                MyDBHelper.COLUMN_CHECKOUT,
                MyDBHelper.COLUMN_TOTALPRICE,
                MyDBHelper.COLUMN_TOTALROOMS,
                MyDBHelper.COLUMN_ROOMS,
                MyDBHelper.COLUMN_RESERVEDBY
        };

        Cursor cursor = db.query(
                MyDBHelper.TABLE_RESERVATIONS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_NAME));
            String hotelLocation = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_LOCATION));
            String checkInDate = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_CHECKIN));
            String checkOutDate = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_CHECKOUT));
            String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_TOTALPRICE));
            String totalRooms = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_TOTALROOMS));
            String rooms = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_ROOMS));
            String reservedBy = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COLUMN_RESERVEDBY));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
            LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);

            for (Hotel hotel : hotels) {
                if (hotel.getName().equals(hotelName) && hotel.getLocation().equals(hotelLocation)) {
                    hotel.getReservations().add(new Reservation(hotelName, hotelLocation, totalRooms, rooms, totalPrice, checkIn.toString(), checkOut.toString(), reservedBy));
                }
            }
        }
        cursor.close();
    }

    // Adding the missing method
    public Hotel searchHotelByNameLoc(String name, String location) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name) && hotel.getLocation().equals(location)) {
                return hotel;
            }
        }
        return null;
    }
}