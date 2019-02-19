package com.bikash.bloodbank;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class Signup extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }




    public void onSignupClick(View v) {

        if (v.getId() == R.id.Bsignupsubmit) {

            EditText name = (EditText) findViewById(R.id.TFusername);
            EditText email = (EditText) findViewById(R.id.TFemail);
            EditText pass1 = (EditText) findViewById(R.id.TFpass);
            EditText pass2 = (EditText) findViewById(R.id.TFpass2);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            String duplicityCheck = helper.searchExisting(emailstr);

            if (!pass1str.equals(pass2str)) {
                //popup message
                Context context = getApplicationContext();
                String text = "Passwords don't match!";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

            } else if (pass1str.isEmpty()) {
                //password cannot be null

                Toast.makeText(Signup.this, "Password cannot be null", Toast.LENGTH_SHORT).show();

            } else if(!Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()){

                Toast.makeText(Signup.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();

            } else if (duplicityCheck.equals(emailstr)) {
                //checks if email already exists
                Toast.makeText(Signup.this, "Email already exists", Toast.LENGTH_SHORT).show();

            } else {
                //insert details in database

                Contact c = new Contact();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setPass(pass1str);


                helper.insertContact(c);

                Intent i = new Intent(Signup.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Signup Success..!",Toast.LENGTH_LONG).show();
            }


        }
    }
}
