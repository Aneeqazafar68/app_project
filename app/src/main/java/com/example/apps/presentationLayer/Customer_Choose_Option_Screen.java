package com.example.apps.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.apps.R;
import com.example.apps.dataLayer.DatabaseHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Choose_Option_Screen extends AppCompatActivity {
    RelativeLayout reserve_hotel, view_old_reservations;
    CircleImageView dp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_choose_option_screen);

        reserve_hotel = findViewById(R.id.rl_reserve_hotel_button);
        view_old_reservations = findViewById(R.id.rl_view_old_reservations);
        dp = findViewById(R.id.display_pic);

        sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbHelper = new DatabaseHelper(this);

        String email = getIntent().getStringExtra("email");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_CUSTOMERS,
                new String[]{DatabaseHelper.COLUMN_DP},
                DatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String dpUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DP));
            Picasso.get().load(dpUrl).into(dp);
            cursor.close();
        }

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Customer_Choose_Option_Screen.this)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("loggedIn", false);
                                editor.commit();
                                editor.apply();
                                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Splash_Screen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        reserve_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Reserve_Screen.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        view_old_reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Reservations_Screen.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}