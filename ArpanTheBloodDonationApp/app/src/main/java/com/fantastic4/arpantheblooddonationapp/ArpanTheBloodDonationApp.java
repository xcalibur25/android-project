package com.fantastic4.arpantheblooddonationapp;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArpanTheBloodDonationApp extends Application {
    @Override
    public void onCreate() {
        System.out.println("this is the first page");
        super.onCreate();

    }
}
