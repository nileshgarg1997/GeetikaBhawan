package com.myapp.geetikabhawan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class FullImageView extends AppCompatActivity {

    private  PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_full_image_view);

        photoView = findViewById(R.id.photoView);

        String image = getIntent().getStringExtra("image");
//        Glide.with(this).load(image).into(photoView);             For loading image using glide dependency
        Picasso.get().load(image).into(photoView);
    }
}