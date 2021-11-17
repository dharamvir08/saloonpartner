package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.content.SharedPreferences;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.HomeVendorModel;
import android.example.saloonpartner.model.SlotsModel;


import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button logoutBtn, viewBookingsButton;
    DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView phoneTxt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private String phone, datesList = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor").child(phone);

        //Initialize
        initialize();

        //Buttons
        logout();
        viewBookings();


        //navigation view
        setNavigationDrawer();

        getVendorData();

        setDays();


    }

    private void setDays() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        String getCurrentDate = sdf.format(c.getTime());

        checkExistence(getCurrentDate);

        Calendar d = Calendar.getInstance();
        d.add(Calendar.DAY_OF_MONTH, (-1));



        //updating calender Schedule
        for (int i = 1; i < 7; i++)
        {
            c.add(Calendar.DAY_OF_MONTH, 1);
            String getDate = sdf.format(c.getTime());
            checkExistence(getDate);


        }


    }


    private void checkExistence(String sDate) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("hmvendor").child(phone).child("days").child(sDate);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    //Send Otp

                    System.out.println("Yes this np Exists");
                    datesList = datesList + " " + sDate + " Exists" + "\n";


                } else {
                    //User is not a partner
                    SlotsModel slotsModel = new SlotsModel();
                    slotsModel.setSlot1("Available");
                    slotsModel.setSlot2("Available");
                    slotsModel.setSlot3("Available");
                    slotsModel.setSlot4("Available");
                    slotsModel.setSlot5("Available");
                    slotsModel.setSlot6("Available");
                    reference.child("days").child(sDate).setValue(slotsModel);
                    Toast.makeText(MainActivity.this, "UpDated", Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("dbaerror", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }


    private void getVendorData() {


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HomeVendorModel homeVendorModel = snapshot.getValue(HomeVendorModel.class);

                assert homeVendorModel != null;
                phoneTxt.setText(homeVendorModel.getPhoneNo());
                Common.currentRecord = homeVendorModel;
                progressBar.setVisibility(View.GONE);
                viewBookingsButton.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private void initialize() {

        drawerLayout = findViewById(R.id.my_drawer_layout);
        logoutBtn = findViewById(R.id.logoutBtn);
        viewBookingsButton = findViewById(R.id.viewBookingButton);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        phoneTxt = findViewById(R.id.phoneNoMain);


        progressBar = findViewById(R.id.loadingProgressBar);


    }

    private void logout() {
        logoutBtn.setOnClickListener(v -> {
            editor.clear();
            editor.apply();
            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(intent);
            finish();

        });
    }

    private void viewBookings() {
        viewBookingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewBookingsScreen.class);
            startActivity(intent);
        });
    }


    private void setNavigationDrawer() {
        /*Declaration*/

        NavigationView navigationView = findViewById(R.id.nav_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //      View view = navigationView.getHeaderView(0);


//        ImageView uImage = view.findViewById(R.id.nav_imag);
//        TextView uName = view.findViewById(R.id.nav_name);


        // implement setNavigationItemSelectedListener event on NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_profile) {
//                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
//                    startActivity(intent);
//                    return true;
                System.out.println("Profile");
            } else if (item.getItemId() == R.id.nav_settings) {
                System.out.println("nav_Settings selected");
//                    Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
//                    startActivity(intent);
//                    return true;
            } else {
                System.out.println("Else other item selected");
            }
            return false;
        });


    }


}