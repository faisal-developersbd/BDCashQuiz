package faisal.com.bdcashquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import me.msfjarvis.apprate.AppRate;

public class AboutActivity extends AppCompatActivity {
private AppCompatButton rate_btn;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings");

        rate_btn=findViewById(R.id.rate_btn);

        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=faisal.com.bdcashquiz")));
                }catch (ActivityNotFoundException e){

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=faisal.com.bdcashquiz")));
                }


            }
        });

    }
}
