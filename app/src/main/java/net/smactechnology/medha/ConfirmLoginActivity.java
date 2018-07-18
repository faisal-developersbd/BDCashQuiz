package net.smactechnology.medha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import net.smactechnology.medha.model.userManage;

import java.util.HashMap;

import pub.devrel.easypermissions.EasyPermissions;

public class ConfirmLoginActivity extends AppCompatActivity {
    private TextView uname;
    private TextView emailText;
    private EditText phone;
    private Button confirmBtn;
    private ImageView img;
    private UserInfo info;
    private FirebaseAuth mAuth;
FirebaseFirestore db;
   // private EditText usernameText;


private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);
db=FirebaseFirestore.getInstance();
        new DisableOfflineData().disableOfflineData(db);
        mAuth=FirebaseAuth.getInstance();
        info=mAuth.getCurrentUser();
        //usernameText=findViewById(R.id.username);
       // balanceText=findViewById(R.id.balanceText);
        try {
            getExistence();

        }catch (Exception e)
        {
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
            Glide.with(getBaseContext()).load(photoUrl).centerCrop().override(300,300).error(R.color.red_error).into(img);
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

            phone.setText(""+manage.getPhoneNumber());
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {

                                                  if (phone.getText() != null) {
                                                      String phoneNumber = phone.getText().toString();
                                                      HashMap<String, String> hmap = new HashMap<>();

                                                      hmap.put("name", username);
                                                      hmap.put("phoneNumber", phoneNumber);
                                                      hmap.put("email", email);
                                                      hmap.put("totalLife", manage.getTotalLife());
                                                      hmap.put("gameLife", "0");
                                                      if (manage.getBalance() == null || manage.getBalance().equals(""))
                                                          hmap.put("balance", "0");
                                                      else {
                                                          hmap.put("balance", manage.getBalance());
                                                      }
                                                      SharedPreferences.Editor editor = sharedPreferences.edit();
                                                      editor.putString("phone", phoneNumber);
                                                      editor.commit();
                                                      hmap.put("photoUrl", photoUrl);
                                                      FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                      db.collection("userManage").document(uid).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                          @Override
                                                          public void onSuccess(Void aVoid) {
                                                              Intent intent = new Intent(ConfirmLoginActivity.this, FirstActivity.class);
                                                              startActivity(intent);
                                                              finish();
                                                          }
                                                      });
                                                  } else {
                                                      phone.setError("Please provide your phone number");
                                                  }
                                              }});
        }

    }
    userManage manage;
    public void getExistence() throws Exception
    {
     //   FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                manage=documentSnapshot.toObject(userManage.class);
                if(manage==null)
                {
                    manage=new userManage();
                }
                //Toast.makeText(getBaseContext(),"Data: "+manage.toString(),Toast.LENGTH_LONG).show();
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
                Glide.with(getBaseContext()).load(photoUrl).centerCrop().override(300,300).error(R.color.red_error).into(img);
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
                    if(manage.getPhoneNumber()!=null)
                   phone.setText(""+manage.getPhoneNumber());
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
                            // {
                                if (manage.getBalance() == null || manage.getBalance().equals("")){
                                    hmap.put("balance", "0");
                            }else
                            {
                                hmap.put("balance",manage.getBalance());
                            }

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("phone",phoneNumber);
                            editor.commit();
                            hmap.put("photoUrl",photoUrl);
                           // FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
