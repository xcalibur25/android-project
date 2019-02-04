package com.example.textfieldchallege;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logInClicked(View view){

        EditText usernameEditText= (EditText)findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Log.i("Username", usernameEditText.getText().toString());

       // Toast.makeText(MainActivity.this,  usernameEditText.getText().toString(), Toast.LENGTH_SHORT).show();

        Log.i("Password", passwordEditText.getText().toString());
       Toast.makeText(MainActivity.this, usernameEditText.getText().toString()+" "+passwordEditText.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
