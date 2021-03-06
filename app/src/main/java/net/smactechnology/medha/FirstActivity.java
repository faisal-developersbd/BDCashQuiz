package net.smactechnology.medha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dd.processbutton.FlatButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import net.smactechnology.medha.model.userManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FirstActivity extends AppCompatActivity {
private ImageView imgview;
private TextView nameText;
private TextView timeText;
private TextView amountText;
private FirebaseAuth mAuth;
private UserInfo info;
private TextView lifeText;
private userManage userData;
private ImageButton imageButton2;

private AdView mAdView;
    private static final int RC_VIDEO_APP_PERM = 124;
    ListenerRegistration userManageRegistration;
    ListenerRegistration scheduleRegistration;
    ListenerRegistration liveMonitorRegistration;
    private SharedPreferences sharedPreferences;
private FlatButton medacrom;
    private TextView balanceText;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseFirestore.getInstance();
        new DisableOfflineData().disableOfflineData(db);
        setContentView(R.layout.activity_first);

        //..............Admob...........

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        balanceText=findViewById(R.id.balanceText);
        nameText=findViewById(R.id.textView7);
        amountText=findViewById(R.id.amountText);
        lifeText=findViewById(R.id.lifeText);
        timeText=findViewById(R.id.timeText);
        medacrom=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        info=mAuth.getCurrentUser();
        sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String url=sharedPreferences.getString("url",null);
        String username=sharedPreferences.getString("name",null);
        nameText.setText(username);
        imgview=findViewById(R.id.imageView);
        imageButton2=findViewById(R.id.imageButton2);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        getLife();
      //  Glide.with(this).load(url).placeholder(R.mipmap.ic_launcher_round).centerCrop().fitCenter().override(50,50).error(R.color.red_error).into(imgview);
        Glide.with(getBaseContext()).load(url).asBitmap().centerCrop().override(100,100).into(new BitmapImageViewTarget(imgview) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgview.setImageDrawable(circularBitmapDrawable);
            }
        });

        medacrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        realTimeUpdate();
        requestPermissions();
    }
    String VideoId="";
    double balance;

    @Override
    protected void onDestroy() {
        datachAllListener();
        Log.d("distroyCall","Datach All");
        super.onDestroy();
    }

    public void datachAllListener()
{

    liveMonitorRegistration.remove();

    scheduleRegistration.remove();

}
private String sessionId="";
    private String tokenId="";
    private String apiKey="";
    public void realTimeUpdate()
    {

       // FirebaseFirestore db=FirebaseFirestore.getInstance();
     Query query=   db.collection("liveMonitor");
     liveMonitorRegistration=query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);

                String isLive=""+document.get("live");
                if(isLive.equals("true")) {
                    sessionId =""+document.get("sessionId");
                    tokenId=""+document.get("tokenId");
                    apiKey=""+document.get("apiKey");
                    Log.d("checkKey",""+apiKey);
                    Log.d("checkKey",""+tokenId);
                    Log.d("checkKey",sessionId);
                    SharedPreferences preferences=getSharedPreferences("myData",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("sessionId",sessionId);
                    editor.putString("tokenId",tokenId);
                    editor.putString("apiKey",apiKey);
                    editor.commit();
                    String watch=""+document.get("watch");
                    Log.d("watch",watch);
                    if(!watch.equals("false"))
                    processLife();
                    else
                    {
                        Toast.makeText(getBaseContext(),getString(R.string.message1),Toast.LENGTH_LONG).show();
                        watchGame();

                    }
                    datachAllListener();



                }
            }
        });
      Query query1=  db.collection("schedule");
      scheduleRegistration=query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);
                timeText.setText(""+document.get("time"));
                amountText.setText("৳ "+document.get("amount"));
            }
        });

     db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               userData=documentSnapshot.toObject(userManage.class);
                balance=Double.parseDouble(""+documentSnapshot.get("balance"));

                balanceText.setText("৳ "+translateNumber(""+balance));
            }
        });
    }
    public String translateNumber(String number)
    {
     String output=number.replaceAll("0","০").replaceAll("1","১").replaceAll("2","২").replaceAll("3","৩").replaceAll("4","৪").replaceAll("5","৫").replaceAll("6","৬").replaceAll("7","৭").replaceAll("8","৮").replaceAll("9","৯");
    return output;
    }
     FirebaseFirestore db;
    int maxLife;
    public void getLife()
    {
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userManage manage=documentSnapshot.toObject(userManage.class);
                String lifet=manage.getTotalLife();
                String output=lifet.replaceAll("0","০").replaceAll("1","১").replaceAll("2","২").replaceAll("3","৩").replaceAll("4","৪").replaceAll("5","৫").replaceAll("6","৬").replaceAll("7","৭").replaceAll("8","৮").replaceAll("9","৯");
               lifeText.setText(""+output);
            }
        });
    }

    public void watchGame()
    {
        // FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("schedule").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot documentSnapshot=documents.get(0);
                maxLife=Integer.parseInt(""+documentSnapshot.get("max_life"));
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userManage manage=documentSnapshot.toObject(userManage.class);
                        Log.d("data",manage.toString());
                        int life=Integer.parseInt(manage.getTotalLife());


                        Map<String,String> hmap=new HashMap<>();

                        hmap.put("name",info.getDisplayName());
                        hmap.put("phoneNumber",sharedPreferences.getString("phone",null));
                        hmap.put("email",info.getEmail());
                        hmap.put("photoUrl",""+info.getPhotoUrl());

                        hmap.put("totalLife",""+life);
                        hmap.put("gameLife",""+0);
                        hmap.put("balance",manage.getBalance());
                        FirebaseFirestore  db=FirebaseFirestore.getInstance();
                        db.collection("userManage").document(info.getUid()).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("datacheck","Update life ");
                                Intent intent = new Intent(FirstActivity.this, MainLiveActivity.class);

//                                intent.putExtra("sessionId", "ab");
//                                intent.putExtra("tokenId","cd");
//                                intent.putExtra("apiKey","ef");
                                Log.d("videoId", VideoId);

                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("datacheck","error: "+e);
                            }
                        });
                    }
                });
            }
        });


    }
    public void processLife()
    {
        // FirebaseFirestore db=FirebaseFirestore.getInstance();
         db.collection("schedule").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 List<DocumentSnapshot> documents=queryDocumentSnapshots.getDocuments();
                 DocumentSnapshot documentSnapshot=documents.get(0);
                 maxLife=Integer.parseInt(""+documentSnapshot.get("max_life"));
                 FirebaseFirestore db=FirebaseFirestore.getInstance();
                 db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                         userManage manage=documentSnapshot.toObject(userManage.class);
                         Log.d("data",manage.toString());
                         int life=Integer.parseInt(manage.getTotalLife());
                         int gameLife=0;
                         if(life>=maxLife) {
                             gameLife = maxLife;
                             life=life-gameLife;
                         }
                         else gameLife=life;
                         Map<String,String> hmap=new HashMap<>();

                         hmap.put("name",info.getDisplayName());
                         hmap.put("phoneNumber",sharedPreferences.getString("phone",null));
                         hmap.put("email",info.getEmail());
                         hmap.put("photoUrl",""+info.getPhotoUrl());

                         hmap.put("totalLife",""+life);
                         hmap.put("gameLife",""+gameLife);
                         hmap.put("balance",manage.getBalance());
                         FirebaseFirestore  db=FirebaseFirestore.getInstance();
                         db.collection("userManage").document(info.getUid()).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Log.d("datacheck","Update life ");
                                 Intent intent = new Intent(FirstActivity.this, MainLiveActivity.class);
                                 intent.putExtra("videoId", VideoId);
                                 Log.d("videoId", VideoId);

                                 startActivity(intent);
                                 finish();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Log.d("datacheck","error: "+e);
                             }
                         });
                     }
                 });
             }
         });


    }

    public void nextActivity(View view) {
        Intent intent=new Intent(FirstActivity.this,new ReferralCodeActivity().getClass());
        intent.putExtra("code",info.getDisplayName());
        startActivity(intent);
       // finish();
    }

    public void onBackPressed() {
        datachAllListener();
        finish();
        //super.onBackPressed();
    }

    public void goSettings(View view) {
        Gson gson=new Gson();
        if(userData!=null)
        {
            String data=gson.toJson(userData);
            Intent intent=new Intent(FirstActivity.this,new SettingsActivity().getClass());
            intent.putExtra("data",data);
            startActivity(intent);
        }

    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { android.Manifest.permission.INTERNET, android.Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout



        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic for livestreaming", RC_VIDEO_APP_PERM, perms);
        }
    }
}
