package faisal.com.bdcashquiz;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import faisal.com.bdcashquiz.model.Questions;
import faisal.com.bdcashquiz.model.userManage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainLiveActivity extends AppCompatActivity implements Session.SessionListener,PublisherKit.PublisherListener {
private LinearLayout linearLayout;
private WebView webview;
private android.app.FragmentManager manager;
private VideoView videoView;
private FloatingActionButton life1;
private FloatingActionButton life2;
private TextView watchTextView;
ListenerRegistration userManageRegistration;
ListenerRegistration liveMonitorRegistration;
ListenerRegistration liveUserRegistration;
ListenerRegistration aRegistration;
ListenerRegistration bRegistration;
ListenerRegistration cRegistration;
ListenerRegistration dRegistration;
private boolean messagedisplay;
    FloatingActionButton life3;
    FirebaseAuth mAuth;
    UserInfo info;
    FirebaseFirestore db;
   // private YouTubePlayerView mYouTubePlayerView;
private ProgressDialog mDialog;
    //public static final String APP_KEY="AIzaSyBgbP5yQ9KdRnl8GM9fqWxl-EQSNWkAFDg";
    public static  String VIDEL_ID="";
    private AppBarLayout appBarLayout;

    //TokBox........

    private  static  String API_KEY="46125722";
    private static String SESSION_ID="1_MX40NjEyNTcyMn5-MTUyNzMyNzcyNjk5OX5PNjdCUXhVVEw1MTZINmN0eWNzbWQxbW5-fg";
    private static String TOKEN="T1==cGFydG5lcl9pZD00NjEyNTcyMiZzaWc9Yjk4M2Q1ZmZlMDg0N2RhODQzZjUxZjBiYzcyMDhkMWE0NTBiMzYzYjpzZXNzaW9uX2lkPTFfTVg0ME5qRXlOVGN5TW41LU1UVXlOek15TnpjeU5qazVPWDVQTmpkQ1VYaFZWRXcxTVRaSU5tTjBlV056YldReGJXNS1mZyZjcmVhdGVfdGltZT0xNTMwMDEwMTA3Jm5vbmNlPTAuMDg4NTAzMDg3OTQwMTEyMyZyb2xlPXN1YnNjcmliZXImZXhwaXJlX3RpbWU9MTUzMDYxNDkwNyZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==" ;
    private static String LOG_TAG=MainActivity.class.getSimpleName();
    private static final int PC_SETTINGS=123;
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;


    private Session mSession;
    private FrameLayout pub_container,sub_container;

    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private ListenerRegistration liveAttendRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_live);
        db=FirebaseFirestore.getInstance();
        disableOfflineData();
      //  VIDEL_ID=getIntent().getStringExtra("videoId");
      //  Log.d("videoId","In Main: "+VIDEL_ID);

        //TopBoX Live................
        requestPermissions();

        mPublisherViewContainer = findViewById(R.id.publisher_container);
        mSubscriberViewContainer = findViewById(R.id.subscriber_container);


         mAuth=FirebaseAuth.getInstance();
         info=mAuth.getCurrentUser();
         life1=findViewById(R.id.life1);
         life2=findViewById(R.id.life2);
         life3=findViewById(R.id.life3);

        realTimeMonitorUpdate();
        liveAttendUser();
        //webview use to call own site
//        webview =(WebView)findViewById(R.id.webview);
//
//        webview.setWebViewClient(new WebViewClient());
//        webview.setWebChromeClient(new WebChromeClient());
//        webview .getSettings().setJavaScriptEnabled(true);
//        webview .getSettings().setDomStorageEnabled(true);
//        webview.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
       // webview.loadUrl("https://www.bukwild.com/");
        //videoView=findViewById(R.id.videoView);
     // AppCompatActivity activity=  new AppCompatActivity();
      //  linearLayout=findViewById(R.id.youtubeContainer);
        manager=getFragmentManager();
       // loadQuestionFragment();
        framgentQuestion=new FramgentQuestion();
       // manager.beginTransaction().replace(R.id.displayQuestionLayout,framgentQuestion).commit();
        liveUpdate();
        attendUser();
      //  mYouTubePlayerView=(YouTubePlayerView) findViewById(R.id.youTubePlayerView);
      //  mYouTubePlayerView.initialize(APP_KEY,this);
        Drawable drawable = AppCompatResources.getDrawable(getBaseContext(),R.drawable.ic_chatbackground);
        AppBarLayout layout=findViewById(R.id.chatbarlayout);
       // layout.setBackground(drawable);
        appBarLayout=findViewById(R.id.appBarLayout);
        appBarLayout.setBackground(drawable);
        appBarLayout.setVisibility(View.GONE);
