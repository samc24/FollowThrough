package com.example.samch.followthrough;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class UserVPro extends AppCompatActivity {
    public static final int PICK_VIDEO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vpro);
        final ImageButton pro = findViewById(R.id.pro);
        final VideoView mVideoView1 =  findViewById(R.id.videoView1);
        String uriPath1 = "android.resource://com.example.samch.followthrough/";
        String form = getIntent().getExtras().getString("Pro");
        switch(form){
            case "Stephen Curry":
                uriPath1 += R.raw.stephform;
                break;
            case "Damian Lillard":
                uriPath1 += R.raw.dameform;
                break;
             default:
                 uriPath1 += R.raw.dameform;
        }
        final Uri uri1 = Uri.parse(uriPath1);
        pro.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                pro.setVisibility(View.INVISIBLE);
                mVideoView1.setVideoURI(uri1);
                mVideoView1.requestFocus();
                mVideoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mp.setVolume(0, 0);
                    }
                });


                mVideoView1.start();
            }
        });
        mVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                pro.setVisibility(View.VISIBLE);

            }
        });
        final ImageButton profs = findViewById(R.id.profs);
        profs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make fullscreen - make a viewgroup and combine the videoview with the imagebutton
               // mVideoView1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
            }
        });
        final Button choose = findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserVPro.this);

                alertDialogBuilder.create();
                alertDialogBuilder.setMessage("");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Record Video",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.GoogleCamera");

                                if (launchIntent != null) {
                                    startActivity(launchIntent);
                                }
                            }
                        });
                alertDialogBuilder.setNeutralButton("Upload Video",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST );


                                //dialogInterface.dismiss();
                            }
                        });
                alertDialogBuilder.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                // The Intent's data Uri identifies which contact was selected.

                // Do something here
            }
        }
    }
    
    public void user(){

        final ImageButton user = findViewById(R.id.user);
        final VideoView mVideoView2 =  findViewById(R.id.videoView2);
        String uriPath2 = "android.resource://com.example.samch.followthrough/" + R.raw.dameform;
        final Uri uri2 = Uri.parse(uriPath2);

        user.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setVisibility(View.INVISIBLE);
                mVideoView2.setVideoURI(uri2);
                mVideoView2.requestFocus();
                mVideoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mp.setVolume(0, 0);
                    }
                });
                mVideoView2.start();

            }
        });
        mVideoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                user.setVisibility(View.VISIBLE);
            }
        });
        final ImageButton userfs = findViewById(R.id.userfs);
        userfs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent record = new Intent(getApplicationContext(),RecordUser.class);
                // startActivity(record);

                // mVideoView2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

            }
        });
    }
}
