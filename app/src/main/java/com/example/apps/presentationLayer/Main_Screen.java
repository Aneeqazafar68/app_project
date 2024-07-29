package com.example.apps.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.apps.R;

public class Main_Screen extends AppCompatActivity {

    RelativeLayout customer;
    RelativeLayout vendor;
    String Page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // corrected this line
        setContentView(R.layout.activity_main_screen);

        customer = findViewById(R.id.rl_customer_button);
        vendor = findViewById(R.id.rl_vendor_button);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = "Customer";
                Intent intent = new Intent(Main_Screen.this, Login_Screen.class);
                intent.putExtra("Page", Page);
                startActivity(intent);
            }
        });
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = "Vendor";
                Intent intent = new Intent(Main_Screen.this, Login_Screen.class);
                intent.putExtra("Page", Page);
                startActivity(intent);
            }
        });
    }
}