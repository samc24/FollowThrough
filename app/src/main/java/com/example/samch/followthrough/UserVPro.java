package com.example.samch.followthrough;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import io.objectbox.Box;

import static android.media.AudioManager.AUDIOFOCUS_NONE;

public class UserVPro extends AppCompatActivity {
    public static final int PICK_VIDEO_REQUEST = 1;
    public ImageButton choose;
    public VideoView mVideoView1, mVideoView2;
    public ImageButton user, pro, userfs, profs;
    public long playerId;
    public Box<Player> playersBox;
    public Player player;
    public Uri uri1;
    public String path;
    public TextView chooseTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(),"onCreate", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_user_vpro);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        pro();

        choose = findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserVPro.this);

                alertDialogBuilder.create();
                alertDialogBuilder.setMessage("");
                alertDialogBuilder.setCancelable(true);
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
                                startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
                                dialogInterface.dismiss();
                            }
                        });
                alertDialogBuilder.show();
            }
        });


    }

    public void pro() {
        pro = findViewById(R.id.pro);
        profs = findViewById(R.id.profs);
        mVideoView1 = findViewById(R.id.videoView1);
        playerId = getIntent().getExtras().getLong("ProId");
        playersBox = ((App) getApplication()).getBoxStore().boxFor(Player.class);
        player = playersBox.get(playerId);
        uri1 = Uri.parse(player.getLocalFileURI());
        pro.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(View.INVISIBLE);
                mVideoView1.setVideoURI(uri1);
                //mVideoView1.requestFocus();
                mVideoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        /*
                        int videoWidth = mp.getVideoWidth();
                        int videoHeight = mp.getVideoHeight();
                        float videoProportion = (float) videoWidth / (float) videoHeight;
                        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                        float screenProportion = (float) screenWidth / (float) screenHeight;
                        android.view.ViewGroup.LayoutParams lp = mVideoView1.getLayoutParams();
                        if (videoProportion > screenProportion) {
                            lp.width = screenWidth;
                            lp.height = (int) ((float) screenWidth / videoProportion);
                        } else {
                            lp.width = (int) (videoProportion * (float) screenHeight);
                            lp.height = screenHeight;
                        }
                        mVideoView1.setLayoutParams(lp);
                         */

                        mp.setVolume(0.0f, 0.0f);
                    }
                });
                mVideoView1.start();
            }
        });
        mVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Log.d("App","Video Complete");
                mp.setVolume(0.0f, 0.0f);
                pro.setVisibility(View.VISIBLE);

            }
        });

        profs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make fullscreen - make a viewgroup and combine the videoview with the imagebutton
                // mVideoView1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == RESULT_OK) {
                mVideoView2 = findViewById(R.id.videoView2);
                mVideoView2.setVisibility(View.VISIBLE);
                mVideoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Log.d("App","videoOnPrepared");
                        mp.setVolume(0.0f, 0.0f);
                    }

                });
                user = findViewById(R.id.user); // combine all these initializations in another method
                user.setVisibility(View.VISIBLE);
                userfs = findViewById(R.id.userfs);
                userfs.setVisibility(View.VISIBLE);
                choose.setVisibility(View.GONE);
                path = data.getData().toString();
                mVideoView2.setVideoPath(path);
                user();
            }
        }
    }

    public void user() {
        user.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setVisibility(View.INVISIBLE);

                Log.d("App","before start");
                mVideoView2.start();
                Log.d("App","after start");

            }
        });
        mVideoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.setVolume(0.0f, 0.0f);
                user.setVisibility(View.VISIBLE);
            }
        });
        userfs = findViewById(R.id.userfs);
        userfs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        pro.setVisibility(View.VISIBLE); // DRY - dont repeat yourself. Make a sept method for this.
    }

    public void onResume() {
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        pro.setVisibility(View.VISIBLE);
    }

    /*public void onRestart(){
        super.onRestart();
        pro();
    }

    public void onStart(){
        super.onStart();
        pro();
    }*/
}
