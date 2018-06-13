package com.example.samch.followthrough;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class HomePage extends AppCompatActivity {
    private Box<Player> playersBox;
    private List<Player> players;
    private ArrayList<String> playerNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //getWindow().setEnterTransition(new Fade());
        setTheme(R.style.AppTheme_AB);
        playersBox = ((App)getApplication()).getBoxStore().boxFor(Player.class);
        //playersBox = (MyObjectBox.builder().androidContext(HomePage.this).build()).boxFor(Player.class);
        players = playersBox.query().build().find();
        for (int i =0; i<players.size();i++){
            playerNames.add(players.get(i).getPlayerName());
        }
        // sort pros array (mergesort?)
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003da5")));
//        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
  //              getResources().getStringArray(R.array.pros)));
        final ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
                 playerNames.toArray(new String[playerNames.size()])));
        final PlayerAdapter playerArrayAdapter = (new PlayerAdapter(this,R.layout.player_item, players.toArray(),playersBox));
        ListView lv = findViewById(R.id.proList);
        lv.setAdapter(playerArrayAdapter);

        //lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),((TextView)view).getText(), Toast.LENGTH_SHORT).show();
                Intent compare = new Intent(HomePage.this, UserVPro.class);
                //compare.putExtra("Pro", ((TextView)view).getText());
                compare.putExtra("ProId", players.get(i).getId());
                startActivity(compare);
            }
        } );

    }
}