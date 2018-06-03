package faisal.com.bdcashquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import faisal.com.bdcashquiz.model.userManage;


public class SettingsActivity extends AppCompatActivity {
    String m_Text;
private TextView inputReferralCode,refmsg,txtPhone,txtEmail;
private TextView referralCode;
private FirebaseAuth mAuth;
private UserInfo info;
private userManage userData;
private TextView userName;
private ImageView userPic,editPImg,editEImg;
private Button logOut,accountBT;
public String refCk="";
private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Gson gson=new Gson();

        userData=gson.fromJson(getIntent().getStringExtra("data"),userManage.class);
        userName=findViewById(R.id.txtName);
        userPic=findViewById(R.id.userPic);
        refmsg=findViewById(R.id.reftext);
        txtPhone=findViewById(R.id.txtPhone);
        txtEmail=findViewById(R.id.txtEmail);
        editPImg=findViewById(R.id.editPImg);
        editEImg=findViewById(R.id.editEImg);
        setUpAuth();

        logOut=findViewById(R.id.button5);
        accountBT=findViewById(R.id.accountBT);




mAuth=FirebaseAuth.getInstance();
info=mAuth.getCurrentUser();









      //  AppBarLayout appBarLayout=findViewById(R.id.appbar);
        Toolbar tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings Activity");
        inputReferralCode=findViewById(R.id.inpref);


        //Profile info.....................

        Glide.with(getBaseContext()).load(userData.getPhotoUrl()).override(150,150).fitCenter().into(userPic);
        userName.setText(userData.getName());
        txtEmail.setText(userData.getEmail());
        txtPhone.setText(userData.getPhoneNumber());


         final String ref_code=userData.getReferral_code();
         final String userName=userData.getName();
       // Log.d("ref_code",ref_code);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(mAuth.getUid()).collection("referral").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                try {


                    List<DocumentSnapshot> doclist = queryDocumentSnapshots.getDocuments();
                    DocumentSnapshot document = doclist.get(0);
                    Log.d("docTest", "Document: " + document.get("code"));

