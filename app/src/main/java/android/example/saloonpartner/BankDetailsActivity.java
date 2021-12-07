package android.example.saloonpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.example.saloonpartner.profile.ProfileActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class BankDetailsActivity extends AppCompatActivity {

    ImageView backBtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        backBtn = findViewById(R.id.backBankDetailsBtn);

        back();

    }
    private void back() {
        backBtn.setOnClickListener(v -> BankDetailsActivity.super.onBackPressed());
    }

}