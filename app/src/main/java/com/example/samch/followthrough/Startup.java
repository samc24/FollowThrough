package com.example.samch.followthrough;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class Startup extends AppCompatActivity {

    private Box<Player> playersBox;
    private ImageView logo;
    private TextView slogan;
    long animationDuration = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_startup);


        initializePlayerBox();
        logo = (ImageView) findViewById(R.id.logobig);
        handleAnimationLogo(logo);
        slogan = findViewById(R.id.slogan);
        handleAnimationText(slogan);
        /*Button login = findViewById(R.id.start);

        login.setVisibility(View.VISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent loginIntent = new Intent(Startup.this, UserVPro.class);
                                            startActivity(loginIntent);
                                        }
                                    }

        );*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Startup.this,UserVPro.class);
                startActivity(mainIntent);
                Startup.this.finish();
            }
        }, 1500); //have to set it long enough so the videos get loaded. first time app is used it has to be ~3500. after that, even 1500 works
    }

    private void initializePlayerBox(){
        playersBox = ((App)getApplication()).getBoxStore().boxFor(Player.class);
//        playersBox.removeAll(); // uncomment this whenever changes are made to the players or videos, because if not then the old ones will stay in the box and will lead to errors (responsible for cant play video)
        Player[] players = new Player[100];
        players[0]= new Player("Stephen Curry", R.drawable.stephprof, "Quick Release\nWide Base\nOne Motion");
        players[1] = new Player("Damian Lillard", R.drawable.dameprof,"yeet\nyeet\nskrrt");
        players[2] = new Player("Lebron James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[3] = new Player("Lebron Jame", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[4] = new Player("Lebron Jams", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[5] = new Player("Lebron Jaes", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[6] = new Player("Lebron Jmes", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[7] = new Player("Lebron ames", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[8] = new Player("Lebro James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[9] = new Player("Lebrn James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[10] = new Player("Leron James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[11] = new Player("Lbron James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[12] = new Player("Ebron James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[13] = new Player("ALebron James", R.drawable.lebronprof,"kobe>bron\nking\nlabron");
        players[14] = new Player("Klay Thompson", R.drawable.klayprof,"Quick Release\nWide Base\nOne Motion\nMeme Lord");

        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                QueryBuilder<Player> builder = playersBox.query();
                builder.equal(Player_.playerName, players[i].playerName);
                Query<Player> query = builder.build();
                if (query.findFirst() == null) {
                    playersBox.put(players[i]);
                }
            }
        }
    }

    public void onResume(){
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void handleAnimationLogo(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(logo,"x", 200f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(logo,"y", 300f);
        animatorX.setDuration(animationDuration);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void handleAnimationText(View view){
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1.0f)
                .setDuration(animationDuration)
                .setListener(null);
    }
}
