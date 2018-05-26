package faisal.com.bdcashquiz;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import faisal.com.bdcashquiz.model.Questions;
import faisal.com.bdcashquiz.model.userManage;

public class FramgentQuestion extends Fragment {
    ViewGroup view;
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
    private TextView timverview;
    private LinearLayout linearLayout;
    private AnsQuestion ansQuestion;
    private SubmitProcessButton optn1;
    private SubmitProcessButton optn2;
    private SubmitProcessButton optn3;
        Context context;
    private String btnClicked=null;
    //private boolean isClikable;
    private int lifeCount=0;
    UserInfo info;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    public class AnsTimer extends CountDownTimer{

        public AnsTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            if(l/1000==1) {
                setAllError();
                liveAttend();
                ansSubmit();
            }
        }
public void setLifeCount(int life)
{
    lifeCount=life;
}
        @Override
        public void onFinish() {

            optn1.setClickable(false);
            optn2.setClickable(false);
            optn3.setClickable(false);

            ansUpdate();
        }
    }
    public class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
           // isClikable=true;
        }

        @Override
        public void onTick(long l) {
timverview.setText(""+(l/1000));
        }
        boolean visible;
        @Override
        public void onFinish() {
           // view.setVisibility(View.GONE);
           // Toast.makeText(context,"Timer finished",Toast.LENGTH_LONG).show();
            userAns="";
            String usAns="";
            if(btnClicked==null)
                usAns="d";
            else if(btnClicked.equals("a"))
                usAns="a";
            else if(btnClicked.equals("b"))
                usAns="b";
            else if(btnClicked.equals("c"))
                usAns="c";
            userAns=usAns;
           // else userAns="d";
            ansQuestion.userAns(usAns);
            AnsTimer timer=new AnsTimer(13000,1000);
            timverview.setBackground(getResources().getDrawable(R.drawable.btnshape));
            if(lifeCount==0)
                timverview.setText(getString(R.string.message1));
            timverview.setText(R.string.time_up);
            timverview.setTextColor(getResources().getColor(R.color.red_error));
            timverview.setPadding(30,30,30,30);

            visible=!visible;
            TransitionSet set = new TransitionSet()
                    .addTransition(new Scale(0.7f))
                    .addTransition(new Fade())
                    .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                            new FastOutLinearInInterpolator());


            TransitionManager.beginDelayedTransition(view);
            timverview.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
           // isClikable=false;
            timer.start();

           // ansSubmit();
            //linearLayout.setVisibility(View.GONE);
