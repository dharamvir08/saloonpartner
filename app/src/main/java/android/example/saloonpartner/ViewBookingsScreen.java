package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.saloonpartner.adapter.BookingAdapter;

import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.BookingModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingsScreen extends AppCompatActivity {
ProgressBar progressBar;
ImageView backBtn;
RecyclerView recyclerView;

FirebaseDatabase firebaseDatabase;
DatabaseReference reference;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings_screen);
        initialise();


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("bookings");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        back();


        showBookings();
    }

    private void back() {
        backBtn.setOnClickListener(v -> {
//                onBackPressed();
            ViewBookingsScreen.super.onBackPressed();
        });

    }


    private void initialise() {
        progressBar = findViewById(R.id.progressBar_bookingS);
        backBtn = findViewById(R.id.backBtn2);
        recyclerView = findViewById(R.id.showBookingsRecyclerView);

    }


    private void showBookings() {

            List<BookingModel> list = new ArrayList<>();

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();

                    for (DataSnapshot sp : snapshot.getChildren()) {
                        BookingModel bookingModel = sp.getValue(BookingModel.class);


                        assert bookingModel != null;

                        String phone = Common.currentRecord.getPhoneNo();
                        if(bookingModel.getBycPhone().equals(phone)) {

                            bookingModel.setBookingId(sp.getKey());
                            list.add(bookingModel);
                        }

                    }
                    BookingAdapter bookingAdapter = new BookingAdapter(list, ViewBookingsScreen.this);
                    recyclerView.setAdapter(bookingAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




    }




}