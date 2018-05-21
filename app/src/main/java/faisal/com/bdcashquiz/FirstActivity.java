package faisal.com.bdcashquiz;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import faisal.com.bdcashquiz.model.userManage;

public class FirstActivity extends AppCompatActivity {
private ImageView imgview;
private TextView nameText;
private TextView timeText;
private TextView amountText;
private FirebaseAuth mAuth;
private UserInfo info;
private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        nameText=findViewById(R.id.textView7);
        amountText=findViewById(R.id.amountText);
        timeText=findViewById(R.id.timeText);
        mAuth=FirebaseAuth.getInstance();
        info=mAuth.getCurrentUser();
        sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String url=sharedPreferences.getString("url",null);
        String username=sharedPreferences.getString("name",null);
        nameText.setText(username);
        imgview=findViewById(R.id.imageView);
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
        realTimeUpdate();
    }
    String VideoId="";
    public void realTimeUpdate()
    {

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("liveMonitor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);
               VideoId =""+document.get("video_id");

                String isLive=""+document.get("live");
                if(isLive.equals("true")) {
                    Intent intent = new Intent(FirstActivity.this, MainLiveActivity.class);
                    intent.putExtra("videoId", VideoId);
                    Log.d("videoId", VideoId);
                    startActivity(intent);
                    processLife();
                    finish();
                }
            }
        });
        db.collection("schedule").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);
                timeText.setText(""+document.get("time"));
                amountText.setText("৳ "+document.get("amount"));
            }
        });
    }
     FirebaseFirestore db;
    public void processLife()
    {
         FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userManage manage=documentSnapshot.toObject(userManage.class);
                Log.d("data",manage.toString());
                int life=Integer.parseInt(manage.getTotalLife());
               int gameLife=0;
               if(life>=3) {
                   gameLife = life - 2;
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
         FirebaseFirestore  db=FirebaseFirestore.getInstance();
           db.collection("userManage").document(info.getUid()).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Log.d("datacheck","Update life ");
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
}
