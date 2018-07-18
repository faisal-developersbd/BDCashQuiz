package net.smactechnology.medha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SpalashActivity extends AppCompatActivity {
ImageView spalashIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);

        spalashIv=findViewById(R.id.spalashIv);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.spalash_animation);
        spalashIv.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent intent=new Intent(SpalashActivity.this,FacebookLogin.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
