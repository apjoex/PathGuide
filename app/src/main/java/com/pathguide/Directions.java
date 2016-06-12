package com.pathguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import models.Classroom;
import models.Lab;
import models.Office;
import models.Restroom;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Directions extends AppCompatActivity {

    Context context;
    Classroom classroom;
    Restroom restroom;
    Office office;
    Lab lab;
    String starting_point;
    TextView destination_text, start_text, direction_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        context = this;
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        classroom = (Classroom)getIntent().getSerializableExtra("class");
        office = (Office) getIntent().getSerializableExtra("office");
        lab = (Lab) getIntent().getSerializableExtra("lab");
        restroom = (Restroom) getIntent().getSerializableExtra("restroom");
        starting_point = getIntent().getStringExtra("location");
        destination_text = (TextView)findViewById(R.id.destination_text);
        start_text = (TextView)findViewById(R.id.start_text);
        direction_text = (TextView)findViewById(R.id.direction_text);

        populateData();

    }

    private void populateData() {
        if(classroom!= null){
            start_text.setText(starting_point);
            destination_text.setText(classroom.getName());
            if(starting_point.contains("bridge")){
                direction_text.setText(classroom.getBridge());
            }else{
                direction_text.setText(classroom.getCar_park());
            }
        }

        if(restroom != null){
            start_text.setText(starting_point);
            if(restroom.getName().equals("male")){
                destination_text.setText("Male restroom");
            }else{
                destination_text.setText("Female restroom");
            }
            if(starting_point.contains("bridge")){
                direction_text.setText(restroom.getBridge());
            }else{
                direction_text.setText(restroom.getCar_park());
            }
        }

        if(office != null){
            start_text.setText(starting_point);
            destination_text.setText(office.getName());
            if(starting_point.contains("bridge")){
                direction_text.setText(office.getBridge());
            }else{
                direction_text.setText(office.getCar_park());
            }
        }

        if(lab!= null){
            start_text.setText(starting_point);
            destination_text.setText(lab.getName());
            if(starting_point.contains("bridge")){
                direction_text.setText(lab.getBridge());
            }else{
                direction_text.setText(lab.getCar_park());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.direction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

                if(id == android.R.id.home){
                    finish();
                }



        if(id == R.id.action_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "PathGuide just made it super easy for me to find places in the faculty of Engineering! Check it out! #Unilag ");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share"));
        }

        if(id == R.id.action_feedback){
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "apjoex@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on PathGuide");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
