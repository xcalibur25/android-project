package com.fantastic4.arpantheblooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fantastic4.arpantheblooddonationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DonorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button registerDonor;
    EditText emailEditText;
    EditText passwordEditText;

    LocationManager locationManager;

    LocationListener locationListener;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
   /* private void registerUser(){

        //getting email and password from edit texts
        String email = emailEditText.getText().toString().trim();
        String password  = passwordEditText.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(DonorSignUp.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(DonorSignUp.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

   /* @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
*/

    public void locate(View view) {
        locationManager = (LocationManager) DonorSignUp.this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocationInfo(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        /*if (Build.VERSION.SDK_INT < 23) {

            startListening();

        } else {*/

        if (ContextCompat.checkSelfPermission(DonorSignUp.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DonorSignUp.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {

                updateLocationInfo(location);

            }

        }

    }


    // }


    public void startListening() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }

    }

    public void updateLocationInfo(Location location) {

        Log.i("LocationInfo", location.toString());


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            String address = "Could not find address";

            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (listAddresses != null && listAddresses.size() > 0) {

                Log.i("PlaceInfo", listAddresses.get(0).toString());

                address = "Address: \n";

                if (listAddresses.get(0).getSubThoroughfare() != null) {

                    address += listAddresses.get(0).getSubThoroughfare() + " ";

                }

                if (listAddresses.get(0).getThoroughfare() != null) {

                    address += listAddresses.get(0).getThoroughfare() + "\n";

                }

                if (listAddresses.get(0).getLocality() != null) {

                    address += listAddresses.get(0).getLocality() + "\n";

                }

                if (listAddresses.get(0).getPostalCode() != null) {

                    address += listAddresses.get(0).getPostalCode() + "\n";

                }

                if (listAddresses.get(0).getCountryName() != null) {

                    address += listAddresses.get(0).getCountryName() + "\n";

                }

            }

            TextView addressTextView = (TextView) findViewById(R.id.addressTextView);

            addressTextView.setText(address);


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startListening();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_sign_up);
        //Firebase.setAndroidContext(this);



        //Text view
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText ageEditText = (EditText) findViewById(R.id.ageEditText);
        final EditText contact = (EditText) findViewById(R.id.contact);
        //final EditText addressTextView = (EditText) findViewById(R.id.addressTextView);
        passwordEditText = (EditText) findViewById(R.id.password);

        // Spinner element
        Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        final Spinner bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);

        // Spinner click listener
        genderSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> gender = new ArrayList<String>();
        gender.add("Male");
        gender.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> genderDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);

        // Drop down layout style - list view with radio button
        genderDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        genderSpinner.setAdapter(genderDataAdapter);

        // Spinner element


        // Spinner click listener
        bloodGroupSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> bloodGroup = new ArrayList<String>();
        bloodGroup.add("A+");
        bloodGroup.add("A-");
        bloodGroup.add("B+");
        bloodGroup.add("B-");
        bloodGroup.add("AB+");
        bloodGroup.add("AB-");
        bloodGroup.add("O+");
        bloodGroup.add("O-");

        // Creating adapter for spinner
        ArrayAdapter<String> bloodDonorDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroup);

        // Drop down layout style - list view with radio button
        bloodDonorDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        bloodGroupSpinner.setAdapter(bloodDonorDataAdapter);

        /*************************Backend Code Here****************************/

        registerDonor = (Button) findViewById(R.id.registerDonor);
        registerDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
                System.out.println(nameEditText.getText().toString());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onSubmit(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //firebaseAuth = FirebaseAuth.getInstance();

        final DatabaseReference myRef = database.getReference("https://arpantheblooddonationapp-c6e80.firebaseio.com/");


    }

}