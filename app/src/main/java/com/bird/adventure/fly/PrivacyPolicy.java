package com.bird.adventure.fly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class PrivacyPolicy extends AppCompatActivity {

    TextView policy, accept;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        accept = findViewById(R.id.accept);
        policy = findViewById(R.id.policy);

        String data = getIntent().getStringExtra("polisy");
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PrivacyPolicy.this, Policy.class);
                myIntent.putExtra("polisy", data);
                startActivity(myIntent);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PrivacyPolicy.this, ActivityLoader.class);
                startActivity(myIntent);
                finish();
            }
        });

    }
}