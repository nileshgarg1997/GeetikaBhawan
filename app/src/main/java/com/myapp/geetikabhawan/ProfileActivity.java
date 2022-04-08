package com.myapp.geetikabhawan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class ProfileActivity extends AppCompatActivity {

    TextView name,email;
    ImageView customerImage;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Section");

        name=findViewById(R.id.customerName);
        email=findViewById(R.id.customerEmail);
        customerImage=findViewById(R.id.customerImage);

        account= GoogleSignIn.getLastSignedInAccount(this);
        name.setText(account.getDisplayName());
        email.setText(account.getEmail());
        Glide.with(this).load(account.getPhotoUrl()).placeholder(R.drawable.avatar_image).into(customerImage);
    }

    public void customerImage(View view) {

        String customerPic=account.getPhotoUrl().toString();
        Intent intent=new Intent(ProfileActivity.this, FullImageView.class);
        intent.putExtra("image",customerPic);
        startActivity(intent);
    }
}