package com.example.samch.followthrough;

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

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class Startup extends AppCompatActivity {

    private Box<Player> playersBox;

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
        Button login = findViewById(R.id.start);
        login.setVisibility(View.VISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent loginIntent = new Intent(Startup.this, HomePage.class);
                                            startActivity(loginIntent);
                                        }
                                    }

        );
     /*   new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(Startup.this,HomePage.class);
                startActivity(mainIntent);
                Startup.this.finish();
            }
        }, 3500); //have to set it long enough so the videos get loaded. first time app is used it has to be ~3500. after that, even 1500 works*/
    }

    private void initializePlayerBox(){
        playersBox = ((App)getApplication()).getBoxStore().boxFor(Player.class);
        Player[] players = new Player[100];
        players[0]= new Player("Stephen Curry","android.resource://com.example.samch.followthrough/" + R.raw.stephform);
        players[1] = new Player("Damian Lillard", "android.resource://com.example.samch.followthrough/" + R.raw.dameform);
        players[2] = new Player("Lebron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[3] = new Player("Lebron Jame", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[4] = new Player("Lebron Jams", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[5] = new Player("Lebron Jaes", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[6] = new Player("Lebron Jmes", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[7] = new Player("Lebron ames", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[8] = new Player("Lebro James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[9] = new Player("Lebrn James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[10] = new Player("Leron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[11] = new Player("Lbron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[12] = new Player("ebron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        players[13] = new Player("aLebron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebronform);
        Log.d("App", "loaded all players" );

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
}
