package com.fantastic4.arpantheblooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DonorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mPhoneEditText;
    private EditText mAddressTextView;
    private Spinner mBloodGroupSpinner;
    private Spinner mGenderSpinner;
    private Button mRegisterDonor;
    private ProgressDialog mRegProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    String name;
    String email;
    String mobile;
    String password;
    String address;
    String bloodGroupString;
    String genderString;
    String age;


    LocationManager locationManager;

    LocationListener locationListener;


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

        if (Build.VERSION.SDK_INT < 23) {

            startListening();

        } else {

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


    }


    public void startListening() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }

    }

    public void updateLocationInfo(Location location) {

        Log.i("LocationInfo", location.toString());


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            address = "Could not find address";

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


        // Spinner element
        mGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        // Spinner click listener
        mGenderSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> gender = new ArrayList<String>();
        gender.add("Male");
        gender.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> genderDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);

        // Drop down layout style - list view with radio button
        genderDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mGenderSpinner.setAdapter(genderDataAdapter);

        // Spinner element
        mBloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);

        // Spinner click listener
        mBloodGroupSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> bloodGroup = new ArrayList<String>();
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
        mBloodGroupSpinner.setAdapter(bloodDonorDataAdapter);

        mRegProgressDialog = new ProgressDialog(this);


        FirebaseApp.initializeApp(this);
        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance(); // this line crashes the app idk why?

        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mAgeEditText = (EditText) findViewById(R.id.ageEditText);
        mGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        mBloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        mPhoneEditText = (EditText) findViewById(R.id.contact);
        mAddressTextView = (EditText) findViewById(R.id.addressTextView);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mRegisterDonor = (Button) findViewById(R.id.registerDonor);

        mRegisterDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mNameEditText.getText().toString();
                email = mEmailEditText.getText().toString();
                password = mPasswordEditText.getText().toString();
                mobile = mPhoneEditText.getText().toString();
                address = mAddressTextView.getText().toString();
                age = mAgeEditText.getText().toString();
                bloodGroupString = mBloodGroupSpinner.getSelectedItem().toString();
                genderString = mGenderSpinner.getSelectedItem().toString();

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) ||
                        !TextUtils.isEmpty(bloodGroupString) || !TextUtils.isEmpty(mobile) || !TextUtils.isEmpty(address)
                        || !TextUtils.isEmpty(age) || !TextUtils.isEmpty(genderString)) {
                    mRegProgressDialog.setTitle("Registering User");
                    mRegProgressDialog.setMessage("Please wait");
                    mRegProgressDialog.setCanceledOnTouchOutside(false);
                    mRegProgressDialog.show();

                    register_user(name, email, password);
                }

            }
        });

    }

    private void register_user(final String name, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //user.sendEmailVerification();
                    //mEmailverification.setTitle("Check your email and verify it");
                    //mEmailverification.setMessage("Verifying...");
                    // mEmailverification.show();
                    Boolean emailVerfied = user.isEmailVerified();
                    Log.e("Success", String.valueOf(emailVerfied));


                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", name);
                    userMap.put("email", email);
                    userMap.put("password", password);
                    userMap.put("blood_group", bloodGroupString);
                    userMap.put("mobile", mobile);
                    userMap.put("place", address);
                    userMap.put("device_token", device_token);
                    Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_LONG).show();
                    mRegProgressDialog.dismiss();
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //  mRegProgress.dismiss();
                            Intent mainIntent = new Intent(DonorSignUp.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }

                    });

                } else {
                    mRegProgressDialog.hide();
                    Toast.makeText(DonorSignUp.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

    }
}


