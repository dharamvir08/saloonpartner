package android.example.saloonpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class LoginScreen extends AppCompatActivity {
    TextInputLayout phoneLyt, otpLyt;
    EditText phoneNoTxtView, otpTxtView;
    ProgressBar progressBar;


    Button sendOtpBtn, verifyOtpBtn;

    TextView createAccTxt;

    private FirebaseAuth mAuth;
    private String otp, phoneNo, verificationId;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setStatusBarTransparent();
        mAuth = FirebaseAuth.getInstance();

        initializeE();
        sendButton();
        verifyButton();


        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        if (sharedPreferences.getBoolean("isLogin", false)) {

            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    private void initializeE() {
        phoneNoTxtView = findViewById(R.id.phoneNoTxtEdt);
        otpTxtView = findViewById(R.id.otpTxtEdt);
        sendOtpBtn = findViewById(R.id.sendOtpBtn);
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);

        phoneLyt = findViewById(R.id.phonenNoLayt);
        otpLyt = findViewById(R.id.otpLayt);
        progressBar =findViewById(R.id.login_progress_bar);
        createAccTxt = findViewById(R.id.createAcc);


    }

    private void sendButton() {
        sendOtpBtn.setOnClickListener(v -> {
            phoneNo = phoneNoTxtView.getText().toString();
            validateNo(phoneNo);


        });
    }

    private void verifyButton() {
        verifyOtpBtn.setOnClickListener(v -> {
            otp = otpTxtView.getText().toString();

            // validating if the OTP text field is empty or not.
            if (TextUtils.isEmpty(otp)) {
                // if the OTP text field is empty display
                // a message to user to enter OTP
                Toast.makeText(LoginScreen.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                // if OTP field is not empty calling
                // method to verify the OTP.
                try {
                    verifyCode(otp);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(LoginScreen.this, "Verification Code is wrong" + e.toString(), Toast.LENGTH_SHORT);
                    System.out.println("WrongVerifcation  :" + e.toString());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }


    private void validateNo(String phoneNo) {
        if (phoneNo.isEmpty()) {
            System.out.println("Please enter your registered number to continue");
            Toast.makeText(LoginScreen.this, "Please enter your registered number to continue", Toast.LENGTH_SHORT).show();
        } else if (phoneNo.length() < 10) {

            System.out.println("Please enter your valid registered number to continue");
            Toast.makeText(LoginScreen.this, "Please enter your valid 10 digit registered number to continue", Toast.LENGTH_SHORT).show();
        } else {
            phoneNoTxtView.setVisibility(View.GONE);
            phoneLyt.setVisibility(View.GONE);
            sendOtpBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            checkExistence(phoneNo);
        }
    }

    private void checkExistence(String phoneNo) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("hmvendor").child(phoneNo);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Send Otp
                    Toast.makeText(LoginScreen.this, "Yes this np Exists", Toast.LENGTH_SHORT).show();
                    System.out.println("Yes this np Exists");
                    sendOtpBtn.setVisibility(View.GONE);
                    sendVerificationCode("+91" + phoneNo);
                    Toast.makeText(LoginScreen.this, "Otp sent to " + phoneNo + " successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    otpLyt.setVisibility(View.VISIBLE);
                    otpTxtView.setVisibility(View.VISIBLE);
                    verifyOtpBtn.setVisibility(View.VISIBLE);


                } else {
                    //User is not a partner
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginScreen.this, "Nope  this np doesn't  Exists", Toast.LENGTH_SHORT).show();
                    System.out.println("Nope  this np doesn't  Exists");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginScreen.this, "Cancelled " + databaseError.toString(), Toast.LENGTH_SHORT).show();
                Log.d("dbaerror", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginScreen.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otpTxtView.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        editor.putBoolean("isLogin", true);
                        editor.putString("userP", phoneNo);
                        // editor.putString("passW", otp);
                        editor.commit();
                        Toast.makeText(LoginScreen.this, "Logging In", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent);


                        FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                        finish();
                        // Update UI
                    } else {
                        // Sign in failed, display a message and update the UI
                        // Toast.makeText(LauncherActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginScreen.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                        // Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(LoginScreen.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                            // The verification code entered was invalid
                        }
                    }
                });
    }


}