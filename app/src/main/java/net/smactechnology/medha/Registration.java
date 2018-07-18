package net.smactechnology.medha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Registration extends AppCompatActivity {
    private static final String TAG = "";
    EditText phoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       // setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){

            actionBar.hide();
        }

        phoneEt = (EditText) findViewById(R.id.phoneET);
        SharedPreferences sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String s=sharedPreferences.getString("phone",null);
        if(s!=null)
        {
            Intent intent=new Intent(Registration.this,UserProfile.class);
            startActivity(intent);
        }
    }
    public void varify(View view) {


        Intent intent = new Intent(Registration.this, VerifyCodeActivity.class);
        intent.putExtra("phone", phoneEt.getText().toString());
        startActivity(intent);





        // startActivity(new Intent(RegisterActivity.this, VerifyCodeActivity.class));
    }
}
