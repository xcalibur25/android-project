package com.bikash.bloodbank;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import static com.bikash.bloodbank.MainActivity.database;

public class DonorForm extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice;

    EditText Name;
    EditText Mobile;

    Button Save;

    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);

        cityChoice = (Spinner) findViewById(R.id.dropdownCity);

        String[] citis = new String[]{"Mumbai","Delhi", "Hyderabad", "Kolkata","Chennai", "Rajasthan", "Bangalore", "Pune"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        cityChoice.setAdapter(adapter);


        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);

        Name = (EditText) findViewById(R.id.edt_name);
        Mobile = (EditText) findViewById(R.id.edt_mobileNumber);
        Save = (Button) findViewById(R.id.btn_saveDonor);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(Name.getText().toString().trim().length()==0){
                    Name.setError("Name not valid");
                    Name.requestFocus();
                }

                if(Mobile.getText().toString().trim().length()==0){
                    Mobile.setError("Mobile number not valid");
                    Mobile.requestFocus();
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Record Saved Successfully",Toast.LENGTH_LONG).show();
                }

                    String name = Name.getText().toString();
                    String city = cityChoice.getSelectedItem().toString();
                    String group = groupChoice.getSelectedItem().toString();
                    String mobile = Mobile.getText().toString();
                    String lat = MainActivity.lat.toString();
                    String lng = MainActivity.lng.toString();


              //  Toast.makeText(getApplicationContext(),"Incorrect Details ",Toast.LENGTH_LONG).show();

                Donor donor = new Donor(name,mobile,group,city,lat, lng);
                DatabaseReference myRef = database.getReference("donors");
                myRef.child(city).child(group).push().setValue(donor);

                finish();
            }
        });

    }
}
