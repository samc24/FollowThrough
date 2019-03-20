package com.example.samch.followthrough;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.appyvet.materialrangebar.RangeBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

import static android.media.AudioManager.AUDIOFOCUS_NONE;

public class UserVPro extends AppCompatActivity {
    public static final int PRO_VIDEO_REQUEST = 2, PICK_VIDEO_REQUEST = 1, UPLOAD_VIDEO_REQUEST = 0;
    public ImageButton choose, proChoose;
    public VideoView mVideoView1, mVideoView2, preview;
    public ImageButton user, pro, userfs, profs, previewPlay, compare;
    public long playerId;
    public Box<Player> playersBox;
    public Player player;
    public Uri uri1;
    public String path;
    public TextView chooseTxt, proTxt;
    public SeekBar speed1, speed2;
    public int progress, duration;
    public Spinner spinner1, spinner2;
    public ArrayAdapter<Double> adapter;
    public RangeBar trimmer;
    public MediaPlayer mediaPlayer, previewMp;
    public float start = 0, end = 4, innerEnd;
    public RelativeLayout proSide, userSide;
    public LayoutInflater inflater;
    public View v;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Toast.makeText(getApplicationContext(),"onCreate", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_user_vpro);
        proSide = findViewById(R.id.proSide);
        userSide = findViewById(R.id.userSide);
        compare = findViewById(R.id.compare);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true); //changing the list item's style to be highlighted because the list items are part of a checkable group
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        Toast.makeText(getApplicationContext(), "Selected:" + menuItem.toString(), Toast.LENGTH_LONG).show();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        // If your app switches out content based on which navigation menu item the user selects, you should consider using fragments in the main content area. Swapping fragments when you navigate from the navigation drawer allows for a seamless drawer animation, because the same base layout stays in place. To learn how to build your layout with fragments, see the Fragments documentation.

                        //TODO: add suitable items. Maybe have: all cardview categories for easy access; access to duos; etc

                        return true;
                    }
                });


        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //pro();

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.performClick();
                pro.performClick();
            }
        });
        proTxt = findViewById(R.id.proTxt);
        proChoose = findViewById(R.id.proBack);
        proChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("video/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(UserVPro.this, HomePage.class);
                startActivityForResult(intent, PRO_VIDEO_REQUEST);
            }
        });

        choose = findViewById(R.id.userBack);
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
                alertDialogBuilder.setNeutralButton("Choose Video",
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

                //upload trial
                alertDialogBuilder.setNegativeButton("Upload Video", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("video/*");
                        intent1.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent1, "Select Video"), UPLOAD_VIDEO_REQUEST);
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });
        mVideoView1 = findViewById(R.id.videoView1);
        mVideoView2 = findViewById(R.id.videoView2);
        if(mVideoView1.getVisibility() == View.VISIBLE && mVideoView2.getVisibility() == View.VISIBLE)
            compare.setVisibility(View.VISIBLE);

    }

    public void pro(Intent data) {
        pro = findViewById(R.id.pro);
        pro.setVisibility(View.VISIBLE);
        profs = findViewById(R.id.profs);
        profs.setVisibility(View.VISIBLE);
        mVideoView1.setVisibility(View.VISIBLE);
        playerId = data.getExtras().getLong("ProId"); // may have to change
        playersBox = ((App) getApplication()).getBoxStore().boxFor(Player.class);
        player = playersBox.get(playerId);
        speed1 = (SeekBar) findViewById(R.id.speed1);
        speed1.setVisibility(View.VISIBLE);
        uri1 = Uri.parse(data.getExtras().getString("ProVid"));
//        uri1 = Uri.parse(player.getLocalFileURI());

        speed1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //pro.setVisibility(View.INVISIBLE);
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pro.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(View.INVISIBLE);
                mVideoView1.setVideoURI(uri1);
//                mVideoView1.requestFocus();
                mVideoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onPrepared(final MediaPlayer mp) {
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

                        try {
                            mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(progress / 100.0f)); //TODO: fix this... apparently mp is not prepared sometimes
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Exception: " + e, Toast.LENGTH_SHORT).show();
                            Log.e("Exception: ", e + "");
                        }


                        mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

                        mp.setVolume(0.0f, 0.0f);

                    }
                });
                mVideoView1.start();
            }
        });
        mVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(final MediaPlayer mp) {
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
        List<Double> spinnerArray = new ArrayList<Double>();
        spinnerArray.add(0.25);
        spinnerArray.add(0.50);
        spinnerArray.add(0.75);
        spinnerArray.add(1.00);
        spinnerArray.add(1.25);
        spinnerArray.add(1.50);
        spinnerArray.add(1.75);
        spinnerArray.add(2.00);
        adapter = new ArrayAdapter<Double>(this,
                R.layout.change_speed, spinnerArray);
        adapter.setDropDownViewResource(R.layout.change_speed);
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setVisibility(View.VISIBLE);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(3);
        
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onResume();
                Object item = adapterView.getItemAtPosition(i);
                //Toast.makeText(adapterView.getContext(), "Speed: " + item, Toast.LENGTH_SHORT).show();
                if (item != null) {
                    progress = (int) (Double.parseDouble(item.toString()) * 100);
                    speed1.setProgress(progress);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onResume();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == RESULT_OK) {
                path = data.getData().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(UserVPro.this);
                inflater = UserVPro.this.getLayoutInflater();
                v = inflater.inflate(R.layout.trim_dialog, null);
                builder.setView(v);
                builder.create();
                trimPreview();
                builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        start = trimmer.getLeftIndex() / 10.0f;
                        end = trimmer.getRightIndex() / 10.0f;
                        compare.setVisibility(View.VISIBLE);
                        user();
                    }
                }).setNegativeButton("Select another video", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setMessage("Trim your video!");
                builder.setCancelable(false);
                builder.show();
            }
        }
        else if (requestCode == UPLOAD_VIDEO_REQUEST){
            path = data.getData().toString();
            Upload up = new Upload(path);
            Log.d("App:", "about to execute upload: " + path);
            up.execute();
        }

        else if (requestCode == PRO_VIDEO_REQUEST){
            proChoose.setVisibility(View.GONE);
            proTxt.setVisibility(View.GONE);
            pro(data);
        }
    }

    public void trimPreview() {
        trimmer = v.findViewById(R.id.trimmer);
        preview = v.findViewById(R.id.preview);
        previewMp = MediaPlayer.create(this, Uri.parse(path));
        duration = previewMp.getDuration();
        preview.setVideoPath(path);
        preview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.setVolume(0.0f, 0.0f);
                mp.seekTo((int) (start * 1000));
                trimmer.setTickEnd(duration / 1000.0f);
                end = trimmer.getRightIndex() / 10.0f;
                innerEnd = end;
                Toast.makeText(getApplicationContext(), "Duration: " + (innerEnd - start) + " seconds", Toast.LENGTH_SHORT).show();
                trimmer.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                        start = leftPinIndex / 10.0f;
                        innerEnd = rightPinIndex / 10.0f;
                        if (!mp.isPlaying())
                            mp.seekTo((int) (start * 1000));
                    }
                });
            }
        });

        previewPlay = v.findViewById(R.id.previewPlay);

        previewPlay.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewPlay.setVisibility(View.INVISIBLE);
                preview.start();
                preview.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        preview.stopPlayback();
                        trimPreview(); // maybe instead of this, add the body og onComplete method
                    }
                }, (long) (innerEnd - start) * 1000);
            }
        });
        preview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // this is never called because of stopPlayback
            public void onCompletion(MediaPlayer mp) {
                mp.setVolume(0.0f, 0.0f);
                mp.seekTo((int) (start * 1000));
                previewPlay.setVisibility(View.VISIBLE);
            }
        });
        previewPlay.setVisibility(View.VISIBLE);


    }

    public void user() {

        mVideoView2.setVisibility(View.VISIBLE);/*
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(data.getData().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
        speed2 = findViewById(R.id.speed2);
        speed2.setVisibility(View.VISIBLE);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setVisibility(View.VISIBLE);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(3);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onResume();
                Object item = adapterView.getItemAtPosition(i);
                if (item != null) {
                    progress = (int) (Double.parseDouble(item.toString()) * 100);
                    speed2.setProgress(progress);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onResume();
            }
        });

        mVideoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.setVolume(0.0f, 0.0f);
                mp.seekTo((int) (start * 1000));
                speed2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        user.setVisibility(View.INVISIBLE);
                        progress = i;/*
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(progress / 100.0f));

                            }
                        });*/
                        try {
                            mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(progress / 100.0f)); //TODO: fix this... apparently mp is not prepared sometimes, trimmed video or untrimmed.
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Exception: " + e, Toast.LENGTH_SHORT).show();
                            user.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });

        user = findViewById(R.id.user); // combine all these initializations in another method
        user.setVisibility(View.VISIBLE);
        userfs = findViewById(R.id.userfs);
        userfs.setVisibility(View.VISIBLE);
        choose.setVisibility(View.GONE);
        mVideoView2.setVideoPath(path);
        chooseTxt = findViewById(R.id.chooseTxt);
        chooseTxt.setVisibility(View.GONE);

        user.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setVisibility(View.INVISIBLE);
                mVideoView2.start();
                mVideoView2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoView2.stopPlayback();
                        user(); // maybe instead of this, add the body og onComplete method
                    }
                }, (long) (end - start) * 1000);
            }
        });
        mVideoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // this is never called because of stopPlayback
            public void onCompletion(MediaPlayer mp) {
                mp.setVolume(0.0f, 0.0f);
                mp.seekTo((int) (start * 1000));
                user.setVisibility(View.VISIBLE);
            }
        });

        userfs = findViewById(R.id.userfs);
        userfs.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        user.setVisibility(View.VISIBLE);
//        pro.setVisibility(View.VISIBLE); // DRY - dont repeat yourself. Make a sept method for this.
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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(mVideoView1.getVisibility() == View.VISIBLE && mVideoView2.getVisibility() == View.VISIBLE)
            compare.setVisibility(View.VISIBLE);
//        pro.setVisibility(View.VISIBLE);
    }

    public void onPause() {
        super.onPause();
        if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public void onRestart() {
        super.onRestart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(mVideoView1.getVisibility() == View.VISIBLE && mVideoView2.getVisibility() == View.VISIBLE)
            compare.setVisibility(View.VISIBLE);
    }

    /*public void onStart(){
        super.onStart();
        pro();
    }*/
}


// Log: Log.d("App", "before start:" + start);
// Toast: Toast.makeText(this, "duration: " + duration / 1000.0f + "seconds", Toast.LENGTH_SHORT).show();