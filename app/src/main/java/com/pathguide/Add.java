package com.pathguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.Classroom;
import models.Lab;
import models.Office;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Add extends AppCompatActivity {

    Button proceedBtn,postBtn;
    EditText passcode, place_name, place_car_park, place_bridge;
    Context context;
    RelativeLayout passcode_body;
    TextView header_text;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        passcode = (EditText)findViewById(R.id.passcode);
        place_name = (EditText)findViewById(R.id.place_name);
        place_car_park = (EditText)findViewById(R.id.place_car_park);
        place_bridge = (EditText)findViewById(R.id.place_bridge);
        proceedBtn = (Button) findViewById(R.id.proceedBtn);
        postBtn = (Button) findViewById(R.id.postBtn);
        passcode_body = (RelativeLayout)findViewById(R.id.passcode_body);
        header_text = (TextView)findViewById(R.id.header_text);


        clickEvents();
    }

    private void clickEvents() {
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passcode.getText().length() < 4){
                    Toast.makeText(context,"Please enter a four digit passcode", Toast.LENGTH_SHORT).show();
                }else{
                    proceedCodeCheck(passcode.getText().toString());
                }
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(place_name.getText().length()!=0 && place_bridge.getText().length() != 0 && place_car_park.getText().length() != 0){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    switch (status) {
                        case "class": {
                            DatabaseReference myRef = database.getReference();
                            Classroom classroom = new Classroom(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString());
                            myRef.child("classes").child(classroom.getName()).setValue(classroom, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Congratulations! Your content has been successfully added", Toast.LENGTH_SHORT).show();
                                        place_name.setText("");
                                        place_car_park.setText("");
                                        place_bridge.setText("");
                                    }
                                }
                            });
                            break;
                        }
                        case "office": {
                            DatabaseReference myRef = database.getReference();
                            Office office = new Office(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString());
                            myRef.child("offices").child(office.getName()).setValue(office, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Congratulations! Your content has been successfully added", Toast.LENGTH_SHORT).show();
                                        place_name.setText("");
                                        place_car_park.setText("");
                                        place_bridge.setText("");
                                    }
                                }
                            });
                            break;
                        }
                        default: {
                            DatabaseReference myRef = database.getReference();
                            Lab lab = new Lab(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString());
                            myRef.child("labs").child(lab.getName()).setValue(lab, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Congratulations! Your content has been successfully added", Toast.LENGTH_SHORT).show();
                                        place_name.setText("");
                                        place_car_park.setText("");
                                        place_bridge.setText("");
                                    }
                                }
                            });
                            break;
                        }
                    }
                }else{
                    Toast.makeText(context, "Please fill all fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void proceedCodeCheck(String s) {
        if(s.equals("1357")){
            status = "class";
            passcode_body.setVisibility(View.INVISIBLE);
            header_text.setText("Add new classroom");
        }else if(s.equals("2468")){
            status = "office";
            passcode_body.setVisibility(View.INVISIBLE);
            header_text.setText("Add new office");
        }else if(s.equals("1369")){
            status = "lab";
            passcode_body.setVisibility(View.INVISIBLE);
            header_text.setText("Add new laboratory");
        }else{
            Toast.makeText(context,"Incorrect passcode", Toast.LENGTH_SHORT).show();
        }
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
