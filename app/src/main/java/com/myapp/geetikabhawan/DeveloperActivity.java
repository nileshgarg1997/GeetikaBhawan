package com.myapp.geetikabhawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DeveloperActivity extends AppCompatActivity {

    DatabaseReference reference;
    ImageView myProfilePic;
    Uri uri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_developer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Developer Section");

        reference= FirebaseDatabase.getInstance().getReference().child("Developer");
        myProfilePic=findViewById(R.id.myProfilePic);
        progressDialog=new ProgressDialog(this);

        setProfilePic();
    }

    private void setProfilePic() {

        progressDialog.setMessage("Loading Profile...");
        progressDialog.show();
        reference.child("Nilesh Garg").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    uri=Uri.parse(data);

                }
                Picasso.get().load(uri).into(myProfilePic);
                progressDialog.dismiss();
//                String uri=snapshot.child("image").getValue().toString();         Another method for fetch the image
//                if (!uri.isEmpty()){                                              from firebase real-time database.
//                    Picasso.get().load(uri).into(myProfilePic);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void myProfilePic(View view) {

        String myPic=uri.toString();
        Intent intent=new Intent(DeveloperActivity.this, FullImageView.class);
        intent.putExtra("image",myPic);
        startActivity(intent);
    }

    public void instaclick(View view) {
        Uri url= Uri.parse("https://www.instagram.com/nileshgarg1997?r=nametag");
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }

    public void linkedinclick(View view) {

        Uri url= Uri.parse("https://www.linkedin.com/in/nilesh-garg-7ab3181b2");
        startActivity(new Intent(Intent.ACTION_VIEW, url));

    }
}