//            YouTubePlayerView playerView=find
//            ViewGroup.LayoutParams params=
//            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
//            params.width=ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }
    public void ansSubmit()
    {
        linearLayout.setVisibility(View.GONE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= (ViewGroup) inflater.inflate(R.layout.fragment_question,container,false);
        context=getActivity().getBaseContext();
        initAll();
        return view;
    }
    public void displayCorrectAns(String ans)
    {
        if(ans.equals("a"))
        {

                op1.setBackgroundColor(getResources().getColor(R.color.colorAccent));


        }
        else if(ans.equals("b"))
        {

                op2.setBackgroundColor(getResources().getColor(R.color.colorAccent));


        }
        else if(ans.equals("c"))
        {


                op3.setBackgroundColor(getResources().getColor(R.color.colorAccent));




        }
        else if(ans.equals("d"))
        {


                op4.setBackgroundColor(getResources().getColor(R.color.colorAccent));



        }
    }
    public void updateGameLife(final int lifeCount)
    {

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userManage manage=documentSnapshot.toObject(userManage.class);

                Map<String,String> hmap=new HashMap<>();
                hmap.put("name",manage.getName());
                hmap.put("phoneNumber",manage.getPhoneNumber());
                hmap.put("email",info.getEmail());
                hmap.put("photoUrl",manage.getPhotoUrl());
                hmap.put("balance",manage.getBalance());

                hmap.put("totalLife",""+manage.getTotalLife());
                hmap.put("gameLife",""+lifeCount);
                FirebaseFirestore  db=FirebaseFirestore.getInstance();
                db.collection("userManage").document(info.getUid()).set(hmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("datacheck","Update life from question fragment");
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
    public void initAll()
    {
        //linearLayout=view.findViewsWithText(R.id.linearFragment);

        ansQuestion=new AnsQuestion();
        timverview=view.findViewById(R.id.timer);

//        op1=view.findViewById(R.id.option1);
//        op2=view.findViewById(R.id.option2);
//        op3=view.findViewById(R.id.option3);
//        op4=view.findViewById(R.id.option4);
        submitBtn=view.findViewById(R.id.btnsubmit);
      //  radioGroup=view.findViewById(R.id.questionRadioGroup);
        nextBtn=view.findViewById(R.id.btnNext);
        titleText=view.findViewById(R.id.quesTitle);
        linearLayout=view.findViewById(R.id.linearFragment);
       // if(lifeCount>0){
        optn1=view.findViewById(R.id.btnOption1);
        optn2=view.findViewById(R.id.btnOption2);
        optn3=view.findViewById(R.id.btnOption3);

       // }

        mAuth=FirebaseAuth.getInstance();
        sharedPreferences=getActivity().getBaseContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        info=mAuth.getCurrentUser();
        if(questions!=null) {
            optn1.setText(questions.getO1());
            optn2.setText(questions.getO2());
            optn3.setText(questions.getO3());
            ans=questions.getAns();
            titleText.setText(questions.getQ());
            new MyTimer(10000,1000).start();
        }
//        optn1.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//        optn2.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//        optn3.setBackgroundColor(getResources().getColor(R.color.colorDefault));

        optn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(isClikable==true) {
                    btnClicked = "a";

                    GradientDrawable gradientDrawable = optn1.getProgressDrawable();
                    gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
                    optn1.setProgressDrawable(gradientDrawable);
                    optn1.setProgress(100);
                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
                    optn2.setProgressDrawable(gradientDrawable);
                    optn2.setProgress(0);
                    optn2.setText(questions.getO2());
                    optn3.setProgressDrawable(gradientDrawable);
                    optn3.setProgress(0);
                    optn3.setText(questions.getO3());
               // }
              //  optn2.se

            }
        });
        optn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(isClikable==true) {
                    btnClicked = "b";
//                optn1.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//                optn2.setBackgroundColor(getResources().getColor(R.color.blue_pressed));
//                optn3.setBackgroundColor(getResources().getColor(R.color.colorDefault));
                    GradientDrawable gradientDrawable = optn2.getProgressDrawable();
                    gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
                    optn2.setProgressDrawable(gradientDrawable);
                    optn2.setProgress(100);
                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
                    optn1.setProgressDrawable(gradientDrawable);
                    optn1.setProgress(0);
                    optn1.setText(questions.getO1());
                    optn3.setProgressDrawable(gradientDrawable);
                    optn3.setProgress(0);
                    optn3.setText(questions.getO3());
              //  }
            }
        });
        optn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if(isClikable==true) {
                    btnClicked = "c";
//                optn1.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//                optn2.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//                optn3.setBackgroundColor(getResources().getColor(R.color.blue_pressed));
                    GradientDrawable gradientDrawable = optn3.getProgressDrawable();
                    gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
                    optn3.setProgressDrawable(gradientDrawable);
                    optn3.setProgress(100);
                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
                    optn1.setProgressDrawable(gradientDrawable);
                    optn1.setProgress(0);
                    optn1.setText(questions.getO1());
                    optn2.setProgressDrawable(gradientDrawable);
                    optn2.setProgress(0);
                    optn2.setText(questions.getO2());
              //  }
            }
        });
        disableClickonCondition();

    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions,int lifeCount) {
        this.questions = questions;
        this.lifeCount=lifeCount;
//Toast.makeText(context,questions.toString(),Toast.LENGTH_LONG).show();
    }
    public void setAllError()
    {
                            GradientDrawable gradientDrawable=optn1.getProgressDrawable();
                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
                    optn1.setProgressDrawable(gradientDrawable);
        GradientDrawable gradientDrawable2=optn2.getProgressDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
        optn2.setProgressDrawable(gradientDrawable2);
        GradientDrawable gradientDrawable3=optn3.getProgressDrawable();
        gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
        optn3.setProgressDrawable(gradientDrawable3);
        optn1.setProgress(40);
        optn2.setProgress(70);
        optn3.setProgress(80);

    }
    public void disableClickonCondition()
    {
//        FirebaseFirestore db=FirebaseFirestore.getInstance();
//        db.collection("userManage").document(info.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                lifeCount=Integer.parseInt(""+documentSnapshot.get("gameLife"));
//            }
//        });
      //  Toast.makeText(getActivity().getBaseContext(),"Life Count Outside condition: "+lifeCount,Toast.LENGTH_LONG).show();
        if(lifeCount<=0)
            {
                Toast.makeText(getActivity().getBaseContext(),"Life Count: "+lifeCount,Toast.LENGTH_LONG).show();
                optn1.setClickable(false);
                optn2.setClickable(false);
                optn3.setClickable(false);
}
}
int livea,liveb,livec,no,total;
    int parsea,parseb,parsec;
