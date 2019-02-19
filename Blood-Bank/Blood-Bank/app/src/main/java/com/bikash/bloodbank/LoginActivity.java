package com.bikash.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }



    public void onButtonclick(View v) {

        if (v.getId() == R.id.Blogin) {

            EditText u = (EditText) findViewById(R.id.ETuname);
            String str = u.getText().toString();
            EditText p = (EditText) findViewById(R.id.ETpassword);
            String passCheck = p.getText().toString();

            String password = helper.searchPass(str);

            if (passCheck.equals(password)) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("Username", str);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Login Successful..!",Toast.LENGTH_LONG).show();
            } else {
                Context context = getApplicationContext();
                String text = "incorrect email/password!";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }

        }

        if (v.getId() == R.id.Bsignup) {

            Intent i = new Intent(LoginActivity.this, Signup.class);

            startActivity(i);


        }
    }


}
