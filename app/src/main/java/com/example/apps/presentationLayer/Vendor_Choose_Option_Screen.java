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

import com.squareup.picasso.Picasso;
import com.example.apps.R;
import com.example.apps.logicLayer.HRS;

import de.hdodenhof.circleimageview.CircleImageView;

public class Vendor_Choose_Option_Screen extends AppCompatActivity {
    RelativeLayout register_hotel, view_registered_hotels;
    CircleImageView dp;
    HRS hrs;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_choose_option_screen);

        register_hotel = findViewById(R.id.rl_register_hotel_button);

        dp = findViewById(R.id.display_pic);
        hrs = HRS.getInstance(Vendor_Choose_Option_Screen.this);

        sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbHelper = new MyDBHelper(this);

        String email = getIntent().getStringExtra("email");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(MyDBHelper.TABLE_VENDORS,
                new String[]{MyDBHelper.COLUMN_DP},
                MyDBHelper.COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String dpUrl = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_DP));
            Picasso.get().load(dpUrl).into(dp);
            cursor.close();
        }

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Vendor_Choose_Option_Screen.this)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("loggedIn", false);
                                editor.commit();
                                editor.apply();
                                Intent intent = new Intent(Vendor_Choose_Option_Screen.this, Splash_Screen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        register_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vendor_Choose_Option_Screen.this, Hotel_Registration_Screen.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


    }
}
