package android.example.saloonpartner.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProfileActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView nameTxt, emailId, bycId, docName, docNo, address, city, basePrice, phoneNo, phoneOpt;



    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceProfile;
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
        setContentView(R.layout.activity_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        initialise();

        sharedPreferencesData();

        back();

        try {
            getProfileData();
        }
        catch (Exception e)
        {
            Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initialise() {
        backBtn = findViewById(R.id.backProfileBtn);
        nameTxt = findViewById(R.id.ownerNameTxt);
        emailId = findViewById(R.id.emailIdTxt);
        bycId = findViewById(R.id.bycIdTxt);
        docName = findViewById(R.id.govtIdName);
        docNo = findViewById(R.id.govtIdNoTxt);
        address = findViewById(R.id.addressTxt);
        city = findViewById(R.id.cityTxt);
        basePrice = findViewById(R.id.basePriceTxt);
        phoneNo = findViewById(R.id.phoneNoTxtProfile);
        phoneOpt = findViewById(R.id.phoneNoAddiTxtProfile);
    }

    private void sharedPreferencesData() {

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();





    }


    private void getProfileData() {

        referenceProfile= firebaseDatabase.getReference("hmvendor/" + phone);

        referenceProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTxt.setText(snapshot.child("ownerName").getValue(String.class));
                emailId.setText(snapshot.child("email").getValue(String.class));
                bycId.setText(snapshot.child("bycId").getValue(String.class));
                docName.setText(snapshot.child("docType").getValue(String.class));
                docNo.setText(snapshot.child("govtidno").getValue(String.class));
                address.setText(snapshot.child("address").getValue(String.class));
                city.setText(snapshot.child("city").getValue(String.class));
                basePrice.setText(snapshot.child("basePrice").getValue(String.class));
                phoneNo.setText(snapshot.child("phoneNo").getValue(String.class));
                phoneOpt.setText(snapshot.child("phoneNoAdd").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void back() {
        backBtn.setOnClickListener(v -> ProfileActivity.super.onBackPressed());
    }
}