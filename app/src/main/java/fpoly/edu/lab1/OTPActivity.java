package fpoly.edu.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPActivity extends AppCompatActivity {

    private static final String TAG = OTPActivity.class.getName();
    private EditText edt_otp;
    private Button btn_login;
    private String mPhoneNumber;
    private String mVerification;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        mAuth = FirebaseAuth.getInstance();
        edt_otp = findViewById(R.id.edt_otp);
        btn_login = findViewById(R.id.btn_login_phone);
        getDataIntent();
        setTitleToolbar();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOTP = edt_otp.getText().toString().trim();
                onClickOTP(strOTP);
            }
        });
    }

    private void onClickOTP(String strOTP){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, strOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("phone_number");
        mVerification = getIntent().getStringExtra("verification_id");
    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("OTP code");
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e(TAG, "signInWithPhoneAuthCredential:success");
                    FirebaseUser user = task.getResult().getUser();
                    gotoMainActivity(user.getPhoneNumber());
                } else {
                    Log.w(TAG, "signInWithPhoneAuthCredential:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(OTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void gotoMainActivity(String phoneNumber) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}