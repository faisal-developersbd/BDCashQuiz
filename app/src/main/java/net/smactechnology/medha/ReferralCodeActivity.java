package net.smactechnology.medha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReferralCodeActivity extends AppCompatActivity {
private TextView referrelCode;
FirebaseAuth mAuth;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_code);
      //  db=FirebaseFirestore.getInstance();
      //  mAuth=FirebaseAuth.getInstance();
      //UserInfo info= mAuth.getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        referrelCode=findViewById(R.id.codeText);
        String codet=""+getIntent().getStringExtra("code");
        referrelCode.setText(""+codet);
//        ActionBar ab = getActionBar();
//       // ab.setHomeButtonEnabled(true);
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setTitle(R.string.extra_life);
//
//        ab.show();
    }

    public void sharecontent(View view) {
        String code=referrelCode.getText().toString();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody=getString(R.string.share_message1)+" "+code+" "+getString(R.string.share_message2)+" "+"' https://play.google.com/store/apps/details?id=faisal.com.bdcashquiz";
        String shareSub=getString(R.string.app_name);

        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        intent.putExtra(Intent.EXTRA_TEXT,sharebody);
        startActivity(Intent.createChooser(intent,"Share using"));
    }
}