watchTextView=findViewById(R.id.watchText);
      //  manager.beginTransaction().remove(framgentQuestion).commit();
       // playVideo();
    }
    public  void disableOfflineData() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        try {
            db.setFirestoreSettings(settings);
        } catch (Exception e)
        {

        }
    }

    @Override
    public void onBackPressed() {
        datachAllListener();
        finish();
        //super.onBackPressed();
    }

    public void datachAllListener()
    {
       userManageRegistration.remove();
       liveMonitorRegistration.remove();
       liveAttendRegistration.remove();
    }
    private boolean isGameStart=true;
    int gamelife;
public void userManageStatus()
{
  //  FirebaseFirestore db=FirebaseFirestore.getInstance();
   Query query= db.collection("userManage").whereEqualTo("photoUrl",""+info.getPhotoUrl());
  userManageRegistration=query.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
          List<DocumentChange> documentChange=  queryDocumentSnapshots.getDocumentChanges();
          DocumentChange documentChange1=documentChange.get(0);
            userManage manage=documentChange1.getDocument().toObject(userManage.class);
             gamelife=Integer.parseInt(manage.getGameLife());
            switch (documentChange1.getType())
          {
              case ADDED:
                  Log.d("datacheck","Data added"+manage.toString());
             // if(gamelife!=0)
                  displayLife(gamelife);
              break;
              case MODIFIED:
                  Log.d("datacheck","Data added"+manage.toString());
                  displayLife(Integer.parseInt(manage.getGameLife()));
                  break;
              default:break;
          }
//            List<DocumentSnapshot> documentSnapshotList=queryDocumentSnapshots.getDocuments();
//            DocumentSnapshot documentSnapshot=documentSnapshotList.get(0);
//            Log.d("datacheck",documentSnapshot.getData().toString());
        }
    });

