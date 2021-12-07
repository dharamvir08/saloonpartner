package android.example.saloonpartner.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.example.saloonpartner.R;
import android.os.Bundle;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView backBtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backBtn = findViewById(R.id.backProfileBtn);

        back();

    }
    private void back() {
        backBtn.setOnClickListener(v -> ProfileActivity.super.onBackPressed());
    }
}