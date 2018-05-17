package faisal.com.bdcashquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyCodeActivity extends AppCompatActivity {

    EditText codeValue1, codeValue2, codeValue3, codeValue4, codeValue5, codeValue6;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;

    String phoneNumber;
    TextView phoneTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){

            actionBar.hide();
        }

        fbAuth = FirebaseAuth.getInstance();


        codeValue1 = findViewById(R.id.code_value_1);
        codeValue2 = findViewById(R.id.code_value_2);
        codeValue3 = findViewById(R.id.code_value_3);
        codeValue4 = findViewById(R.id.code_value_4);
        codeValue5 = findViewById(R.id.code_value_5);
        codeValue6 = findViewById(R.id.code_value_6);
        phoneTV = findViewById(R.id.phoneTV);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phone");
        phoneNumber = "+880" + phoneNumber;
        phoneTV.setText(phoneNumber);




        FirebaseApp.initializeApp(this);
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    public void continueMain(View view) {

        verifyCode();
    }

    public void resendMeCode(View view) {

        resendCode();
    }





    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Toast.makeText(VerifyCodeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded

                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;


                    }
                };
    }

    public void verifyCode() {

        String code1 = codeValue1.getText().toString();
        String code2 = codeValue2.getText().toString();
        String code3 = codeValue3.getText().toString();
        String code4 = codeValue4.getText().toString();
        String code5 = codeValue5.getText().toString();
        String code6 = codeValue6.getText().toString();

        String code = code1 + code2 + code3 + code4 + code5 + code6;

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(VerifyCodeActivity.this, "Successfully verified code", Toast.LENGTH_SHORT).show();
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            db.collection("userManage");
                            Map<String,String> map=new HashMap<>();
                            map.put("user","");
                            db.collection("userManage").document(phoneNumber).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    SharedPreferences sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("phoneNo",phoneNumber);
                                    editor.commit();
                                    Intent intent=new Intent(VerifyCodeActivity.this,UserProfile.class);
                                    startActivity(intent);
                                }
                            });

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Toast.makeText(VerifyCodeActivity.this, "Invalid code.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void resendCode() {



        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);

        //Toast.makeText(this, "Successfully resend the code !!!", Toast.LENGTH_SHORT).show();
    }
}
