package com.example.saad.jspart3;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class JobSeekicious extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView Name;
    TextView email;
    ImageView image;

    Firebase ref;
    Intent mydata;
    String package_key;

    Button _btn_search;
    Button _btn_post;

    Intent myintent;
    String email_key;
    String img_src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seekicious);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        try {
            _btn_search = (Button) findViewById(R.id.btn1);
            _btn_post = (Button) findViewById(R.id.btn2);
        }
        catch (Exception ex){

        }
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);



        myintent = getIntent();
        email_key = myintent.getStringExtra("email");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        main_fragment MainFragment = new main_fragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_profile, MainFragment, "Main Screen");
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.job_seekicious, menu);


        Name  = (TextView)findViewById(R.id._header_name);
        email = (TextView)findViewById(R.id._header_email);
        image  = (ImageView) findViewById(R.id._header_imageView);

        mydata = getIntent();
        package_key = mydata.getStringExtra("email");


        ref = new Firebase("https://js-part-3.firebaseio.com/");
        Firebase sign_up = ref.child("SignUp_Database");
        Firebase firebase_data = sign_up.child(package_key.replace(".", "/"));
        firebase_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String real_first_name = map.get("First_Name");
                String real_last_name = map.get("Last_Name");
                String real_email = map.get("Email_Address");
                String real_image = map.get("Image_URL");
                Name.setText(real_first_name+ " " + real_last_name);
                email.setText(real_email);
                Picasso.with(JobSeekicious.this).load(real_image).into(image);
                img_src = real_image;
                //Log.d("data", " "+real_first_name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nIntent = new Intent(JobSeekicious.this, DP_Activity.class);
                nIntent.putExtra("email",img_src);
                startActivity(nIntent);
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            main_fragment MainFragment = new main_fragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, MainFragment, "Main Screen");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_profile) {
            MyProfile my_profile = new MyProfile();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, my_profile, "My Profile");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_favourite) {
            MyFavourite my_favourite = new MyFavourite();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, my_favourite, "My Favourite");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_subscription) {
            MySubscription my_subscription = new MySubscription();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, my_subscription, "My Subscription");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_inbox) {
            MyInbox my_inbox = new MyInbox();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, my_inbox, "My Inbox");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_cv) {
            MyCV my_cv = new MyCV();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, my_cv, "My CV");
            fragmentTransaction.commit();

        }
    else if (id == R.id.nav_aboutus) {
            AboutUs about_us = new AboutUs();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.user_profile, about_us, "About us");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_logout) {
            Pop pop=new Pop();
            pop.type();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class Pop {
        public void type() {
            AlertDialog.Builder ab = new AlertDialog.Builder(JobSeekicious.this);
            ab.setMessage("Do you want to exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = ab.create();
            alert.setTitle("Exit");
            alert.show();

        }
    }
    public void SearchJob (View view){
        try {

                    Intent myintent = new Intent(JobSeekicious.this, SearchJob1.class);
                    myintent.putExtra("email", email_key);
                    startActivity(myintent);

        }
        catch (Exception ex){
            Log.d("Exception: "," "+ex.getMessage());
        }
    }
    public void PostJob (View view){
        try {

                    Intent myintent = new Intent(JobSeekicious.this, PostJob1.class);
            myintent.putExtra("email", email_key);
                    startActivity(myintent);

        }
        catch (Exception ex){
            Log.d("Exception: "," "+ex.getMessage());
        }
    }


}
