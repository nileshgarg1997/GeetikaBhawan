package com.myapp.geetikabhawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.geetikabhawan.bookings.BookingActivity;
import com.myapp.geetikabhawan.packages.BudgetActivity;
import com.myapp.geetikabhawan.packages.CustomizeActivity;
import com.myapp.geetikabhawan.packages.MaharajaActivity;
import com.myapp.geetikabhawan.packages.SumptuousActivity;
import com.myapp.geetikabhawan.services.EventActivity;
import com.myapp.geetikabhawan.services.PartyActivity;
import com.myapp.geetikabhawan.services.ReceptionActivity;
import com.myapp.geetikabhawan.services.WeddingActivity;
import com.myapp.geetikabhawan.ui.aboutus.AboutUsFragment;
import com.myapp.geetikabhawan.ui.gallery.GalleryFragment;
import com.myapp.geetikabhawan.ui.home.HomeFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_GeetikaBhawan);
        setContentView(R.layout.activity_main);

        if(!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.fragment_id);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        bottomNavigationView.setItemIconTintList(null);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }

    public boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context context){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Dear user you are not connected to Internet.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_button,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.bookBtn:
                Intent i=new Intent(MainActivity.this, BookingActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Uri uri;
        Intent i;
        Fragment fragment;
        FragmentManager fragmentManager;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_id, fragment).commit();
                drawerLayout.closeDrawers();

                break;


            case R.id.navigation_aboutus:
                fragment = new AboutUsFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_id, fragment).commit();

                drawerLayout.closeDrawers();


                break;
            case R.id.navigation_gallery:
                fragment = new GalleryFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_id, fragment).commit();

                drawerLayout.closeDrawers();

                break;

            case R.id.navigation_myProfile:
                i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                break;

            case R.id.navigation_services:
                Toast.makeText(this, "Services", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_wedding:
                i = new Intent(MainActivity.this, WeddingActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_reception:
                i = new Intent(MainActivity.this, ReceptionActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_party:
                i = new Intent(MainActivity.this, PartyActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_event:
                i = new Intent(MainActivity.this, EventActivity.class);
                startActivity(i);
                break;


            case R.id.navigation_packages:
                Toast.makeText(this, "Package", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_maharaja:
                i = new Intent(MainActivity.this, MaharajaActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_budget:
                i = new Intent(MainActivity.this, BudgetActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_customize:
                i = new Intent(MainActivity.this, CustomizeActivity.class);
                startActivity(i);
                break;
            case R.id.navigation_sumptuous:
                i = new Intent(MainActivity.this, SumptuousActivity.class);
                startActivity(i);
                break;


            case R.id.navigation_website:
                drawerLayout.closeDrawers();

                uri = Uri.parse("http://geetika.in/");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));

                break;
            case R.id.navigation_share:
                drawerLayout.closeDrawers();

                i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_SUBJECT, "Share");
                String shareMessage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                i.putExtra(i.EXTRA_TEXT, shareMessage);
                Intent chooser = Intent.createChooser(i, "Share the App using...");
                startActivity(chooser);
                break;
            case R.id.navigation_rateus:
                drawerLayout.closeDrawers();

                uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                i = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to open!!!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_developer:
                i = new Intent(MainActivity.this, DeveloperActivity.class);
                startActivity(i);
                break;

            case R.id.navigation_logout:

                FirebaseAuth.getInstance().signOut();
                i = new Intent(MainActivity.this, AuthenticationActivity.class);
                startActivity(i);
                Toast.makeText(this, "Logout Successfully!!!", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}