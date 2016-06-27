package com.pathguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
    ImageView pic;
    ProgressBar loading;
    CoordinatorLayout pic_body;
    FloatingActionButton expand;
    String url;

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
        pic_body = (CoordinatorLayout)findViewById(R.id.pic_body);
        pic = (ImageView)findViewById(R.id.pic);
        loading = (ProgressBar)findViewById(R.id.loading);
        expand = (FloatingActionButton)findViewById(R.id.expand);

        if (expand != null) {
            expand.hide();
        }
        pic_body.setVisibility(View.VISIBLE);

        populateData();

        clickEvents();

    }

    private void clickEvents() {

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlideBitmapDrawable image=(GlideBitmapDrawable)pic.getDrawable();
//                pic.buildDrawingCache();
//                Bitmap image= pic.getDrawingCache();
//                Toast.makeText(Directions.this, ""+image.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ExpandActivity.class);
                intent.putExtra("imageUrl", url);
                startActivity(intent);
            }
        });
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
            url = classroom.getUrl();
            Glide.with(context)
                    .load(classroom.getUrl())
                    .error(R.drawable.image)
                    .fitCenter()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pic_body.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            new CountDownTimer(1000,1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    expand.show();
                                }
                            }.start();
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pic);
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
            url = restroom.getUrl();
            Glide.with(context)
                    .load(restroom.getUrl())
                    .error(R.drawable.image)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pic_body.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            new CountDownTimer(1000,1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    expand.show();
                                }
                            }.start();
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pic);
        }

        if(office != null){
            start_text.setText(starting_point);
            destination_text.setText(office.getName());
            if(starting_point.contains("bridge")){
                direction_text.setText(office.getBridge());
            }else{
                direction_text.setText(office.getCar_park());
            }
            url = office.getUrl();
            Glide.with(context)
                    .load(office.getUrl())
                    .error(R.drawable.image)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pic_body.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            new CountDownTimer(1000,1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    expand.show();
                                }
                            }.start();
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pic);
        }

        if(lab!= null){
            start_text.setText(starting_point);
            destination_text.setText(lab.getName());
            if(starting_point.contains("bridge")){
                direction_text.setText(lab.getBridge());
            }else{
                direction_text.setText(lab.getCar_park());
            }
            url = lab.getUrl();
            Glide.with(context)
                    .load(lab.getUrl())
                    .error(R.drawable.image)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pic_body.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            new CountDownTimer(1000,1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    expand.show();
                                }
                            }.start();
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(pic);
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
