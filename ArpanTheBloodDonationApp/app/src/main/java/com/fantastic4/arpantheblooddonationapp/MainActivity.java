package com.fantastic4.arpantheblooddonationapp;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;

import com.fantastic4.arpantheblooddonationapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public  void donateBlood(View view){
        Button donateBlood = (Button)findViewById(R.id.donateBlood);
        Intent donorIntent = new Intent(MainActivity.this, DonorLogin.class);
        startActivity(donorIntent);
    }

    public void requestBlood(View view){
        Button requestBlood = (Button)findViewById(R.id.requestBlood);
        Intent requestIntent = new Intent(MainActivity.this, RequestBlood.class);
        startActivity(requestIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void printConsole(View view){
        Button requestBlood=findViewById(R.id.yash_button);
        System.out.println(requestBlood.toString());
    }



}
