package faisal.com.bdcashquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ConfirmLoginActivity extends AppCompatActivity {
    private TextView uname;
    private TextView emailText;
    private EditText phone;
    private Button confirmBtn;
    private ImageView img;

private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);
        sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
        final String username=sharedPreferences.getString("name",null);
        final String phoneNumber=sharedPreferences.getString("phone",null);
        final String uid=sharedPreferences.getString("uid",null);
        final String photoUrl=sharedPreferences.getString("url",null);
        final String email=sharedPreferences.getString("email",null);
       // final String
      //  final String =sharedPreferences.getString("email",null);
        Log.d("usrname","Photo Url "+photoUrl);
        img=findViewById(R.id.propic);
        Glide.with(this).load(photoUrl).centerCrop().override(300,300).error(R.color.red_error).into(img);
//        Glide.with(getBaseContext()).load(photoUrl).asBitmap().centerCrop().override(150,150).into(new BitmapImageViewTarget(img) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                img.setImageDrawable(circularBitmapDrawable);
//            }
//        });
        confirmBtn=findViewById(R.id.btnConfirm);
        uname=findViewById(R.id.nameText);
        phone=findViewById(R.id.phoneNumber);
        emailText=findViewById(R.id.emailText);

        emailText.setText(""+email);
        uname.setText(""+username);
        if(phoneNumber!=null)
        phone.setText(""+phoneNumber);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText()!=null) {
                  String  phoneNumber=phone.getText().toString();
                    HashMap<String,String> hmap=new HashMap<>();

                    hmap.put("name",username);
                    hmap.put("phoneNumber",phoneNumber);
                    hmap.put("email",email);
                    hmap.put("totalLife","1");
                    hmap.put("gameLife","0");

                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putString("phone",phoneNumber);
                   editor.commit();
                    hmap.put("photoUrl",photoUrl);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("userManage").document(uid).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent=new Intent(ConfirmLoginActivity.this,FirstActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else
                {
                    phone.setError("Please provide your phone number");
                }
            }
        });


    }
}
