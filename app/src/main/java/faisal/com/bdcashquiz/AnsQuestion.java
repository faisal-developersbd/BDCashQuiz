package faisal.com.bdcashquiz;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AnsQuestion {
    FirebaseFirestore db;
    AnsQuestion()
    {
        db=FirebaseFirestore.getInstance();
    }
    public void userAns(String ans)
    {
        Map<String,String> map=new HashMap<>();
        db.collection(ans).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ans",documentReference.getId());

            }
        });

    }
}
