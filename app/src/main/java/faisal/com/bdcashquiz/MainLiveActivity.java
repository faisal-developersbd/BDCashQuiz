package faisal.com.bdcashquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

import faisal.com.bdcashquiz.model.Questions;

public class MainLiveActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
private LinearLayout linearLayout;
private WebView webview;
private android.app.FragmentManager manager;
private VideoView videoView;
    private YouTubePlayerView mYouTubePlayerView;
private ProgressDialog mDialog;
    public static final String APP_KEY="AIzaSyBgbP5yQ9KdRnl8GM9fqWxl-EQSNWkAFDg";
    public static  String VIDEL_ID="";
    private AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_live);
        VIDEL_ID=getIntent().getStringExtra("videoId");
        Log.d("videoId","In Main: "+VIDEL_ID);
        realTimeMonitorUpdate();
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
        mYouTubePlayerView=(YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        mYouTubePlayerView.initialize(APP_KEY,this);
        Drawable drawable = AppCompatResources.getDrawable(getBaseContext(),R.drawable.ic_chatbackground);
        AppBarLayout layout=findViewById(R.id.chatbarlayout);
        layout.setBackground(drawable);
        appBarLayout=findViewById(R.id.appBarLayout);
        appBarLayout.setBackground(drawable);
        appBarLayout.setVisibility(View.GONE);

      //  manager.beginTransaction().remove(framgentQuestion).commit();
       // playVideo();
    }
    public void realTimeMonitorUpdate()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("liveMonitor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                List<DocumentSnapshot> docs=queryDocumentSnapshots.getDocuments();
                DocumentSnapshot document=docs.get(0);


                String isLive=""+document.get("live");
                if(isLive.equals("false")) {
                    Intent intent = new Intent(MainLiveActivity.this, FirstActivity.class);

                    startActivity(intent);
                }
            }
        });
    }
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
        framgentQuestion.setQuestions(questions);
        manager.beginTransaction().replace(R.id.displayQuestionLayout,framgentQuestion).commit();

    }
    public void attendUser()

    {
        HashMap<String,String> hashMap=new HashMap<>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("liveuser").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("usersubmit","user joined");
            }
        });
    }
    ViewGroup.LayoutParams params;
    public void liveUpdate()
    {
       // manager.beginTransaction().remove(framgentQuestion).commit();
        if(mYouTubePlayerView==null)
        mYouTubePlayerView=findViewById(R.id.youTubePlayerView);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
      params = mYouTubePlayerView.getLayoutParams();
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
                            //Toast.makeText(getBaseContext(),questions.toString(),Toast.LENGTH_LONG).show();
                            break;
                        case MODIFIED:

                            break;
                        case REMOVED:

manager.beginTransaction().remove(framgentQuestion).commit();
                            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
                            params.width=ViewGroup.LayoutParams.MATCH_PARENT;
                            appBarLayout.setVisibility(View.GONE);
                            break;
                    }
                }
            }




            });


    }
    boolean isFullscreen;
    private YouTubePlayer player;
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            this.player = youTubePlayer;
            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
           player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener()

            {
                @Override
                public void onFullscreen(boolean b)
                {
                    isFullscreen = b;
                }
            });
            if (VIDEL_ID != null && !wasRestored) {
                youTubePlayer.loadVideo(VIDEL_ID);
            }

        }
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

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public YouTubePlayer.PlayerStateChangeListener playerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
