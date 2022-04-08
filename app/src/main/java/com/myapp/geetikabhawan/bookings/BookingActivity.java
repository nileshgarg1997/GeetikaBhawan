package com.myapp.geetikabhawan.bookings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myapp.geetikabhawan.MainActivity;
import com.myapp.geetikabhawan.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements PaymentResultListener {

    private final int REQ = 1;
    TextView dateTextView;
    EditText addCustomerName, addCustomerMobile;
    Spinner bookingCategory;
    String category, date, uniqueKey, availability = "",downloadUrl = "";
    String name, mobile, bookingDate, email;
    private Bitmap bitmap = null;
    ImageView addCustomerImage;
    LinearLayout datePickerLinearLayout;
    GoogleSignInAccount account;

    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setTitle("Booking Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTextView = findViewById(R.id.dateTextView);
        datePickerLinearLayout = findViewById(R.id.datePicker);
        bookingCategory = findViewById(R.id.bookingCategory);
        addCustomerImage = findViewById(R.id.addCustomerImage);
        addCustomerName = findViewById(R.id.addCustomerName);
        addCustomerMobile = findViewById(R.id.addCustomerMobile);

        account= GoogleSignIn.getLastSignedInAccount(this);
        email=account.getEmail();
        addCustomerName.setText(account.getDisplayName());
        Glide.with(this).load(account.getPhotoUrl()).placeholder(R.drawable.avatar_image).into(addCustomerImage);

        storageReference = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        Checkout.preload(getApplicationContext());

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String[] items = new String[]{"--Select Category--", "Wedding", "Reception", "Party", "Event"};
        bookingCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        bookingCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                category = bookingCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void datePicker(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = dayOfMonth + "/" + month + "/" + year;
                dateTextView.setText(date);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void checkAvailability(View view) {

        String dateTv=dateTextView.getText().toString();
        if (dateTv.equals("--Tap to Select--")){
            Toast.makeText(this, "Please Choose Date!!!", Toast.LENGTH_SHORT).show();
        }else {
            dbRef = reference.child("Customers");
            uniqueKey = dbRef.push().getKey();
            dbRef.orderByChild("bookingDate").equalTo(date).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String key = dataSnapshot.getKey();

                            if (key.equals(uniqueKey)) {

                                Toast.makeText(BookingActivity.this, "Booking Confirmed!!!", Toast.LENGTH_SHORT).show();

                            } else {
                                availability = "Unavailable";
                                Toast.makeText(BookingActivity.this, "Sorry! Selected Date is Unavailable. Please select another one!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        availability = "Available";
                        Toast.makeText(BookingActivity.this, "Great! The date is Available!!!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void addCustomerImage(View view) {

        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addCustomerImage.setImageBitmap(bitmap);
        }
    }

    public void proceedToCheckout(View view) {

        if (availability.equals("")) {
            Toast.makeText(this, "Please check the availability first!!!", Toast.LENGTH_LONG).show();
        } else {

            checkValidation();
        }

    }

    private void checkValidation() {

        name = addCustomerName.getText().toString();
        mobile = addCustomerMobile.getText().toString();
        bookingDate = dateTextView.getText().toString();

        if (name.isEmpty()) {
            addCustomerName.setError("Empty");
            addCustomerName.requestFocus();
        } else if (mobile.isEmpty()) {
            addCustomerMobile.setError("Empty");
            addCustomerMobile.requestFocus();
        } else if (bookingDate.equals("--Tap to Select--")) {
            Toast.makeText(this, "Please Choose Date!!!", Toast.LENGTH_SHORT).show();
            dateTextView.requestFocus();
        } else if (availability.equals("Unavailable")) {
            Toast.makeText(this, "Please change the date & check availability!!!", Toast.LENGTH_LONG).show();
            dateTextView.requestFocus();
        } else if (category.equals("--Select Category--")) {
            Toast.makeText(this, "Please Select Category!!!", Toast.LENGTH_SHORT).show();
            bookingCategory.requestFocus();
        } else if (bitmap == null) {
//            progressDialog.setMessage("Confirming...");
//            progressDialog.show();
            makePayment();
        } else {
//            progressDialog.setMessage("Confirming...");
//            progressDialog.show();
            makePayment();
        }
    }

    private void makePayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_wxDRGXH6b5ODbe");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.payment_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Geetika Bhawan");
            options.put("description", "Banquet Hall Booking Payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "512100");//pass amount in currency subunits

            JSONObject prefill=new JSONObject();
            prefill.put("contact",mobile);
            prefill.put("email",email);
            options.put("prefill",prefill);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Customers").child(finalimg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(BookingActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
//                    progressDialog.dismiss();
                    Toast.makeText(BookingActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertData() {

        dbRef = reference.child("Customers");
//        uniqueKey = dbRef.push().getKey();

        BookingData bookingData = new BookingData(name, mobile, category, bookingDate, downloadUrl, uniqueKey);

        dbRef.child(uniqueKey).setValue(bookingData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

//                progressDialog.dismiss();
                Toast.makeText(BookingActivity.this, "Booking Confirmed!!!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BookingActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

//                progressDialog.dismiss();

                Toast.makeText(BookingActivity.this, "Something went wrong!!!Please contact us", Toast.LENGTH_LONG).show();
                Intent i = new Intent(BookingActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {

        if (bitmap == null) {

            insertData();
        }else {

            uploadImage();
        }


    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Payment Error: "+s, Toast.LENGTH_SHORT).show();

    }

}