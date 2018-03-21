package com.example.samch.followthrough;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

public class StephVDame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steph_vdame);
        final ImageButton steph = findViewById(R.id.steph);
        final VideoView mVideoView1 =  findViewById(R.id.videoView1);
        String uriPath1 = "android.resource://com.example.samch.followthrough/" + R.raw.stephform;
        final Uri uri1 = Uri.parse(uriPath1);
        steph.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                steph.setVisibility(View.INVISIBLE);
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
                steph.setVisibility(View.VISIBLE);

            }
        });
        final ImageButton stephfs = findViewById(R.id.stephfs);
        stephfs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make fullscreen
                //mVideoView1.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            }
        });

        final ImageButton dame = findViewById(R.id.dame);
        final VideoView mVideoView2 =  findViewById(R.id.videoView2);
        String uriPath2 = "android.resource://com.example.samch.followthrough/" + R.raw.dameform;
        final Uri uri2 = Uri.parse(uriPath2);

        dame.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                dame.setVisibility(View.INVISIBLE);
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
                dame.setVisibility(View.VISIBLE);
            }
        });
        final ImageButton damefs = findViewById(R.id.damefs);
        damefs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
