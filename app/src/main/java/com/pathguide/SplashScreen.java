package com.pathguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import connectors.Database;
import models.Classroom;
import models.Lab;
import models.Office;
import models.Restroom;

public class SplashScreen extends AppCompatActivity {

    Context context;
    ArrayList<Classroom> classrooms = new ArrayList<>();
    ArrayList<Office> offices = new ArrayList<>();
    ArrayList<Lab> labs = new ArrayList<>();
    ArrayList<Restroom> restrooms = new ArrayList<>();
    Database local_db;
    DatabaseReference myRef, myOfficeRef, myLabRef, myRestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;
        local_db = new Database(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("classes");
        myOfficeRef = database.getReference("offices");
        myLabRef = database.getReference("labs");
        myRestRef = database.getReference("restrooms");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Classroom classroom = datasnapshot.getValue(Classroom.class);
                    classrooms.add(classroom);
                }
                proceedToRestrooms();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);
//        Classroom classroom = new Classroom("Hardware Lab","This is the direction for Hardware lab from car park","This is the direction for Hardware lab from bridge");
//        myRef.child("classes").child(classroom.getName()).setValue(classroom);
    }

    private void proceedToRestrooms() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Restroom restroom = datasnapshot.getValue(Restroom.class);
                    restrooms.add(restroom);
                }
                proceedToOffices();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };

        myRestRef.addValueEventListener(postListener);
    }

    private void proceedToOffices() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Office office = datasnapshot.getValue(Office.class);
                    offices.add(office);
                }
                proceedToLabs();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };

        myOfficeRef.addValueEventListener(postListener);
    }

    private void proceedToLabs() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Lab lab = datasnapshot.getValue(Lab.class);
                    labs.add(lab);
                }

                local_db.removeRestrooms();
                local_db.saveRestroom(restrooms);
                local_db.removeClasses();
                local_db.saveClassroom(classrooms);
                local_db.removeOffices();
                local_db.saveOffice(offices);
                local_db.removeLabs();
                local_db.saveLab(labs);
                local_db.close();
                proceed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };

        myLabRef.addValueEventListener(postListener);
    }

    private void proceed() {
            new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, Home.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
