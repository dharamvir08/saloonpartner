package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.BookingModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingDetailsActivity extends AppCompatActivity {

    TextView bookingId, userName, userCity, userAddress, userPhone, itemList, pricePaid, timeSlot, dateSlot, status, thanksTxt;
    ImageView backBtn;
    Button onWayBtn, reachedBtn, doneBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    private  String previousTracking, formattedDate;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        firebaseDatabase = FirebaseDatabase.getInstance();
        String bid = Common.currentBooking.getBookingId();


        reference = firebaseDatabase.getReference("bookings/" + bid);
        //referenceTracking = firebaseDatabase.getReference("bookings/" + bid+"");


        initialise();

        back();

        getUserData();
        references();

        setTracking();





    }

    private void references() {
        reference.child("trackingList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 previousTracking = snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
         formattedDate = df.format(c.getTime());



    }

    private void setTracking() {
        String bookingStatus = Common.currentBooking.getStatus();
        String pending = "Pending";
        String onTheWay = "On the Way to your destination";
        String reached = "Reached at  Destination";
        String done = "Done Successfully";

        if (bookingStatus.equalsIgnoreCase(pending)) {
            onWayBtn.setVisibility(View.VISIBLE);
            reachedBtn.setVisibility(View.GONE);
            doneBtn.setVisibility(View.GONE);
            thanksTxt.setVisibility(View.GONE);

        } else if (bookingStatus.equalsIgnoreCase(onTheWay)) {
            onWayBtn.setVisibility(View.GONE);
            reachedBtn.setVisibility(View.VISIBLE);
            doneBtn.setVisibility(View.GONE);
            thanksTxt.setVisibility(View.GONE);


        } else if (bookingStatus.equalsIgnoreCase(reached)) {
            onWayBtn.setVisibility(View.GONE);
            reachedBtn.setVisibility(View.GONE);
            doneBtn.setVisibility(View.VISIBLE);
            thanksTxt.setVisibility(View.GONE);


        } else if (bookingStatus.equalsIgnoreCase(done)) {

            onWayBtn.setVisibility(View.GONE);
            reachedBtn.setVisibility(View.GONE);
            doneBtn.setVisibility(View.GONE);
            thanksTxt.setVisibility(View.VISIBLE);

        }


        onWayBtn.setOnClickListener(v -> {

            Common.currentBooking.setStatus(onTheWay);

            previousTracking = previousTracking+"\n"+onTheWay+"   "+formattedDate;
            Common.currentBooking.setTrackingList(previousTracking);


            reference.child("trackingList").setValue(previousTracking);
            reference.child("status").setValue(onTheWay);
            recreate();


        });


        reachedBtn.setOnClickListener(v -> {
            Common.currentBooking.setStatus(reached);
            reference.child("status").setValue(reached);

            previousTracking = previousTracking+"\n"+reached+"   "+formattedDate;
            Common.currentBooking.setTrackingList(previousTracking);


            reference.child("trackingList").setValue(previousTracking);


            recreate();

        });


        doneBtn.setOnClickListener(v -> {
            Common.currentBooking.setStatus(done);

            reference.child("status").setValue(done);


            previousTracking = previousTracking+"\n"+done+"   "+formattedDate;
            Common.currentBooking.setTrackingList(previousTracking);


            reference.child("trackingList").setValue(previousTracking);


            recreate();
        });

    }

    private void getUserData() {
        //get and set data into textViews
        String bookingIdS = Common.currentBooking.getBookingId();
        String userNameS = Common.currentBooking.getUserName();
        String userAddressS = Common.currentBooking.getUserAddress();
        String userCityS = Common.currentBooking.getUserCity();
        String userPhoneS = Common.currentBooking.getUserPhone();
        String itemListS = Common.currentBooking.getItemList();
        String pricePaidS = Common.currentBooking.getPricePaid();
        String timeSlotS = Common.currentBooking.getTimeSlot();
        String dateSlotS = Common.currentBooking.getDateSlot();
        String statusS = Common.currentBooking.getStatus();

        bookingId.setText(bookingIdS);
        userName.setText(userNameS);
        userAddress.setText(userAddressS);
        userCity.setText(userCityS);
        userPhone.setText(userPhoneS);
        itemList.setText(itemListS);
        pricePaid.setText(pricePaidS);
        timeSlot.setText(timeSlotS);
        bookingId.setText(bookingIdS);
        dateSlot.setText(dateSlotS);
        status.setText(statusS);




    }

    private void back() {
        backBtn.setOnClickListener(v -> BookingDetailsActivity.super.onBackPressed());
    }

    private void initialise() {
        //back icon imageView
        backBtn = findViewById(R.id.backBookingDetailsBtn);

        //text Views of user data
        bookingId = findViewById(R.id.bookingIdDt);
        userName = findViewById(R.id.userNameDt);
        userAddress = findViewById(R.id.userAddressDt);
        userCity = findViewById(R.id.userCityDt);
        userPhone = findViewById(R.id.userPhoneDt);
        itemList = findViewById(R.id.itemsListDt);
        pricePaid = findViewById(R.id.pricePaidDt);
        timeSlot = findViewById(R.id.timeSlotDt);
        dateSlot = findViewById(R.id.dateSlotDt);
        status = findViewById(R.id.statusDt);

        onWayBtn = findViewById(R.id.onTheWayBtn);
        reachedBtn = findViewById(R.id.reachedBtn);
        doneBtn = findViewById(R.id.doneBtn);
        thanksTxt = findViewById(R.id.thanksTxt);
        //Status Buttons
        onWayBtn = findViewById(R.id.onTheWayBtn);
        reachedBtn = findViewById(R.id.reachedBtn);
        doneBtn = findViewById(R.id.doneBtn);



    }
}