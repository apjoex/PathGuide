package com.pathguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

import connectors.Database;
import models.Restroom;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RestroomActivity extends AppCompatActivity {

    Context context;
    CardView male, female;
    String selected_gender;
    ArrayList<Restroom> restrooms = new ArrayList<>();
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroom);
        context = this;
        database = new Database(context);
        male = (CardView)findViewById(R.id.male_card);
        female = (CardView)findViewById(R.id.female_card);
        clickEvents();
    }


    private void clickEvents() {
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrooms = database.getSearchRestrooms("male");
                database.close();
                chooseStartingPoint();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrooms = database.getSearchRestrooms("female");
                database.close();
                chooseStartingPoint();
            }
        });
    }

    private void chooseStartingPoint() {
        final CharSequence[] options = {"Engineering car park","Engineering-Science link up bridge"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Where are you coming from?")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                        Intent intent = new Intent(context, Directions.class);
                        intent.putExtra("restroom", restrooms.get(0));
                        intent.putExtra("location",options[position]);
                        startActivity(intent);

                        restrooms.clear();
                    }
                })
                .create().show();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
