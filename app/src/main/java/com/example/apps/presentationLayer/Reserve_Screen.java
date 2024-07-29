package com.example.apps.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.apps.R;
import com.example.apps.logicLayer.HRS;
import com.example.apps.logicLayer.Hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;

public class Reserve_Screen extends AppCompatActivity {

    ImageView backbutton;
    EditText location, persons, checkinDate, checkoutDate;
    CheckBox single_room, double_room;
    RelativeLayout submitButton;
    HRS hrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_screen);

        backbutton = findViewById(R.id.back_button);
        location = findViewById(R.id.Location_text);
        persons = findViewById(R.id.Persons_text);
        checkinDate = findViewById(R.id.Check_in_date);
        checkoutDate = findViewById(R.id.Check_out_date);
        submitButton = findViewById(R.id.submit_button);
        single_room = findViewById(R.id.Single_box);
        double_room = findViewById(R.id.Double_box);
        hrs = HRS.getInstance(Reserve_Screen.this);

        backbutton.setOnClickListener(view -> onBackPressed());

        submitButton.setOnClickListener(view -> {
            String Location = location.getText().toString();
            String Persons = persons.getText().toString();
            String CheckinDate = checkinDate.getText().toString();
            String CheckoutDate = checkoutDate.getText().toString();

            if (Location.isEmpty() || Persons.isEmpty() || CheckinDate.isEmpty() || CheckoutDate.isEmpty() ||
                    (!single_room.isChecked() && !double_room.isChecked())) {
                Toast.makeText(Reserve_Screen.this, "Fill All Boxes Correctly.", Toast.LENGTH_LONG).show();
            } else {
                boolean single = false;
                boolean doub = false;
                boolean both = false;
                String TypeRoom = "";
                if (single_room.isChecked()) {
                    single = true;
                    TypeRoom = "Single";
                }
                if (double_room.isChecked()) {
                    doub = true;
                    TypeRoom = "Double";
                }
                if (single && doub) {
                    both = true;
                    TypeRoom = "";
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate localDate = LocalDate.parse(CheckinDate, formatter);
                    LocalDate localDate1 = LocalDate.parse(CheckoutDate, formatter);

                    Vector<Hotel> hotels = hrs.getHotels(Location, Persons, localDate, TypeRoom, both);
                    if (hotels.isEmpty()) {
                        Toast.makeText(Reserve_Screen.this, "No Hotels Found", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Reserve_Screen.this, Hotel_Selection.class);
                        intent.putExtra("Location", Location);
                        intent.putExtra("Persons", Persons);
                        intent.putExtra("localDate", CheckinDate);
                        intent.putExtra("checkoutDate", CheckoutDate);
                        intent.putExtra("TypeRoom", TypeRoom);
                        intent.putExtra("both", both);
                        intent.putExtra("Email", getIntent().getStringExtra("email"));
                        startActivity(intent);
                    }
                } catch (DateTimeParseException e) {
                    Toast.makeText(Reserve_Screen.this, "Kindly enter dates in correct format (dd/MM/yyyy)", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(Reserve_Screen.this, "An unexpected error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
