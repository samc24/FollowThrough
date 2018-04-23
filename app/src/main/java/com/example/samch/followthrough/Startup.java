package com.example.samch.followthrough;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.objectbox.Box;

public class Startup extends AppCompatActivity {

    private Box<Player> playersBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
//        MyObjectBox.builder().androidContext(Startup.this).build();
//        initializePlayerBox();
        Button login = findViewById(R.id.login);
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
        playersBox = (MyObjectBox.builder().androidContext(Startup.this).build()).boxFor(Player.class);
        Player player1 = new Player("Stephen Curry","android.resource://com.example.samch.followthrough/" + R.raw.stephform);
        Player player2 = new Player("Damian Lillard","android.resource://com.example.samch.followthrough/" + R.raw.dameform);
        playersBox.put(player1,player2);
    }
}
