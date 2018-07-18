package net.smactechnology.medha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
       // populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                  //  attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //0
                // attemptLogin();
//                login("faisal.hossain.pk@gmail.comn","123456");
                Toast.makeText(getBaseContext(), "Email: "+mEmailView.getText().toString()+" "+"Pass: "+mPasswordView.getText().toString(), Toast.LENGTH_SHORT).show();
               //login(mEmailView.getText().toString(),mPasswordView.getText().toString());
                Intent intent=new Intent(LoginActivity.this,FirstActivity.class);
               // intent.putExtra("userMail",user.getEmail());
                startActivity(intent);
             //   Intent intent=new Intent(LoginActivity.this,MainLiveActivity.class);
               // intent.putExtra("userMail",user.getEmail());
              //  startActivity(intent);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();

    }
    private void login(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("testFire", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity mainActivity=new MainActivity();
                            mainActivity.setUser(mAuth.getCurrentUser());
                            Intent intent=new Intent(LoginActivity.this,MainLiveActivity.class);
                            intent.putExtra("userMail",user.getEmail());
                            startActivity(intent);
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("testFire", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                         //   updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
