package com.example.apps.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apps.R;
import com.example.apps.logicLayer.HRS;
import com.example.apps.logicLayer.Hotel;
import com.example.apps.logicLayer.Reservation;

import java.util.Vector;

public class Hotel_Reservation_Screen extends AppCompatActivity {

    TextView hotelName, rooms, totalPrice, totalRooms;
    HRS hrs;
    String email, checkInDate, checkOutDate, hotelNameString, hotelLocation;
    Hotel h1;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation_screen);

        hotelName = findViewById(R.id.tv_hotel_name);
        rooms = findViewById(R.id.tv_rooms);
        totalPrice = findViewById(R.id.tv_total_price);
        totalRooms = findViewById(R.id.tv_total_rooms);
        hrs = HRS.getInstance(Hotel_Reservation_Screen.this);
        dbHelper = new MyDBHelper(this);

        email = getIntent().getStringExtra("Email");
        hotelNameString = getIntent().getStringExtra("Hotel_name");
        hotelLocation = getIntent().getStringExtra("Hotel_Loc");
        checkInDate = getIntent().getStringExtra("checkinDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        h1 = hrs.searchHotelByNameLoc(hotelNameString, hotelLocation);

        Vector<Reservation> res = h1.getReservations();

        Reservation reservation = res.get(res.size() - 1);
        hotelName.setText(h1.getName());
        totalRooms.setText(reservation.getTotalRooms());
        totalPrice.setText(reservation.getTotalPrice());
        rooms.setText(reservation.getRoomNumbers());

        findViewById(R.id.END_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hotel_Reservation_Screen.this, Customer_Choose_Option_Screen.class);
                intent.putExtra("email", email);

//                // SQL Lite
//                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                ContentValues cv = new ContentValues();
//                cv.put(MyDBHelper.COLUMN_NAME, hotelNameString);
//                cv.put(MyDBHelper.COLUMN_LOCATION, hotelLocation);
//                cv.put(MyDBHelper.COLUMN_CHECKIN, checkInDate);
//                cv.put(MyDBHelper.COLUMN_CHECKOUT, checkOutDate);
//                cv.put(MyDBHelper.COLUMN_TOTALPRICE, totalPrice.getText().toString());
//                cv.put(MyDBHelper.COLUMN_TOTALROOMS, totalRooms.getText().toString());
//                cv.put(MyDBHelper.COLUMN_ROOMS, reservation.getRoomNumbers());
//                cv.put(MyDBHelper.COLUMN_RESERVEDBY, email);
//                double tep = database.insert(MyDBHelper.TABLE_RESERVATIONS, null, cv);
//                database.close();
//                dbHelper.close();

                startActivity(intent);
                finish();
            }
        });
    }
}