//    db.collection("userMange").document(info.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//        @Override
//        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//         //   switch (documentSnapshot.)
//            //userManage manage=documentSnapshot.toObject(userManage.class);
//            Log.d("datacheck",""+documentSnapshot.getData());
//            if(e!=null)
//                Log.d("datacheck","Error: "+e);
//            //switch (documentSnapshot.ty)
//           // Toast.makeText(getBaseContext(),"Data: "+documentSnapshot.toString(),Toast.LENGTH_LONG).show();
////           if(isGameStart==true) {
////
//
//
//
//        }
//    });
}
View view;
public void displayLife(int life)
{
//view=

                   switch (life){
                       case 0:
                           life1.setVisibility(View.GONE);
                           life2.setVisibility(View.GONE);
                           life3.setVisibility(View.GONE);
                         //  Snackbar.make(,"Sorry You Are No More!!",Snackbar.LENGTH_LONG).setAction(null,null);
                           if(messagedisplay==true) {
                               Toast.makeText(getBaseContext(), getString(R.string.message1), Toast.LENGTH_LONG).show();
                           }
                           break;
                       case 1:
                           life1.setVisibility(View.VISIBLE);
                           life2.setVisibility(View.GONE);
                           life3.setVisibility(View.GONE);
                           if(messagedisplay==true) {
                               Toast.makeText(getBaseContext(), getString(R.string.message2), Toast.LENGTH_LONG).show();
                           }
                           break;
                       case 2:
                           life1.setVisibility(View.VISIBLE);
                           life2.setVisibility(View.VISIBLE);
                           life3.setVisibility(View.GONE);
                           if(messagedisplay==true) {
                               Toast.makeText(getBaseContext(), getString(R.string.message3), Toast.LENGTH_LONG).show();
                           }
                           break;
                       case 3:
                           life1.setVisibility(View.VISIBLE);
                           life2.setVisibility(View.VISIBLE);
                           life3.setVisibility(View.VISIBLE);

                           break;
                           default:break;
                   }

}
    public void realTimeMonitorUpdate()
    {
        userManageStatus();
        //Toast.makeText(getBaseContext(),"User Id: "+info.getUid(),Toast.LENGTH_LONG).show();
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
       Query query= db.collection("liveMonitor");
       liveMonitorRegistration=query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);


                String isLive=""+document.get("live");

                if(isLive.equals("false")) {
                    Intent intent = new Intent(MainLiveActivity.this, WinnerActivity.class);


                   onDisconnected(mSession);
                   datachAllListener();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

  //  public void update
    public void playVideo()
    {
        String videoUrl="http://techslides.com/demos/sample-videos/small.webm";
        mDialog=new ProgressDialog(MainLiveActivity.this);
        mDialog.setMessage("please wait");
        mDialog.setCanceledOnTouchOutside(false);

        mDialog.show();
        try{
            Uri uri=Uri.parse(videoUrl);
            videoView.setVideoURI(uri);


        }catch (Exception e)
        {

        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mDialog.dismiss();
                videoView.start();
                mediaPlayer.setLooping(true);
            }
        });
    }
    FramgentQuestion framgentQuestion;

    public void loadQuestionFragment(Questions questions)
    {
        framgentQuestion=new FramgentQuestion();
        framgentQuestion.setQuestions(questions,gamelife);
        try {
            manager.beginTransaction().replace(R.id.subscriber_container, framgentQuestion).commit();
        }catch(Exception e)
        {

        }

    }
    public void attendUser()

    {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        UserInfo info=mAuth.getCurrentUser();
        HashMap<String,String> hashMap=new HashMap<>();
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("liveuser").document(info.getUid()).set(hashMap);
    }
    ViewGroup.LayoutParams params;
    public void liveUpdate()
    {
       // manager.beginTransaction().remove(framgentQuestion).commit();
     //   if(mYouTubePlayerView==null)
     //   mYouTubePlayerView=findViewById(R.id.youTubePlayerView);
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
    //  params = mYouTubePlayerView.getLayoutParams();
        final CollectionReference docRef = db.collection("livequestion");
        manager.beginTransaction().remove(framgentQuestion).commit();
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                List<DocumentChange> chang=queryDocumentSnapshots.getDocumentChanges();
                for(DocumentChange change:chang)
                {
                    switch (change.getType())
                    {
                        case ADDED:
                         //   params.width=ViewGroup.LayoutParams.WRAP_CONTENT;
                        //  params.height=  params.width=ViewGroup.LayoutParams.WRAP_CONTENT;
                          Questions questions= change.getDocument().toObject(Questions.class);
                          if(questions.getId()!=null)
                            loadQuestionFragment(questions);
                            appBarLayout.setVisibility(View.VISIBLE);
                            messagedisplay=true;
                         //   isGameStart=true;
                            //Toast.makeText(getBaseContext(),questions.toString(),Toast.LENGTH_LONG).show();
                            break;
                        case MODIFIED:

                            break;
                        case REMOVED:
try{
manager.beginTransaction().remove(framgentQuestion).commit();
}catch(Exception ex)
{

}
//                            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
                           // params.width=ViewGroup.LayoutParams.MATCH_PARENT;
                            appBarLayout.setVisibility(View.GONE);
                         //   isGameStart=false;
                            break;
                    }
                }
            }




            });


    }
    boolean isFullscreen;
    private YouTubePlayer player;
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
//            this.player = youTubePlayer;
//            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
//            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
//           player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//
//            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener()
//
//            {
//                @Override
//                public void onFullscreen(boolean b)
//                {
//                    isFullscreen = b;
//                }
//            });
//            if (VIDEL_ID != null && !wasRestored) {
//              //  youTubePlayer.loadVideo(VIDEL_ID);
//               // enableDisableView(mYouTubePlayerView,false);
//              //  mYouTubePlayerView.setClickable(false);
//              //  mYouTubePlayerView.setFocusable(false);
//            }
//
//        }

        //youTubePlayer.setPlaybackEventListener(false);
//        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
//        youTubePlayer.setPlaybackEventListener(playbackEventListener);
       // enableDisableView(mYouTubePlayerView,false);
//        youTubePlayer.loadVideo(VIDEL_ID);
//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//
//
//
//        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
//        youTubePlayer.setFullscreen(true);
//        this.player=youTubePlayer;

//player.setFullscreen(true);
        //   maximize();
        //youTubePlayer.setFullscreen(true);
        //  youTubePlayer.play();
//        if(!b)
//        {
//           // youTubePlayer.cueVideo(VIDEL_ID);
//        }
   // }
    public void liveAttendUser()
    {
       // FirebaseFirestore db=FirebaseFirestore.getInstance();
       Query query= db.collection("liveAttend");
       liveAttendRegistration=query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot documentSnapshot:docs)
                {
                    if(documentSnapshot.getId().equals("userNumber"));
                    DocumentSnapshot document=documentSnapshot;
                    String number=""+document.get("number");
                    watchTextView.setText(number);
                }
            }
        });
    }
    public YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {
              player.play();
        }

        @Override
        public void onStopped() {
            player.play();
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };


//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//    }



    //TokBox.Live.......



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer =  findViewById(R.id.publisher_container);
            mSubscriberViewContainer =  findViewById(R.id.subscriber_container);

            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");
        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        //mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
        session.disconnect();
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
        if (mSubscriber != null) {
            mSubscriber = null;
           // mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {


    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

}