                    refCk=document.get("code").toString();
                    if(refCk!=""){
                        refmsg.setText("রেফারেল কোড ব্যবহার করা হয়েছে");
                        inputReferralCode.setText("কোড : "+refCk);
                        inputReferralCode.setEnabled(false);

                    }

                }catch (Exception e){

                    refCk="";


                }

            }

        });





        /*if(refCk!=""){

        refmsg.setText("রেফারেল কোড ব্যবহার করা হয়েছে");
        inputReferralCode.setText("কোড : "+refCk);

        }*/





        inputReferralCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ref_code==null){
                alaertDialog();}

            }
        });

        editPImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alaertDialog2("phoneNumber");

            }
        });

        editEImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alaertDialog3("email");
            }
        });


        accountBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogAccount();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
       FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }

    public void setUpAuth(){
mAuthStateListener=new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user=mAuth.getCurrentUser();

        if (user != null) {
            // User is signed in
            Log.d("Auth", "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            Log.d("Auth","onAuthStateChanged:signed_out");
            startActivity(new Intent(SettingsActivity.this, FacebookLogin.class));
        }



    }
};

    }






















    private void alertdialogAccount() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Title");

        LayoutInflater li = LayoutInflater.from(getBaseContext());
        View promptsView = li.inflate(R.layout.promts2, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SettingsActivity.this);


        alertDialogBuilder.setView(promptsView);



         final EditText accountName=promptsView.findViewById(R.id.acountName);
        final Spinner accountType=promptsView.findViewById(R.id.acountType);
        final EditText accountNumber=promptsView.findViewById(R.id.acountNumber);


        // ..................................Get Acount Information.....................
        FirebaseFirestore db2=FirebaseFirestore.getInstance();


        db2.collection("userManage").document(mAuth.getUid()).collection("account").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                try {


                    List<DocumentSnapshot> doclist = queryDocumentSnapshots.getDocuments();
                    DocumentSnapshot document = doclist.get(0);
                    Log.d("docTest", "Document: " + document.get("Type"));

                    // refCk=document.get("code").toString();

                    accountName.setText(document.get("Name").toString());
                     accountType.setSelection(((ArrayAdapter<String>)accountType.getAdapter()).getPosition(document.get("Type").toString()));
                    accountNumber.setText(document.get("Number").toString());
                                        /*    if(refCk!=""){
                                                refmsg.setText("রেফারেল কোড ব্যবহার করা হয়েছে");
                                                inputReferralCode.setText("কোড : "+refCk);
                                                inputReferralCode.setEnabled(false);

                                            }*/

                }catch (Exception e){

                    Toast.makeText(SettingsActivity.this,"No Data Found Add Account Information",Toast.LENGTH_SHORT).show();



                }

            }

        });




        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text

                              //  m_Text=userInput.getText().toString();












                                //.....................Save Account Information .....................
                               if(accountType.getSelectedItem().toString().equals("CHOOSE ONE")){
                                   Toast.makeText(SettingsActivity.this,"Please Choose Acount Type",Toast.LENGTH_SHORT).show();
                               }
                               else if(accountNumber.getText().toString().equals("")){
                                    Toast.makeText(SettingsActivity.this,"Please Give Account Number",Toast.LENGTH_SHORT).show();
                                }

                               else {
                                   FirebaseFirestore db = FirebaseFirestore.getInstance();
                                   Map<String, String> hmap = new HashMap<>();
                                   hmap.put("Name", accountName.getText().toString());
                                   hmap.put("Type", accountType.getSelectedItem().toString());
                                   hmap.put("Number", accountNumber.getText().toString());
                                   db.collection("userManage").document(mAuth.getUid()).collection("account").document("account_info").set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {

                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           Toast.makeText(SettingsActivity.this, "account Add", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                               }
                                //editProfile(m_Text);

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





    private void alaertDialog2( final String phone) {


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
        alertDialogBuilder.setTitle("");
        final TextView txtview=promptsView.findViewById(R.id.textView1);
        txtview.setText("মোবাইল নাম্বার পরিবর্তনঃ");



        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        userInput.setText(txtPhone.getText().toString());
        userInput.selectAll();

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text

                                m_Text=userInput.getText().toString();

                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                db.collection("userManage").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        userManage manage=documentSnapshot.toObject(userManage.class);
                                        userUpdatePhone(documentSnapshot.getId(),manage,m_Text);
                                        txtPhone.setText(m_Text);
                                        Toast.makeText(SettingsActivity.this,"মোবাইল নাম্বার পরিবর্তন সফল হয়েছে",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //editProfile(m_Text);

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




    private void alaertDialog3( final String Email) {


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
        alertDialogBuilder.setTitle("");
        final TextView txtview=promptsView.findViewById(R.id.textView1);
        txtview.setText("ইমেইল পরিবর্তনঃ ");



        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        userInput.setText(txtEmail.getText().toString());
        userInput.selectAll();

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text

                                m_Text=userInput.getText().toString();

                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                db.collection("userManage").document(mAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        userManage manage=documentSnapshot.toObject(userManage.class);
                                        userUpdateEmail(documentSnapshot.getId(),manage,m_Text);
                                        txtEmail.setText(m_Text);
                                        Toast.makeText(SettingsActivity.this,"ইমেইল পরিবর্তন সফল হয়েছে",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //editProfile(m_Text);

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

    private void userUpdateEmail(final String id, final userManage manage, String newEmail) {

        {
            Map<String,String> hmap=new HashMap<>();
            hmap.put("name",manage.getName());
            hmap.put("phoneNumber",manage.getPhoneNumber());
            hmap.put("email",newEmail);
            hmap.put("photoUrl",manage.getPhotoUrl());
            hmap.put("balance",manage.getBalance());

            hmap.put("totalLife",""+manage.getTotalLife());
            hmap.put("gameLife",""+manage.getGameLife());
            /* hmap.put("referral_code",m_Text);*/
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

    private void userUpdatePhone(final String id, final userManage manage, String newPhone) {
        {
            Map<String,String> hmap=new HashMap<>();
            hmap.put("name",manage.getName());
            hmap.put("phoneNumber",newPhone);
            hmap.put("email",manage.getEmail());
            hmap.put("photoUrl",manage.getPhotoUrl());
            hmap.put("balance",manage.getBalance());

            hmap.put("totalLife",""+manage.getTotalLife());
            hmap.put("gameLife",""+manage.getGameLife());
            /* hmap.put("referral_code",m_Text);*/
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

 /*   private void editProfile(final String userName, String field, String value) {
        Map<String,String> hmap=new HashMap<>();


        hmap.put(field,value);
        FirebaseFirestore  db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(userName).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // Log.d("datacheck","Update life from question fragment "+manage.toString());
                Log.d("datacheck","Id "+userName);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("datacheck","error: "+e);
            }
        });

    }*/

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
                               Toast.makeText(getBaseContext(),"Input: "+m_Text,Toast.LENGTH_LONG).show();
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
                            inputReferralCode.setEnabled(false);
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
        Map<String,String> hmap=new HashMap<>();
        hmap.put("code",m_Text);
        db.collection("userManage").document(mAuth.getUid()).collection("referral").document("referral_code").set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SettingsActivity.this,"ok referaal",Toast.LENGTH_SHORT).show();
            }
        });

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
       /* hmap.put("referral_code",m_Text);*/
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
