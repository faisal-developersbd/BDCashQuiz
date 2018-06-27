package faisal.com.bdcashquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WinnerActivity extends AppCompatActivity {
private GridView gridView;
private List<gridItem> itemList;
private GridAdapter adapter;
private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

try {
    Toolbar tb = findViewById(R.id.toolbar);
    setSupportActionBar(tb);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle("ABOUT");
}catch(Exception e)
{

}

        gridView=findViewById(R.id.winnerGrid);
        itemList=new ArrayList<>();
        adapter=new GridAdapter(getBaseContext(),itemList);
        gridView.setAdapter(adapter);


        db=FirebaseFirestore.getInstance();
        new DisableOfflineData().disableOfflineData(db);

        addWinnerList();
    }
public void addWinnerList()
{
    db.collection("winner").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
           List<DocumentSnapshot> documentList=queryDocumentSnapshots.getDocuments();
           for(DocumentSnapshot doc:documentList)
           {
               String photoUrl=""+doc.get("photoUrl");
               String name=""+doc.get("name");
               String prize=""+doc.get("prize");

               gridItem gi=new gridItem();
               gi.setPhotUrl(photoUrl);
               gi.setNameText(name);
               gi.setBalanceText(prize);

               itemList.add(gi);
           }
           adapter.notifyDataSetChanged();
        }
    });
}
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent=new Intent(WinnerActivity.this,FirstActivity.class);
        startActivity(intent);
        finish();

    }
}
