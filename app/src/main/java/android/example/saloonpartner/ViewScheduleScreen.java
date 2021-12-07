package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.saloonpartner.adapter.SlotsAdapter;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.SlotsModel;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewScheduleScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView backBtn,refreshBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    TextView phoneTxt;
    String phone;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule_screen);



        initialize();

        firebaseReference();

        getStatus();
        back();
        refresh();


    }

    private void firebaseReference() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        phone = Common.currentRecord.getPhoneNo();
        phoneTxt.setText(phone);
        reference = firebaseDatabase.getReference("hmvendor").child(phone).child("days");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void refresh() {
        refreshBtn.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");

            Calendar d = Calendar.getInstance();
            for(int i=-1;i>-20;i--) {
                d.add(Calendar.DAY_OF_MONTH, (i));
                checkExistence(sdf.format(d.getTime()));


            }

                Intent intent = new Intent(ViewScheduleScreen.this, ViewScheduleScreen.class);
                startActivity(intent);
                finish();

        });


    }

    private void checkExistence(String sDate) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("hmvendor").child(phone).child("days").child(sDate);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                  dataSnapshot.getRef().removeValue();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("dbaerror", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
    private void back() {

        backBtn.setOnClickListener(v -> finish());
    }

    private void initialize() {

        backBtn =findViewById(R.id.backViewBookingsScreen);
        phoneTxt = findViewById(R.id.phoneTxtBscreen);
        refreshBtn =findViewById(R.id.refreshBtn);
        recyclerView = findViewById(R.id.bookingsRecyclerView);
        progressBar = findViewById(R.id.viewBookingsProgressbar);


    }

    private void getStatus() {
        List<SlotsModel> list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot sp : snapshot.getChildren()) {
                    SlotsModel slotsModel = sp.getValue(SlotsModel.class);


                    assert slotsModel != null;
                    slotsModel.setDateId(sp.getKey());

                    slotsModel.setDateId(sp.getKey());
                    list.add(slotsModel);

                }
                SlotsAdapter slotsAdapter = new SlotsAdapter(list, ViewScheduleScreen.this);
                recyclerView.setAdapter(slotsAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}