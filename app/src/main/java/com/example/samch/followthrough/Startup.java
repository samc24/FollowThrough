package com.example.samch.followthrough;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
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
}
