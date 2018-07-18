package net.smactechnology.medha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserProfile extends AppCompatActivity {
SharedPreferences sharedPreferences;
    String username,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
         sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
         phoneNo=sharedPreferences.getString("phoneNo",null);
        username=  sharedPreferences.getString("username",null);
       if(username!=null)
       {
           Intent intent=new Intent(UserProfile.this,FirstActivity.class);
           startActivity(intent);
       }
      //  getUserName();
    }
    public void getUserName()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("userManage").
                whereEqualTo("phoneNo",phoneNo)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot doc=  documentSnapshots.get(0);
                String user=doc.getString("userName");
                if(!user.equals("")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", user);
                    Intent intent = new Intent(UserProfile.this, FirstActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void next(View view) {
        final EditText editText=findViewById(R.id.editText);
                       SharedPreferences sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("userName",editText.getText().toString());
        editor.commit();
                Intent intent=new Intent(UserProfile.this,FirstActivity.class);
                startActivity(intent);

//
//        Map<String,String> map=new HashMap<>();
//        map.put("user",editText.getText().toString());
//        FirebaseFirestore db=FirebaseFirestore.getInstance();
        //db.collection("userManage").document(phoneNo).set(map);
//        db.collection("userManage").document(phoneNo).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                SharedPreferences sharedPreferences=getSharedPreferences("credentials", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.putString("userName",editText.getText().toString());
//                Intent intent=new Intent(UserProfile.this,UserProfile.class);
//                startActivity(intent);
//            }
//        });
    }
}
