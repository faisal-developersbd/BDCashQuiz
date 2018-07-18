package net.smactechnology.medha;

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

import com.dd.processbutton.iml.SubmitProcessButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import net.smactechnology.medha.model.Questions;
import net.smactechnology.medha.model.userManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FramgentQuestion extends Fragment {
    ViewGroup view;
    private RadioButton op1;
    private RadioButton op2;
    private RadioButton op3;
    private RadioButton op4;
    private String ansa,ansb,ansc;
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
    Query querya1,querya2,querya3,querya4,querya5,querya6,querya7,querya8,querya9,querya10,querya11,querya12;
    Query queryb1,queryb2,queryb3,queryb4,queryb5,queryb6,queryb7,queryb8,queryb9,queryb10,queryb11,queryb12;
    Query queryc1,queryc2,queryc3,queryc4,queryc5,queryc6,queryc7,queryc8,queryc9,queryc10,queryc11,queryc12;
    Query queryd1,queryd2,queryd3,queryd4,queryd5,queryd6,queryd7,queryd8,queryd9,queryd10,queryd11,queryd12;

    ListenerRegistration listenera1;
    ListenerRegistration listenerb1;
    ListenerRegistration listenerc1;
    ListenerRegistration listenerd1;

    ListenerRegistration listenera2;
    ListenerRegistration listenerb2;
    ListenerRegistration listenerc2;
    ListenerRegistration listenerd2;

    ListenerRegistration listenera3;
    ListenerRegistration listenerb3;
    ListenerRegistration listenerc3;
    ListenerRegistration listenerd3;

    ListenerRegistration listenera4;
    ListenerRegistration listenerb4;
    ListenerRegistration listenerc4;
    ListenerRegistration listenerd4;

    ListenerRegistration listenera5;
    ListenerRegistration listenerb5;
    ListenerRegistration listenerc5;
    ListenerRegistration listenerd5;

    ListenerRegistration listenera6;
    ListenerRegistration listenerb6;
    ListenerRegistration listenerc6;
    ListenerRegistration listenerd6;

    ListenerRegistration listenera7;
    ListenerRegistration listenerb7;
    ListenerRegistration listenerc7;
    ListenerRegistration listenerd7;

    ListenerRegistration listenera8;
    ListenerRegistration listenerb8;
    ListenerRegistration listenerc8;
    ListenerRegistration listenerd8;

    ListenerRegistration listenera9;
    ListenerRegistration listenerb9;
    ListenerRegistration listenerc9;
    ListenerRegistration listenerd9;

    ListenerRegistration listenera10;
    ListenerRegistration listenerb10;
    ListenerRegistration listenerc10;
    ListenerRegistration listenerd10;

    ListenerRegistration listenera11;
    ListenerRegistration listenerb11;
    ListenerRegistration listenerc11;
    ListenerRegistration listenerd11;

    ListenerRegistration listenera12;
    ListenerRegistration listenerb12;
    ListenerRegistration listenerc12;
    ListenerRegistration listenerd12;
    public void set1Active()
    {
        listenera1=querya1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la1=t.getDocuments();
                try {
                    if(la1==null)
                        optn1.setText(""+questions.getO1());
                    else {
                        optn1.setText("" + questions.getO1() + " (" + la1.size() + ")");
                        ansa="" + questions.getO1() + " (" + la1.size() + ")";
                    }
                }catch (Exception xe)
                {

                }

            }


        });
        listenerb1=queryb1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la1=t.getDocuments();

                try {
                    if(la1==null)
                        optn2.setText(""+questions.getO2());
                    else {
                        optn2.setText("" + questions.getO2() + " (" + la1.size() + ")");
                       ansb= "" + questions.getO2() + " (" + la1.size() + ")";
                    }
                }catch (Exception xe)
                {

                }
            }


        });
        listenerc1=queryc1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la1=t.getDocuments();
                try {
                    if(la1==null)
                        optn3.setText(""+questions.getO3());
                    else {
                        optn3.setText("" + questions.getO3() + " (" + la1.size() + ")");
                        ansc="" + questions.getO3() + " (" + la1.size() + ")";
                    }
                }catch (Exception xe)
                {

                }
            }


        });
        listenerd1=queryd1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la1=t.getDocuments();
                optn1.setText(""+questions.getO1()+" ("+la1.size()+")");


            }


        });
    }
    public void set1Deactvity()
    {
        listenera1.remove();
        listenerb1.remove();
        listenerc1.remove();
        listenerd1.remove();

    }
    public void set2Active()
    {
        listenera2=querya2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la2=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la2.size() + ")");
                    ansa="" + questions.getO1() + " (" + la2.size() + ")";
                }catch (Exception ex)
                {

                }
            }


        });
        listenerb2=queryb2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la2=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la2.size() + ")");
                    ansb="" + questions.getO2() + " (" + la2.size() + ")";
                }catch (Exception ex)
                {

                }
            }


        });
        listenerc2=queryc2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la2=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la2.size() + ")");
                    ansc="" + questions.getO3() + " (" + la2.size() + ")";
                }catch (Exception ex)
                {

                }
            }


        });
        listenerd2=queryd2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la2=t.getDocuments();
            }



        });
    }
    public void set2Deactvity()
    {
        listenera2.remove();
        listenerb2.remove();
        listenerc2.remove();
        listenerd2.remove();

    }
    public void set3Active()
    {
        listenera3=querya3.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la3=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la3.size() + ")");
                    ansa="" + questions.getO1() + " (" + la3.size() + ")";
                }catch (Exception ex)
                {

                }
            }



        });
        listenerb3=queryb3.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la3=t.getDocuments();
                optn2.setText("" + questions.getO2() + " (" + la3.size() + ")");
                ansc="" + questions.getO3() + " (" + la3.size() + ")";
            }
        });
        listenerc3=queryc3.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la3=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la3.size() + ")");
                    ansc="" + questions.getO3() + " (" + la3.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd3=queryd3.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la3=t.getDocuments();
                try {

                }catch (Exception ex)
                {

                }
            }
        });
    }
    public void set3Deactvity()
    {
        listenera3.remove();
        listenerb3.remove();
        listenerc3.remove();
        listenerd3.remove();

    }
    public void set4Active()
    {
        listenera4=querya4.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la4=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la4.size() + ")");
                    ansa="" + questions.getO1() + " (" + la4.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb4=queryb4.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la4=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la4.size() + ")");
                    ansb="" + questions.getO2() + " (" + la4.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc4=queryc4.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la4=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la4.size() + ")");
                    ansc="" + questions.getO3() + " (" + la4.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd4=queryd4.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la4=t.getDocuments();
            }
        });
    }
    public void set4Deactvity()
    {
        listenera4.remove();
        listenerb4.remove();
        listenerc4.remove();
        listenerd4.remove();

    }
    public void set5Active()
    {
        listenera5=querya5.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la5=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la5.size() + ")");
                    ansa="" + questions.getO1() + " (" + la5.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb5=queryb5.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la5=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la5.size() + ")");
                    ansb="" + questions.getO2() + " (" + la5.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc5=queryc5.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la5=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la5.size() + ")");
                    ansc="" + questions.getO3() + " (" + la5.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd5=queryd5.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la5=t.getDocuments();
            }
        });
    }
    public void set5Deactvity()
    {
        listenera5.remove();
        listenerb5.remove();
        listenerc5.remove();
        listenerd5.remove();

    }
    public void set6Active()
    {
        listenera6=querya6.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la6=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la6.size() + ")");
                    ansa="" + questions.getO1() + " (" + la6.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb6=queryb6.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la6=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la6.size() + ")");
                    ansb="" + questions.getO2() + " (" + la6.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc6=queryc6.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la6=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la6.size() + ")");
                    ansc="" + questions.getO3() + " (" + la6.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd6=queryd6.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la6=t.getDocuments();

            }
        });
    }
    public void set6Deactvity()
    {
        listenera6.remove();
        listenerb6.remove();
        listenerc6.remove();
        listenerd6.remove();

    }
    public void set7Active()
    {
        listenera7=querya7.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la7=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la7.size() + ")");
                    ansa="" + questions.getO1() + " (" + la7.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb7=queryb7.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la7=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la7.size() + ")");
                    ansb="" + questions.getO2() + " (" + la7.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc7=queryc7.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la7=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la7.size() + ")");
                    ansc="" + questions.getO3() + " (" + la7.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd7=queryd7.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la7=t.getDocuments();

            }
        });

    }
    public void set7Deactvity()
    {
        listenera7.remove();
        listenerb7.remove();
        listenerc7.remove();
        listenerd7.remove();

    }
    public void set8Active()
    {
        listenera8=querya8.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la8=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la8.size() + ")");
                    ansa="" + questions.getO1() + " (" + la8.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb8=queryb8.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la8=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la8.size() + ")");
                    ansb="" + questions.getO2() + " (" + la8.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc8=queryc8.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la8=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la8.size() + ")");
                    ansc="" + questions.getO3() + " (" + la8.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd8=queryd8.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la8=t.getDocuments();
            }
        });
    }
    public void set8Deactvity()
    {
        listenera8.remove();
        listenerb8.remove();
        listenerc8.remove();
        listenerd8.remove();

    }
    public void set9Active()
    {
        listenera9=querya9.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la9=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la9.size() + ")");
                    ansa="" + questions.getO1() + " (" + la9.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb9=queryb9.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la9=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la9.size() + ")");
                    ansb="" + questions.getO2() + " (" + la9.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc9=queryc9.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la9=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la9.size() + ")");
                    ansc="" + questions.getO3() + " (" + la9.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd9=queryd9.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la9=t.getDocuments();
            }
        });
    }
    public void set9Deactvity()
    {
        listenera9.remove();
        listenerb9.remove();
        listenerc9.remove();
        listenerd9.remove();

    }
    public void set10Active()
    {
        listenera10=querya10.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la10=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la10.size() + ")");
                    ansa="" + questions.getO1() + " (" + la10.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb10=queryb10.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la10=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la10.size() + ")");
                    ansb="" + questions.getO2() + " (" + la10.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc10=queryc10.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la10=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la10.size() + ")");
                    ansc="" + questions.getO3() + " (" + la10.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd10=queryd10.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la10=t.getDocuments();
            }
        });
    }
    public void set12Active()
    {
        listenera12=querya12.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la12=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la12.size() + ")");
                    ansa="" + questions.getO1() + " (" + la12.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb12=queryb12.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la12=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la12.size() + ")");
                    ansb="" + questions.getO2() + " (" + la12.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc12=queryc12.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la12=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la12.size() + ")");
                    ansc="" + questions.getO3() + " (" + la12.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd12=queryd12.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la11=t.getDocuments();
            }
        });
    }
    public void set11Active()
    {
        listenera11=querya11.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la11=t.getDocuments();
                try {
                    optn1.setText("" + questions.getO1() + " (" + la11.size() + ")");
                    ansa="" + questions.getO1() + " (" + la11.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerb11=queryb11.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la11=t.getDocuments();
                try {
                    optn2.setText("" + questions.getO2() + " (" + la11.size() + ")");
                    ansb="" + questions.getO2() + " (" + la11.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerc11=queryc11.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la11=t.getDocuments();
                try {
                    optn3.setText("" + questions.getO3() + " (" + la11.size() + ")");
                    ansc="" + questions.getO3() + " (" + la11.size() + ")";
                }catch (Exception ex)
                {

                }
            }
        });
        listenerd11=queryd11.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot t, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List la11=t.getDocuments();
            }
        });
    }
    public void set11Deactvity()
    {
        listenera11.remove();
        listenerb11.remove();
        listenerc11.remove();
        listenerd11.remove();

    }
    public void set12Deactvity()
    {
        listenera12.remove();
        listenerb12.remove();
        listenerc12.remove();
        listenerd12.remove();

    }
    public void set10Deactvity()
    {
        listenera10.remove();
        listenerb10.remove();
        listenerc10.remove();
        listenerd10.remove();

    }
    FirebaseFirestore db;
    public void initQueries()
    {
         db=FirebaseFirestore.getInstance();
        querya1=db.collection("a1");
        querya2=db.collection("a2");
        querya3=db.collection("a3");
        querya4=db.collection("a4");
        querya5=db.collection("a5");
        querya6=db.collection("a6");
        querya7=db.collection("a7");
        querya8=db.collection("a8");
        querya9=db.collection("a9");
        querya10=db.collection("a10");
        querya11=db.collection("a11");
        querya12=db.collection("a12");

        queryb1=db.collection("b1");
        queryb2=db.collection("b2");
        queryb3=db.collection("b3");
        queryb4=db.collection("b4");
        queryb5=db.collection("b5");
        queryb6=db.collection("b6");
        queryb7=db.collection("b7");
        queryb8=db.collection("b8");
        queryb9=db.collection("b9");
        queryb10=db.collection("b10");
        queryb11=db.collection("b11");
        queryb12=db.collection("b12");

        queryc1=db.collection("c1");
        queryc2=db.collection("c2");
        queryc3=db.collection("c3");
        queryc4=db.collection("c4");
        queryc5=db.collection("c5");
        queryc6=db.collection("c6");
        queryc7=db.collection("c7");
        queryc8=db.collection("c8");
        queryc9=db.collection("c9");
        queryc10=db.collection("c10");
        queryc11=db.collection("c11");
        queryc12=db.collection("c12");

        queryd1=db.collection("null1");
        queryd2=db.collection("null2");
        queryd3=db.collection("null3");
        queryd4=db.collection("null4");
        queryd5=db.collection("null5");
        queryd6=db.collection("null6");
        queryd7=db.collection("null7");
        queryd8=db.collection("null8");
        queryd9=db.collection("null9");
        queryd10=db.collection("null10");
        queryd11=db.collection("null11");
        queryd12=db.collection("null12");

    }
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

                ansSubmit();
                if(lifeCount>0)
                    ansQuestion.userAns(userAns+questions.getNo());
            }
            if(l/1000==5)
            {

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
            liveAttend();

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
                usAns="null";
            else if(btnClicked.equals("a"))
                usAns="a";
            else if(btnClicked.equals("b"))
                usAns="b";
            else if(btnClicked.equals("c"))
                usAns="c";
            userAns=usAns;
           // else userAns="d";

            AnsTimer timer=new AnsTimer(5000,1000);
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
        through(Integer.parseInt(questions.getNo()));

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
    public void through(int track)
    {
        initQueries();
        if(track==1)
        {

            set1Active();

        }
        else  if(track==2)
        {
            set2Active();
        }
        else if(track==3)
        {
            set3Active();
        }
        else if(track==4)
        {
            set4Active();
        }
        else if(track==5)
        {
            set5Active();
        }
        else if(track==6)
        {
            set6Active();
        }
        else if(track==7)
        {
            set7Active();
        }
        else if(track==8)
        {
            set8Active();
        }
        else if(track==9)
        {
            set9Active();
        }
        else if(track==10)
        {
            set10Active();
        }
        else if(track==11)
        {
            set11Active();
        }
        else if(track==12)
        {
            set12Active();
        }
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
public void withdraw(int track)
{
    if(track==1)
    {
        set1Deactvity();
    }
    else if(track==2)
    {
        set2Deactvity();
    }
    else if(track==3)
    {
        set3Deactvity();
    }
    else if(track==4)
    {
        set4Deactvity();
    }
    else if(track==5)
    {
        set5Deactvity();
    }
    else if(track==6)
    {
        set6Deactvity();
    }
    else if(track==7)
    {
        set7Deactvity();
    }
    else if(track==7)
    {
        set7Deactvity();
    }
    else if(track==8)
    {
        set8Deactvity();
    }
    else if(track==9)
    {
        set9Deactvity();
    }
    else if(track==10)
    {
        set10Deactvity();
    }
    else if(track==11)
    {
        set11Deactvity();
    }
    else if(track==12)
    {
        set12Deactvity();
    }
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
        try {
            GradientDrawable gradientDrawable = optn1.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn1.setProgressDrawable(gradientDrawable);
            GradientDrawable gradientDrawable2 = optn2.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn2.setProgressDrawable(gradientDrawable2);
            GradientDrawable gradientDrawable3 = optn3.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn3.setProgressDrawable(gradientDrawable3);
            optn1.setProgress(40);
            optn2.setProgress(70);
            optn3.setProgress(80);
        }catch(Exception e)
        {

        }
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
               // Toast.makeText(getActivity().getBaseContext(),"Life Count: "+lifeCount,Toast.LENGTH_LONG).show();
                optn1.setClickable(false);
                optn2.setClickable(false);
                optn3.setClickable(false);
}
}
int livea,liveb,livec,no,total;
    int parsea,parseb,parsec;
public void liveAttend()
{
//    FirebaseFirestore db=FirebaseFirestore.getInstance();
//    db.collection("liveAttend").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//        @Override
//        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//           List<DocumentSnapshot> documentSnapshot=queryDocumentSnapshots.getDocuments();
//           for(DocumentSnapshot document:documentSnapshot)
//           {
//               if(document.getId().equals("ansa"))
//               {
//                    livea=Integer.parseInt(""+document.get("number"));
//               }
//               else if(document.getId().equals("ansb"))
//               {
//                   liveb=Integer.parseInt(""+document.get("number"));
//               }
//               else if(document.getId().equals("ansc"))
//               {
//                   livec=Integer.parseInt(""+document.get("number"));
//               }
//               else if(document.getId().equals("no"))
//               {
//                   no=Integer.parseInt(""+document.get("number"));
//               }
//           }
            ansUpdate(ansa,ansb,ansc);
//           total=livea+liveb+livec+no;
//           parsea=(livea*100)/total;
//           parseb=(liveb*100)/total;
//           parsec=(livec*100)/total;
  //      }
   // });
}

public void updateNumber()
{
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    db.collection("a"+questions.getNo());
}
    public void ansUpdate(String ansa,String ansb,String ansc)
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
try {
    timverview.setText(getString(R.string.message1));
}catch (Exception e)
{


}
           // Toast.makeText(getActivity().getBaseContext(),"You are eliminated!",Toast.LENGTH_LONG).show();
        }
        if(ans.equals("b")) {
            GradientDrawable gradientDrawable = optn2.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));

            optn2.setProgress(100);
           // optn2.setText(questions.getO2()+" ("+liveb+")");

            GradientDrawable gradientDrawable2=optn1.getProgressDrawable();
            gradientDrawable2.setColor(getResources().getColor(R.color.errorProgress));
            optn1.setProgressDrawable(gradientDrawable2);

       optn1.setText(ansa);
            optn3.setProgressDrawable(gradientDrawable2);
            optn3.setProgress(1);
       optn3.setText(ansc);
        }
        else if(ans.equals("a"))
        {
            GradientDrawable gradientDrawable = optn1.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
            optn1.setProgressDrawable(gradientDrawable);
            optn1.setProgress(100);
         //   optn1.setText(questions.getO1()+" ("+livea+")");
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn2.setProgressDrawable(gradientDrawable);
            optn2.setProgress(0);
            optn2.setText(ansb);
            optn3.setProgressDrawable(gradientDrawable);
            optn3.setProgress(0);
            optn3.setText(ansc);

        }
        else if(ans.equals("c"))
        {
            GradientDrawable gradientDrawable = optn3.getProgressDrawable();
            gradientDrawable.setColor(getResources().getColor(R.color.successProgress));
            optn3.setProgressDrawable(gradientDrawable);
            optn3.setProgress(100);
        //    optn3.setText(questions.getO3()+" ("+livec+")");
            gradientDrawable.setColor(getResources().getColor(R.color.errorProgress));
            optn2.setProgressDrawable(gradientDrawable);
            optn2.setProgress(0);
            optn2.setText(ansb);
        //    optn2.setText(questions.getO2()+" ("+liveb+")");
            optn1.setProgressDrawable(gradientDrawable);
            optn1.setProgress(0);
            optn1.setText(ansa);
        //    optn1.setText(questions.getO1()+" ("+livea+")");
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