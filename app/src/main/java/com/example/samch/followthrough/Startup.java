package com.example.samch.followthrough;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        login.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent loginIntent = new Intent(Startup.this, HomePage.class);
                                            startActivity(loginIntent);
                                        }
                                    }

        );
    }

    private void initializePlayerBox(){
        playersBox = ((App)getApplication()).getBoxStore().boxFor(Player.class);
        Player[] players = new Player[100];
        players[0]= new Player("Stephen Curry","android.resource://com.example.samch.followthrough/" + R.raw.stephform);
        players[1] = new Player("Damian Lillard", "android.resource://com.example.samch.followthrough/" + R.raw.dameform);
        players[2] = new Player("Lebron James", "android.resource://com.example.samch.followthrough/" + R.raw.lebroncrop);
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
