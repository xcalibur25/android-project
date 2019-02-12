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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
