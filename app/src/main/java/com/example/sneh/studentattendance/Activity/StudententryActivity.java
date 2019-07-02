package com.example.sneh.studentattendance.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sneh.studentattendance.R;
import com.example.sneh.studentattendance.model.Studentinformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StudententryActivity extends AppCompatActivity {
    private EditText edittextrollnum,edittextstudename,edittextclass,edittextsection,edittextaddress,edittextgender,edittextphone;
    private Button Addbutton,uploadbutton,exit;
    int count=0,selectedId;
    int requestCode;
    final private int PICK_IMAGE_REQUEST = 2;
    private int Image_requestcode=2;
    ProgressDialog progressDialog;
    ImageView imageview;
    private String rollnum,name,classs,section,address,key,phoneno,vehiclename,drivername,path1,image,gender1;
    private DatabaseReference databaseReference;
    Uri filpath;
    RadioGroup radiogroup;
    TextView textviewselectgender;
    Spinner spinnervehicle;
    ArrayList<String> vehname;
    String vehiclenamee;
    RadioButton radiobuttonmale,radiobuttonfemale;
    StudentAttendance studentAttendance;
    String userid;
    Context context;
    Resources resources;

    private StorageReference mStorageRef,storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studententry);
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        if(studentAttendance.getLang(StudententryActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(StudententryActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(StudententryActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(StudententryActivity.this, "el");
            resources = context.getResources();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image");
        edittextrollnum = (EditText) findViewById(R.id.edittextid);
        edittextrollnum.setHint(resources.getString(R.string.Studentid));
        imageview = (ImageView) findViewById(R.id.imageview);
        textviewselectgender=(TextView)findViewById(R.id.textviewselectgender);
        textviewselectgender.setText(resources.getString(R.string.select_gender));
        radiobuttonfemale = (RadioButton) findViewById(R.id.radiobuttonfemale);
        radiobuttonfemale.setText(resources.getString(R.string.female));
        radiobuttonmale = (RadioButton) findViewById(R.id.radiobuttonmale);
        radiobuttonmale.setText(resources.getString(R.string.male));
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        edittextstudename = (EditText) findViewById(R.id.edittextstudename);
        edittextstudename.setHint(resources.getString(R.string.enter_student_name));
        edittextclass = (EditText) findViewById(R.id.edittextclass);
        edittextclass.setHint(resources.getString(R.string.enter_class));
        edittextsection = (EditText) findViewById(R.id.edittextsection);
        edittextsection.setHint(resources.getString(R.string.enter_section));
        edittextaddress = (EditText) findViewById(R.id.edittextaddress);
        edittextaddress.setHint(resources.getString(R.string.enter_address));

        Addbutton = (Button) findViewById(R.id.Addbutton);
        Addbutton.setText(resources.getString(R.string.Add));

        exit = findViewById(R.id.exitbutton);
        exit.setText(resources.getString(R.string.exit));

        uploadbutton = (Button) findViewById(R.id.uploadbutton);
        uploadbutton.setText(resources.getString(R.string.uploadimage));

        edittextphone = (EditText) findViewById(R.id.edittextphone);
        edittextphone.setHint(resources.getString(R.string.phoneno));

        spinnervehicle = findViewById(R.id.spinnervehicle);
        vehname= new ArrayList<>();

        vehname.add(0,"Select Vehicle Type");
        vehname.add(1,"Bus");
        vehname.add(2,"Taxi");
        vehname.add(3,"Individual");

        final ArrayAdapter<String> vehicleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, vehname);
        vehicleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnervehicle.setAdapter(vehicleadapter);

        spinnervehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vehiclenamee = vehname.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

exit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        finish();
    }
});
        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }

            uploadbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    //Setting Intent of Image type and select Image from Mobile Storage
                    startActivityForResult(intent, Image_requestcode);

                }


            });

            Addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    insert();

                }
            });

        }



    private void insert()
    {


        rollnum=edittextrollnum.getText().toString().trim();
         name=edittextstudename.getText().toString().trim();
        classs=edittextclass.getText().toString().trim();
        section=edittextsection.getText().toString().trim();
        address=edittextaddress.getText().toString().trim();
        phoneno=edittextphone.getText().toString().trim();
        Log.e("MyTag","Gender=="+gender1);
        Log.e("MyTag","Useridd="+userid);
                if (TextUtils.isEmpty(rollnum))
            {
                Toast.makeText(StudententryActivity.this,"Please Enter Roll Number",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(name))
            {
                Toast.makeText(StudententryActivity.this,"Please Enter Student Name",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String key=databaseReference.push().getKey();
                Log.e("MyTag","Keyy=="+key);

                Studentinformation studentinformation = new Studentinformation( rollnum,name,classs,section,address,gender1,phoneno,vehiclenamee,image,key);
                databaseReference.child(userid).child(key).setValue(studentinformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("MyTag","successtask=="+task.isSuccessful());
                        if (task.isSuccessful())
                        {
                            Toast.makeText(StudententryActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                            clr();
                        }
                        else
                        {
                            Log.e("MyTag","Error"+task.getException().getMessage());
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MyTag","Error"+e.getMessage());

                    }
                });



        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       super.onActivityResult(requestCode,resultCode,data);


                  if(requestCode==Image_requestcode && resultCode==RESULT_OK && data != null && data.getData() != null){
                      filpath=data.getData();
                      Log.e("Mytag","data"+data.getData());
                      progressDialog.show();
                      mStorageRef= FirebaseStorage.getInstance().getReference();
                    //  Uri resultUri = result.getUri();
                      Uri file = Uri.fromFile(new File(getRealPathFromUri(StudententryActivity.this,data.getData())));
                      StorageReference riversRef = mStorageRef.child("images/"+System.currentTimeMillis());

                      riversRef.putFile(file)
                              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                  @Override
                                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                      // Get a URL to the uploaded content
                                      progressDialog.setMessage("Please wait");
                                      progressDialog.dismiss();
                                      Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                                      image = downloadUrl.toString();
                                      //Toast.makeText(StudententryActivity.this, "image path"+image, Toast.LENGTH_SHORT).show();
                                      Log.e("path","path"+downloadUrl);
                                    //  Picasso.get().load(downloadUrl).into(imageview);
                                      Glide.with(context).load(downloadUrl).placeholder(R.mipmap.ic_launcher).into(imageview);
                                     //imageview.setBackground();
                                      Toast.makeText(StudententryActivity.this, "Student Image Set Success", Toast.LENGTH_SHORT).show();

                                  }
                              })
                              .addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception exception) {
                                      // Handle unsuccessful uploads
                                      // ...
                                      progressDialog.dismiss();
                                      Toast.makeText(StudententryActivity.this, "Image Not Set. Please Try Again....", Toast.LENGTH_SHORT).show();

                                  }
                              });




              }





    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();

        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void radiobutton1(View view)
    {
        radiobuttonfemale=(RadioButton)findViewById(radiogroup.getCheckedRadioButtonId());
        gender1=radiobuttonfemale.getText().toString();
        //Toast.makeText(this, ""+radiobuttonfemale.getText(), Toast.LENGTH_SHORT).show();
    }

    public void radiobutton2(View view)
    {
       radiobuttonmale=(RadioButton)findViewById(radiogroup.getCheckedRadioButtonId());
        gender1=radiobuttonmale.getText().toString();
        //Toast.makeText(this, ""+radiobuttonfemale.getText(), Toast.LENGTH_SHORT).show();
    }

    public void clr()
    {
        edittextrollnum.setText("");
        edittextstudename.setText("");
        edittextclass.setText("");
        edittextsection.setText("");
        edittextaddress.setText("");
        edittextphone.setText("");
        radiogroup.clearCheck();
        imageview.setImageResource(R.mipmap.studentattendance);
    }

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission Necessary");
                alertBuilder.setMessage("Permission is necessary");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(StudententryActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(StudententryActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }
}

