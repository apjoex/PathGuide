package com.pathguide;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import controllers.Utilities;
import models.Classroom;
import models.Lab;
import models.Office;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Add extends AppCompatActivity {

    Button proceedBtn,postBtn, uploadBtn;
    EditText passcode, place_name, place_car_park, place_bridge;
    Context context;
    RelativeLayout passcode_body;
    TextView header_text;
    String status;
    private static final int PICS_FROM_CAMERA = 0;
    private static final int PICS_FROM_GALLERY = 1;
    protected static Uri imageUri;
    StorageReference storageRef, imagesRef;
    String time;
    ImageView photo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://project-1066840240253461094.appspot.com");
        imagesRef = storageRef.child("images");

        passcode = (EditText)findViewById(R.id.passcode);
        place_name = (EditText)findViewById(R.id.place_name);
        place_car_park = (EditText)findViewById(R.id.place_car_park);
        place_bridge = (EditText)findViewById(R.id.place_bridge);
        proceedBtn = (Button) findViewById(R.id.proceedBtn);
        postBtn = (Button) findViewById(R.id.postBtn);
        passcode_body = (RelativeLayout)findViewById(R.id.passcode_body);
        header_text = (TextView)findViewById(R.id.header_text);
        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        photo = (ImageView)findViewById(R.id.photo);
        photo.setVisibility(View.GONE);
//        header_text.setTextSize(20);
//        header_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Montserrat.otf"), Typeface.BOLD);

        clickEvents();
    }

    private void clickEvents() {

        passcode_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passcode.getText().length() < 4){
                    Toast.makeText(context,"Please enter a four digit passcode", Toast.LENGTH_SHORT).show();
                    Utilities.vibratePhone(context, 300);
                    passcode.setText("");
                }else{
                    proceedCodeCheck(passcode.getText().toString());
                }
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(place_name.getText().length()!=0 && place_bridge.getText().length() != 0 && place_car_park.getText().length() != 0 && imageUri != null) {
                    progressDialog = ProgressDialog.show(context, null, "Uploading...", false, false);
                    uploadImage(imageUri);
                }else{
                    Toast.makeText(context, "Please fill all fields",Toast.LENGTH_SHORT).show();
                    Utilities.vibratePhone(context, 300);
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoSelectOption();
            }
        });
    }

    private void postToFirebase(String photoUrl) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            switch (status) {
                case "class": {
                    DatabaseReference myRef = database.getReference();
                    Classroom classroom = new Classroom(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString(),photoUrl);
                    myRef.child("classes").child(classroom.getName()).setValue(classroom, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
                                Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                            } else {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
                                Toast.makeText(context, "Congratulations! Your content has been successfully added", Toast.LENGTH_SHORT).show();
                                place_name.setText("");
                                place_car_park.setText("");
                                place_bridge.setText("");
                                photo.setVisibility(View.GONE);
                                uploadBtn.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    break;
                }
                case "office": {
                    DatabaseReference myRef = database.getReference();
                    Office office = new Office(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString(),photoUrl);
                    myRef.child("offices").child(office.getName()).setValue(office, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
                                Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                            } else {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
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
                    Lab lab = new Lab(place_name.getText().toString(), place_car_park.getText().toString(), place_bridge.getText().toString(),photoUrl);
                    myRef.child("labs").child(lab.getName()).setValue(lab, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
                                Toast.makeText(context, "Whoops! Something went wrong somewhere! Please try again...", Toast.LENGTH_SHORT).show();
                            } else {
                                if(progressDialog.isShowing()){ progressDialog.dismiss(); }
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
    }

    private void showPhotoSelectOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setTitle("Add Photo");
        final String[] items = new String[]{"Take a Photo", "Select From Gallery"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take a Photo")) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Add.this,
                                new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                10);
                    }else{
                        TakePhoto();
                    }

                } else {
                    SelectPhoto();
                }
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TakePhoto();
                } else {
                    Toast.makeText(context,"Please grant permissions to take photos",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void TakePhoto(){
//        Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
//                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
//        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
//        try {
//            intent.putExtra("return-data", true);
//            startActivityForResult(intent, PICS_FROM_CAMERA);
//        } catch (ActivityNotFoundException e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        time = String.valueOf(System.currentTimeMillis());
        Uri uri  = Uri.parse("file:///sdcard/"+time+".jpg");
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,PICS_FROM_CAMERA);
    }

    private void SelectPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PICS_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {
            //Do nothing for now
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case PICS_FROM_GALLERY:
                    time = String.valueOf(System.currentTimeMillis());
                    imageUri = data.getData();
                    InputStream is;
                    try {
                        is = getContentResolver().openInputStream(imageUri);
                        uploadBtn.setVisibility(View.GONE);
                        photo.setVisibility(View.VISIBLE);
                        photo.setImageBitmap(BitmapFactory.decodeStream(is));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    uploadImage(mImageCaptureUri);
////                File file = new File(Environment.getExternalStorageDirectory().getPath(), time+".jpg");
////                Uri uri = Uri.fromFile(file);
////                uploadImage(uri);
////                Bitmap bitmap;
////                try {
////                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//////                    bitmap = crupAndScale(bitmap, 300); // if you mind scaling
//////                    pofileImageView.setImageBitmap(bitmap);
////                } catch (FileNotFoundException e) {
////                    // TODO Auto-generated catch block
////                    e.printStackTrace();
////                } catch (IOException e) {
////                    // TODO Auto-generated catch block
////                    e.printStackTrace();
////                }
                break;
            case PICS_FROM_CAMERA:
//                    path = getFilePath(data.getData());
//                    mImageCaptureUri = Uri.parse(path);
//                    uploadImage(mImageCaptureUri);
                File file = new File(Environment.getExternalStorageDirectory().getPath(), time+".jpg");
                imageUri = Uri.fromFile(file);
                InputStream is2;
                try {
                    is2 = getContentResolver().openInputStream(imageUri);
                    uploadBtn.setVisibility(View.GONE);
                    photo.setVisibility(View.VISIBLE);
                    photo.setImageBitmap(BitmapFactory.decodeStream(is2));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private String getFilePath(Uri _uri){
        String filePath = null;
        if (_uri != null && "content".equals(_uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(_uri, new String[]
                    { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();   
            filePath = cursor.getString(0);
            cursor.close();
        }
        else {
            filePath = _uri.getPath();
        }
        return filePath;
    }

    private void uploadImage(Uri mImageCaptureUri) {
        imagesRef.child(time).putFile(mImageCaptureUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String photoUrl = taskSnapshot.getDownloadUrl().toString();
//                        DatabaseReference databaseRefPhoto = mDatabase.getReference("/posts/"+post_key+"/photos/");
//                        PicUpload picUpload = new PicUpload(photoUrl);
//                        Map<String, Object> urlValues = picUpload.toMap();
//                        Map<String, Object> childUpdatesUrl = new HashMap<>();
//                        childUpdatesUrl.put(String.valueOf(position), urlValues);
//                        databaseRefPhoto.updateChildren(childUpdatesUrl);
//                        if(position == selectedImages.size()){
//                            cancelProgressDialog();
//                            Utilities.showToast(context,"Post submitted.");
//                            finish();
//                        }
//                        Toast.makeText(Add.this, "Image upload successful with link "+photoUrl, Toast.LENGTH_SHORT).show();
                        postToFirebase(photoUrl);

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
//                        cancelProgressDialog();
//                        Utilities.showToast(context,exception);
                        Toast.makeText(Add.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                        finish();
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
            Utilities.vibratePhone(context, 300);
            passcode.setText("");
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
