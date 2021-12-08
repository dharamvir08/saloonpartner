package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.profile.ProfileActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BankDetailsActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView nameTxt,accountNoTxt,ifscCodeTxt,branchTxt,bankNameTxt,accTypeTxt;
    DatabaseReference referenceBank;
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
        setContentView(R.layout.activity_bank_details);

        firebaseDatabase = FirebaseDatabase.getInstance();

        backBtn = findViewById(R.id.backBankDetailsBtn);
        initialise();
        sharedPreferencesData();
        getBankData();

        back();

    }

    private void initialise() {
        nameTxt = findViewById(R.id.benefciaryNameTxt);
        accountNoTxt =findViewById(R.id.accNoTxt);
        ifscCodeTxt =findViewById(R.id.ifscCodeTxt);
        branchTxt =findViewById(R.id.branchTxt);
        bankNameTxt =findViewById(R.id.bankNameTxt);
        accTypeTxt =findViewById(R.id.accTypeTxt);

    }

    private void back() {
        backBtn.setOnClickListener(v -> BankDetailsActivity.super.onBackPressed());
    }
    private void sharedPreferencesData() {

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();


        Common.phoneNo = phone;



    }


    private void getBankData() {

        referenceBank = firebaseDatabase.getReference("hmvendor/" + phone);

   referenceBank.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           nameTxt.setText(snapshot.child("benificaryname").getValue(String.class));
           accountNoTxt.setText(snapshot.child("accountNo").getValue(String.class));
           ifscCodeTxt.setText(snapshot.child("ifsccode").getValue(String.class));
           branchTxt.setText(snapshot.child("bankadress").getValue(String.class));
           bankNameTxt.setText(snapshot.child("bankname").getValue(String.class));
           accTypeTxt.setText(snapshot.child("accounttype").getValue(String.class));
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {
           Toast.makeText(BankDetailsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
       }
   });

    }


}