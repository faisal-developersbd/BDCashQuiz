package faisal.com.bdcashquiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import faisal.com.bdcashquiz.model.Questions;

public class MainActivity extends AppCompatActivity {
FirebaseFirestore db;
private RadioButton op1;
private RadioButton op2;
private RadioButton op3;
private RadioButton op4;
private TextView titleText;
private RadioGroup radioGroup;
private String ans;
private Button nextBtn;
private Button submitBtn;
private Questions questions;
private String userAns;
ArrayList<Questions> questionsList;
FirebaseAuth mAuth;
UserInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseFirestore.getInstance();
        questionsList=new ArrayList<>();
        initAll();
        getQuestion();
      //  user=mAuth.getCurrentUser();
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
    String VideoId="";

    public void initAll()
    {

        op1=findViewById(R.id.option1);
        op2=findViewById(R.id.option2);
        op3=findViewById(R.id.option3);
        op4=findViewById(R.id.option4);
        submitBtn=findViewById(R.id.btnsubmit);
        radioGroup=findViewById(R.id.questionRadioGroup);
        nextBtn=findViewById(R.id.btnNext);
        titleText=findViewById(R.id.quesTitle);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                if(i>=size)
                {
                    Toast.makeText(getBaseContext(),"Game Over",Toast.LENGTH_LONG).show();
                }
                else {

                                    displayQuestion(i);
                                    i++;
                                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(i>=size)
//                {
//                    Toast.makeText(getBaseContext(),"Game Over",Toast.LENGTH_LONG).show();
//                }
                 if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getBaseContext(),"Select Option",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d("testFire","Id: "+radioGroup.getCheckedRadioButtonId());
                    int id=radioGroup.getCheckedRadioButtonId();
                    if(ans.equals("a"))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            op1.setBackgroundColor(getResources().getColor(R.color.colorAccent,getTheme()));
                            if(op1.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="a";
                        }
                        else {
                            op1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            if(op1.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="a";
                        }
                    }
                    else if(ans.equals("b"))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            op2.setBackgroundColor(getResources().getColor(R.color.colorAccent,getTheme()));
                            if(op2.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="b";
                        }
                        else {
                            op2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            if(op2.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="b";
                        }

                    }
                    else if(ans.equals("c"))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            op3.setBackgroundColor(getResources().getColor(R.color.colorAccent,getTheme()));
                            if(op3.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="c";
                        }
                        else {
                            op3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            if(op3.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="c";
                        }


                    }
                    else if(ans.equals("d"))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            op4.setBackgroundColor(getResources().getColor(R.color.colorAccent,getTheme()));
                            if(op4.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="d";
                        }
                        else {
                            op4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            if(op4.getId()==id)
                            {
                                Toast.makeText(getBaseContext(),"Ans is correct",Toast.LENGTH_LONG).show();
                                Map<String, String> map = new HashMap<>();
                                map.put("user", "faisal.hossa.pk@gmail.com");
                                map.put("userAns", userAns);
                                insertResponse(questions.getQ(), map);
                            }
                            else Toast.makeText(getBaseContext(),"Ans is wrong",Toast.LENGTH_LONG).show();
                            userAns="d";
                        }

                    }
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    //displayQuestion(i);
                }
            }
        });
}

boolean responseFlag;
public void insertResponse(String table, Map<String,String> hitMap)
{
    db.collection(table).add(hitMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            responseFlag=true;
        }
    });
}
    int i=0;
    int size;
    public void displayQuestion(int position)
    {
        int id=radioGroup.getCheckedRadioButtonId();
        if(position!=0) {
            RadioButton tempRadio = findViewById(id);
           // try {
               // tempRadio.setChecked(false);
            setContentView(R.layout.activity_main);
            initAll();
//op1=findViewById(R.id.option1);
//            }catch (Exception e)
//            {
//                Log.d("testFire","Position: "+position);
//            }
        }
        Questions questions=questionsList.get(position);
        this.questions=questions;
        titleText.setText(questions.getQ());
        op1.setText(questions.getO1());
        op2.setText(questions.getO2());
        op3.setText(questions.getO3());
        op4.setText(questions.getO4());
        ans=questions.getAns();


       // i++;
    }
    public void getQuestion()
    {

        db=FirebaseFirestore.getInstance();
        db.collection("questions")

                .whereEqualTo("title", "Random Question")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("testFire", "Task Success. Size:  "+task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               Questions questions=document.toObject(Questions.class);
                               questions.setId(document.getId());
                               questionsList.add(questions);


                            }
                            size=questionsList.size();
                            displayQuestion(0);
                        } else {
                            Log.d("testFire", "Error getting documents: ", task.getException());
                        }
                    }
                });



    }
}
