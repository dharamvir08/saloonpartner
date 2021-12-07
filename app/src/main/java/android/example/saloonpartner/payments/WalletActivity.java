package android.example.saloonpartner.payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.example.saloonpartner.R;


import android.example.saloonpartner.common.Common;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalletActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView balanceTxt, debitBtn, creditBtn,bankDetailsBtn, helpSupportBtn;

    DatabaseReference referencePayment;
    FirebaseDatabase firebaseDatabase;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    String phone;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        firebaseDatabase = FirebaseDatabase.getInstance();


        initialize();

        back();
        sharedPreferencesData();

        buttonActions();


        getCurrentBalance();

    }

    private void getCurrentBalance() {

        referencePayment = firebaseDatabase.getReference("payments/" + phone);

        referencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int balanceAmount = snapshot.child("balance").getValue(int.class);
                balanceTxt.setText("Available Balance ₹ " + balanceAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void buttonActions() {


        creditBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, CreditDetailsActivity.class);
            startActivity(intent);
        });


        debitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, DebitDetailsActivity.class);
            startActivity(intent);
        });

        helpSupportBtn.setOnClickListener(v ->
                Toast.makeText(WalletActivity.this, "Help and Support", Toast.LENGTH_SHORT).show());


    }

    private void back() {
        backBtn.setOnClickListener(v -> WalletActivity.super.onBackPressed());

    }

    private void initialize() {
        backBtn = findViewById(R.id.backWalletBtn);
        balanceTxt = findViewById(R.id.availableBalanceTxt);
        debitBtn = findViewById(R.id.debitWalletBtn);
        creditBtn = findViewById(R.id.creditWalletBtn);
        helpSupportBtn = findViewById(R.id.helpWalletBtn);
    }

    private void sharedPreferencesData() {

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();


        Common.phoneNo = phone;



    }

}