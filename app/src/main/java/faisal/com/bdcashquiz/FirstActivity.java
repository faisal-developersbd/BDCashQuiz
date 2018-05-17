package faisal.com.bdcashquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
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
                }
            }
        });
    }
}
