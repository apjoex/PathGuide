package com.pathguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home extends AppCompatActivity {

    Context context;
    CardView classroom,office,lab,restroom;
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.defaults);

        classroom = (CardView)findViewById(R.id.class_card);
        office = (CardView)findViewById(R.id.office_card);
        lab = (CardView)findViewById(R.id.lab_card);
        restroom = (CardView)findViewById(R.id.restroom_card);
        clickEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    mFirebaseRemoteConfig.activateFetched();
//                    Toast.makeText(Home.this, "The remote config has been fetched!", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(Home.this, "Failed to fetch after "+mFirebaseRemoteConfig.getInfo().getLastFetchStatus(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        mFirebaseRemoteConfig.fetch(2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "Fetch Succeeded");
                        // Once the config is successfully fetched it must be activated before newly fetched
                        // values are returned.
                        mFirebaseRemoteConfig.activateFetched();
                        invalidateOptionsMenu();
//                        Toast.makeText(Home.this, "The remote config has been fetched!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, mFirebaseRemoteConfig.getString("beta"),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
////                        Log.d(TAG, "Fetch failed");
////                        mPriceTextView.setText(mFirebaseRemoteConfig.getString(PRICE_PREFIX_CONFIG_KEY) +
////                                mFirebaseRemoteConfig.getLong(PRICE_CONFIG_KEY));
//                        Toast.makeText(Home.this, "The remote config couldn't be fetched!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clickEvents() {
        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClassroomActivity.class);
                startActivity(intent);
            }
        });

        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfficeActivity.class);
                startActivity(intent);
            }
        });

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LabActivity.class);
                startActivity(intent);
            }
        });

        restroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestroomActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

//        if(!mFirebaseRemoteConfig.getString("beta").equals("true")){
//            menu.removeItem(4);
//        }

        if (mFirebaseRemoteConfig.getString("beta").equals("true")) {
            menu.getItem(4).setVisible(true);
        }else{
            menu.getItem(4).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //        if(id == android.R.id.home){
        //            showExit();
        //        }

        if(id == R.id.action_about){
            Snackbar.make(getWindow().getDecorView(), "Version: "+BuildConfig.VERSION_NAME,Snackbar.LENGTH_SHORT).show();
        }

        if(id == R.id.action_faq){
            Intent faqIntent = new Intent(context, FAQs.class);
            startActivity(faqIntent);
        }

        if(id == R.id.action_credit){
//            Intent faqIntent = new Intent(context, Shaky.class);
//            startActivity(faqIntent);
        }

        if(id == R.id.action_feedback){
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "apjoex@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on PathGuide");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        }

        if(id == R.id.action_add){
            Intent addIntent = new Intent(context, Add.class);
            startActivity(addIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
