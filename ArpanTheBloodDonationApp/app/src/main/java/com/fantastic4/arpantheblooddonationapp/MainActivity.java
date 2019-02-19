package com.fantastic4.arpantheblooddonationapp;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;

import com.fantastic4.arpantheblooddonationapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public void donateBlood(View view) {
        Button donateBlood = findViewById(R.id.donateBlood);
        Intent donorIntent = new Intent(MainActivity.this, DonorLogin.class);
        startActivity(donorIntent);
    }

    public void requestBlood(View view) {
        Button requestBlood = findViewById(R.id.requestBlood);
        Intent requestIntent = new Intent(MainActivity.this, RequestBlood.class);
        startActivity(requestIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        // mAuth = FirebaseAuth.getInstance();

    }

}
