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

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTheme(R.style.AppTheme_AB);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF27AE60")));
        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
                getResources().getStringArray(R.array.pros)));
        ListView lv = findViewById(R.id.proList);
        lv.setAdapter(adapter);
        //lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
                Intent compare = new Intent(HomePage.this, StephVDame.class);
                startActivity(compare);
            }
        } );
    }
}