package com.example.apps.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.apps.R;

import java.util.ArrayList;
import java.util.List;

public class Reservations_Screen extends AppCompatActivity {

    RecyclerView rv;
    List<Hotel_row> ls;
    ImageView back_button;
    EditText searchEditText;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_screen);

        rv = findViewById(R.id.rv);
        searchEditText = findViewById(R.id.search_edit_text);
        back_button = findViewById(R.id.back_button);
        ls = new ArrayList<>();
        dbHelper = new MyDBHelper(this);

        String email = getIntent().getStringExtra("email");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(MyDBHelper.TABLE_RESERVATIONS,
                null,
                MyDBHelper.COLUMN_RESERVEDBY + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String hotelName = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_NAME));
                String hotelLocation = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_LOCATION));
                String roomNumbers = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_ROOMS));
                String checkInDate = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CHECKIN));
                String checkOutDate = cursor.getString(cursor.getColumnIndex(MyDBHelper.COLUMN_CHECKOUT));
                ls.add(new Hotel_row(hotelName, hotelLocation, roomNumbers, checkInDate, checkOutDate));
            } while (cursor.moveToNext());
            cursor.close();
        }

        //Adapter
        Hotel_row_adapter adapter = new Hotel_row_adapter(ls, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
