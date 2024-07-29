package com.example.apps.presentationLayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.apps.R;
import com.example.apps.dataLayer.VolleyCallBack;
import com.example.apps.logicLayer.HRS;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Screen extends AppCompatActivity {
    String Page;
    ImageView backButton, addDisplayPic;
    CircleImageView dp;
    EditText name, email, contact, card, cnic, address, password;
    RelativeLayout signup_Button;
    HRS hrs;
    Uri imageURI = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        Page = getIntent().getStringExtra("Page");
        backButton = findViewById(R.id.back_button);
        name = findViewById(R.id.Name_text);
        email = findViewById(R.id.Email_text);
        contact = findViewById(R.id.Contact_text);
        cnic = findViewById(R.id.CNIC_text);
        address = findViewById(R.id.Address_text);
        password = findViewById(R.id.Password_text);
        card = findViewById(R.id.Card_text);
        signup_Button = findViewById(R.id.sign_up_button);
        hrs = HRS.getInstance(Register_Screen.this);
        addDisplayPic = findViewById(R.id.add_display_pic);
        dp = findViewById(R.id.display_pic);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbHelper = new MyDBHelper(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim();
                String Cnic = cnic.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Contact = contact.getText().toString().trim();
                String Address = address.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Card = card.getText().toString().trim();

                if (Name.isEmpty() || Cnic.isEmpty() || Password.isEmpty() || Card.isEmpty() || Address.isEmpty() || Contact.isEmpty() || Email.isEmpty()) {
                    Toast.makeText(Register_Screen.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if (imageURI == null) {
                    Toast.makeText(Register_Screen.this, "Please select an image", Toast.LENGTH_LONG).show();
                } else if (!isValidName(Name)) {
                    Toast.makeText(Register_Screen.this, "Please enter a valid name", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(Email)) {
                    Toast.makeText(Register_Screen.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                } else if (!isValidCNIC(Cnic)) {
                    Toast.makeText(Register_Screen.this, "Please enter a valid 13-digit CNIC", Toast.LENGTH_LONG).show();
                } else if (!isValidContact(Contact)) {
                    Toast.makeText(Register_Screen.this, "Please enter a valid 11-digit contact number", Toast.LENGTH_LONG).show();
                } else if (!isValidCard(Card)) {
                    Toast.makeText(Register_Screen.this, "Please enter a valid 16-digit credit card number", Toast.LENGTH_LONG).show();
                } else if (!isValidPassword(Password)) {
                    Toast.makeText(Register_Screen.this, "Password must be at least 6 characters long", Toast.LENGTH_LONG).show();
                } else {
                    if (Page.equals("Customer")) {
                        if (!hrs.validateCustomerEmail(Email)) {
                            Toast.makeText(Register_Screen.this, "Account with this Email / Phone no Already Exists", Toast.LENGTH_LONG).show();
                        } else {
                            ProgressDialog pd = new ProgressDialog(Register_Screen.this);
                            pd.setMessage("Loading");
                            pd.setCancelable(false);
                            pd.show();

                            hrs.registerCustomer(Name, Email, Password, Address, Contact, Cnic, Card, imageURI, new VolleyCallBack() {
                                @Override
                                public void onSuccess() {
                                    pd.dismiss();
                                    editor.putString("user", "Customer");
                                    editor.putString("email", Email);
                                    editor.putBoolean("loggedIn", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent intent = new Intent(Register_Screen.this, Customer_Choose_Option_Screen.class);
                                    intent.putExtra("email", Email);

                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(MyDBHelper.COLUMN_EMAIL, Email);
                                    values.put(MyDBHelper.COLUMN_DP, imageURI.toString());

                                    db.insert(MyDBHelper.TABLE_CUSTOMERS, null, values);
                                    db.close();

                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    } else {
                        if (!hrs.validateVendorEmail(Email)) {
                            Toast.makeText(Register_Screen.this, "Account with this Email / Phone no Already Exists", Toast.LENGTH_LONG).show();
                        } else {
                            ProgressDialog pd = new ProgressDialog(Register_Screen.this);
                            pd.setMessage("Loading");
                            pd.setCancelable(false);
                            pd.show();
                            hrs.registerVendor(Name, Email, Password, Address, Contact, Cnic, Card, imageURI, new VolleyCallBack() {
                                @Override
                                public void onSuccess() {
                                    pd.dismiss();
                                    editor.putString("user", "Vendor");
                                    editor.putString("email", Email);
                                    editor.putBoolean("loggedIn", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent intent = new Intent(Register_Screen.this, Vendor_Choose_Option_Screen.class);
                                    intent.putExtra("email", Email);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                }
            }
        });

        addDisplayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            addDisplayPic.setAlpha((float) 0);
            imageURI = data.getData();
            dp.setImageURI(imageURI);
        }
    }


    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }
    private boolean isValidCNIC(String cnic) {
        return cnic.matches("\\d{13}");
    }
    private boolean isValidContact(String contact) {
        return contact.matches("\\d{11}");
    }
    private boolean isValidCard(String card) {
        return card.matches("\\d{16}");
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}