public void liveAttend()
{
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    db.collection("liveAttend").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
           List<DocumentSnapshot> documentSnapshot=queryDocumentSnapshots.getDocuments();
           for(DocumentSnapshot document:documentSnapshot)
           {
               if(document.getId().equals("ansa"))
               {
                    livea=Integer.parseInt(""+document.get("number"));
               }
               else if(document.getId().equals("ansb"))
               {
                   liveb=Integer.parseInt(""+document.get("number"));
               }
               else if(document.getId().equals("ansc"))
               {
                   livec=Integer.parseInt(""+document.get("number"));
               }
               else if(document.getId().equals("no"))
               {
                   no=Integer.parseInt(""+document.get("number"));
               }
           }
           total=livea+liveb+livec+no;
           parsea=(livea*100)/total;
           parseb=(liveb*100)/total;
           parsec=(livec*100)/total;
        }
    });
}
    public void ansUpdate()
    {

//        optn1.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//        optn2.setBackgroundColor(getResources().getColor(R.color.colorDefault));
//        optn3.setBackgroundColor(getResources().getColor(R.color.colorDefault));
       // isClikable=false;
        linearLayout.setVisibility(View.VISIBLE);
       FirebaseFirestore db=FirebaseFirestore.getInstance();
      // if(ans.equals("a")) {
            // optn1.setBackgroundColor(getResources().getColor(R.color.blue_pressed));
//            GradientDrawable gradientDrawable=optn1.getProgressDrawable();
//            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
//        GradientDrawable gradientDrawable=optn1.getProgressDrawable();
//        gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
        if(!userAns.equals(ans))
        {
            lifeCount--;
            updateGameLife(lifeCount);

        }
        if(lifeCount<=0)
        {

            timverview.setText(getString(R.string.message1));

           // Toast.makeText(getActivity().getBaseContext(),"You are eliminated!",Toast.LENGTH_LONG).show();
        }
        if(ans.equals("b")) {
            GradientDrawable gradientDrawable = optn2.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));

            optn2.setProgress(100);
            optn2.setText(questions.getO2()+" ("+liveb+")");

            GradientDrawable gradientDrawable2=optn1.getProgressDrawable();
            gradientDrawable2.setColor(getResources().getColor(R.color.errorProgress));
            optn1.setProgressDrawable(gradientDrawable2);
            optn1.setProgress(0);
            optn1.setText(questions.getO1()+" ("+livea+")");
            optn3.setProgressDrawable(gradientDrawable2);
            optn3.setProgress(0);
            optn3.setText(questions.getO3()+" ("+livec+")");
        }
        else if(ans.equals("a"))
        {
            GradientDrawable gradientDrawable = optn1.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
            optn1.setProgressDrawable(gradientDrawable);
            optn1.setProgress(100);
            optn1.setText(questions.getO1()+" ("+livea+")");
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn2.setProgressDrawable(gradientDrawable);
            optn2.setProgress(0);
            optn2.setText(questions.getO2()+" ("+liveb+")");
            optn3.setProgressDrawable(gradientDrawable);
            optn3.setProgress(0);
            optn3.setText(questions.getO3()+" ("+livec+")");

        }
        else if(ans.equals("c"))
        {
            GradientDrawable gradientDrawable = optn3.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
            optn3.setProgressDrawable(gradientDrawable);
            optn3.setProgress(100);
            optn3.setText(questions.getO3()+" ("+livec+")");
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn2.setProgressDrawable(gradientDrawable);
            optn2.setProgress(0);
            optn2.setText(questions.getO2()+" ("+liveb+")");
            optn1.setProgressDrawable(gradientDrawable);
            optn1.setProgress(0);
            optn1.setText(questions.getO1()+" ("+livea+")");
        }
        Log.d("checkData","a = "+parsea);
        Log.d("checkData","b = "+parseb);
        Log.d("checkData","c = "+parsec);
//        optn1.setProgress(50);
//        optn2.setProgress(30);
//        optn3.setProgress(80);
           // optn1.setProgressDrawable(gradientDrawable);
            // optn1.setProgress(40);
      //  }

//
//        else if(ans.equals("b"))
//        {
//            GradientDrawable gradientDrawable=optn2.getProgressDrawable();
//            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
//            optn2.setProgressDrawable(gradientDrawable);
//           // optn2.setProgress(20);
//        }
//        else  if(ans.equals("c"))
//        {
//            GradientDrawable gradientDrawable=optn3.getProgressDrawable();
//            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
//            optn3.setProgressDrawable(gradientDrawable);
//           // optn3.setProgress(60);
//        }
      //  Toast.makeText(getActivity().getBaseContext(),"ANS: "+ans,Toast.LENGTH_LONG).show();
        db.collection("a").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int sizea=queryDocumentSnapshots.size();



//                else
//                {
//                    GradientDrawable gradientDrawable=optn1.getProgressDrawable();
//                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
//                    optn1.setProgressDrawable(gradientDrawable);
//                    optn1.setProgress(40);
//                }
            }
        });

        db.collection("b").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


//                else {
//                    GradientDrawable gradientDrawable=optn2.getProgressDrawable();
//                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
//                    optn2.setProgressDrawable(gradientDrawable);
//                    optn2.setProgress(20);
//                }
            }
        });

        db.collection("c").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


//                else {
//                    GradientDrawable gradientDrawable=optn3.getProgressDrawable();
//                    gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
//                    optn3.setProgressDrawable(gradientDrawable);
//                    optn3.setProgress(60);
//                }
            }
        });
    }
}