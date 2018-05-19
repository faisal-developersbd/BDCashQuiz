package faisal.com.bdcashquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FacebookLogin extends AppCompatActivity {
ImageButton fbloginbtn;

private CallbackManager mCallbackMangager;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
//private CallbackManager mCallbackManager;
    String TAG="FacebookLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FacebookSdk.sdkInitialize(getBaseContext());
        setContentView(R.layout.activity_facebook_login);

        mAuth = FirebaseAuth.getInstance();
        preferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);

        final String username=preferences.getString("name",null);
        final String phoneNumber=preferences.getString("phone",null);
        if(username!=null&&phoneNumber!=null)
        {
            Intent intent=new Intent(FacebookLogin.this,FirstActivity.class);
            startActivity(intent);
        }
        mCallbackMangager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.fblogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackMangager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           Log.d("usrname","Name: "+user.getDisplayName()) ;
                           Log.d("usrname","Phone: "+user.getPhoneNumber()) ;
                           Log.d("usrname","Photo Url: "+user.getPhotoUrl()) ;
                           Log.d("usrname","Id: "+user.getUid()) ;

                   SharedPreferences.Editor editor=preferences.edit();
                   editor.putString("name",user.getDisplayName());
                   editor.putString("phone",user.getPhoneNumber());
                   editor.putString("uid",user.getUid());
                   editor.putString("email",user.getEmail());
                            editor.putString("url",""+user.getPhotoUrl());
                   editor.commit();
                    Intent intent=new Intent(FacebookLogin.this,ConfirmLoginActivity.class);
                    startActivity(intent);
                    finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FacebookLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackMangager.onActivityResult(requestCode, resultCode, data);
    }
}
