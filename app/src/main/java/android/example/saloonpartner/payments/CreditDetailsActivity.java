package android.example.saloonpartner.payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.example.saloonpartner.R;

import android.example.saloonpartner.common.Common;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreditDetailsActivity extends AppCompatActivity {
    TextView creditListTxt;
    ImageView backBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_details);

        initialise();
        getCreditDetails();

        back();


    }

    private void back() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditDetailsActivity.super.onBackPressed();
            }
        });

    }


    private void initialise() {
        creditListTxt = findViewById(R.id.creditListTxt);
        backBtn = findViewById(R.id.backCreditBtn);



    }

    private void getCreditDetails() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        String vendorPhone = Common.phoneNo;
        reference = firebaseDatabase.getReference("payments/" + vendorPhone);


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                creditListTxt.setText(snapshot.child("credit").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}