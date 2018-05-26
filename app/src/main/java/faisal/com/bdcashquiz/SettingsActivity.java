package faisal.com.bdcashquiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import faisal.com.bdcashquiz.model.userManage;


public class SettingsActivity extends AppCompatActivity {
    String m_Text;
private TextView inputReferralCode;
private TextView referralCode;
private FirebaseAuth mAuth;
private UserInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
mAuth=FirebaseAuth.getInstance();
info=mAuth.getCurrentUser();
      //  AppBarLayout appBarLayout=findViewById(R.id.appbar);
        Toolbar tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings Activity");
        inputReferralCode=findViewById(R.id.inpref);

        inputReferralCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alaertDialog();
            }
        });
    }
    void alaertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Title");
// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getBaseContext());
        View promptsView = li.inflate(R.layout.promts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SettingsActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                m_Text=userInput.getText().toString();
                                checkValidity(m_Text);
                               // Toast.makeText(getBaseContext(),"Input: "+m_Text,Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void checkValidity(String name)
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").whereEqualTo("name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doclist = queryDocumentSnapshots.getDocuments();
                try {
                    DocumentSnapshot document = doclist.get(0);
                    userManage manage = document.toObject(userManage.class);
                    if (!info.getUid().equals(document.getId())) {
                        if (manage != null) {
                            inputReferralCode.setText("" + m_Text);
                            userUpdate(document.getId(), manage);
                            updateLoggedUserDetails();
                        }
                    } else
                        Toast.makeText(getBaseContext(), "You can not reffer yourself!!", Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), "Refferral Code Invalid!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void updateLoggedUserDetails()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userManage manage=documentSnapshot.toObject(userManage.class);
                userUpdate(documentSnapshot.getId(),manage);
            }
        });
    }
    public void userUpdate(final String id, final userManage manage)
    {
        Map<String,String> hmap=new HashMap<>();
        hmap.put("name",manage.getName());
        hmap.put("phoneNumber",manage.getPhoneNumber());
        hmap.put("email",manage.getEmail());
        hmap.put("photoUrl",manage.getPhotoUrl());
        hmap.put("balance",manage.getBalance());
        int totLife=Integer.parseInt(manage.getTotalLife());
        totLife=totLife+1;
        hmap.put("totalLife",""+totLife);
        hmap.put("gameLife",""+manage.getGameLife());
        FirebaseFirestore  db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(id).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("datacheck","Update life from question fragment "+manage.toString());
                Log.d("datacheck","Id "+id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("datacheck","error: "+e);
            }
        });
    }
}
