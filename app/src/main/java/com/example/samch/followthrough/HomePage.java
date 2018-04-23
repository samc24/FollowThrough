package com.example.samch.followthrough;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
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
    private ArrayList<String> playerNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTheme(R.style.AppTheme_AB);
//        playersBox = (MyObjectBox.builder().androidContext(HomePage.this).build()).boxFor(Player.class);
//        players = playersBox.query().build().find();
//        for (int i =0; i<players.size();i++){
//            playerNames.add(players.get(i).getPlayerName());
//        }
        // sort pros array (mergesort?)
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF27AE60")));
        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
                getResources().getStringArray(R.array.pros)));
//        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
//                (String[])  playerNames.toArray()));
        ListView lv = findViewById(R.id.proList);
        lv.setAdapter(adapter);

        //lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),((TextView)view).getText(), Toast.LENGTH_SHORT).show();
                Intent compare = new Intent(HomePage.this, UserVPro.class);
                compare.putExtra("Pro", ((TextView)view).getText());
                startActivity(compare);
            }
        } );

    }
}