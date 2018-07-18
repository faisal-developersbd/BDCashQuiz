package net.smactechnology.medha;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AnsQuestion {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    UserInfo info;
    AnsQuestion()
    {
        db=FirebaseFirestore.getInstance();
    }
    public void userAns(String ans)
    {
        mAuth=FirebaseAuth.getInstance();
        info=mAuth.getCurrentUser();
        Map<String,String> map=new HashMap<>();
        db.collection(ans).document(info.getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }
}
