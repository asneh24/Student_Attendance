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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneh.studentattendance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StudentupdateActivity extends AppCompatActivity {
    private EditText edittextstudename,edittextclass,edittextsection,edittextaddress,edittextphone;
    private TextView edittextid;
    private Button updatebutton,deletebutton,imguploadbtn;
    private String Id,Name,classs,Section,addresss,key,gender,phone,Vehiclename,drivername,image;
    private DatabaseReference databaseReference;
    ImageView imageView;
    StudentAttendance studentAttendance;
    String userid;
    RadioGroup radiogroup;
    RadioButton radiobuttonmale,radiobuttonfemale;
    ProgressDialog progressDialog;
    Context context;
    Uri filpath;
    Resources resources;
    private int Image_requestcode=2;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentupdate);
        studentAttendance=(StudentAttendance)getApplication();
        userid = studentAttendance.getUserid();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload image");
        if(studentAttendance.getLang(StudentupdateActivity.this)==0)
        {
            context = LocaleManager.setNewLocale(StudentupdateActivity.this, "en");
            resources = context.getResources();
        }
        else if(studentAttendance.getLang(StudentupdateActivity.this)==1)
        {
            context = LocaleManager.setNewLocale(StudentupdateActivity.this, "el");
            resources = context.getResources();
        }

        radiobuttonfemale = (RadioButton) findViewById(R.id.radiobuttonfemale);
        radiobuttonfemale.setText(resources.getString(R.string.female));
        radiobuttonmale = (RadioButton) findViewById(R.id.radiobuttonmale);
        radiobuttonmale.setText(resources.getString(R.string.male));
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

        key=getIntent().getStringExtra("key");
        Id=getIntent().getStringExtra("id");
        Name=getIntent().getStringExtra("name");
        classs=getIntent().getStringExtra("class");
        Section=getIntent().getStringExtra("section");
        image=getIntent().getStringExtra("image");
        addresss = getIntent().getStringExtra("address");
        phone = getIntent().getStringExtra("phone");
        gender = getIntent().getStringExtra("gender");

        edittextid=(TextView) findViewById(R.id.edittextid);
        edittextid.setHint(resources.getString(R.string.Studentid));
        edittextstudename=(EditText)findViewById(R.id. edittextstudename);
        edittextstudename.setHint(resources.getString(R.string.StudentName));
        edittextclass=(EditText)findViewById(R.id.  edittextclass);
        edittextclass.setHint(resources.getString(R.string.Class));
        edittextsection=(EditText)findViewById(R.id.edittextsection);
        edittextsection.setHint(resources.getString(R.string.Section));
        edittextaddress = (EditText)findViewById(R.id.edittextaddress);
        edittextaddress.setHint(resources.getString(R.string.enter_address));
        edittextphone = (EditText)findViewById(R.id.edittextphonenum);
        edittextphone.setHint(resources.getString(R.string.phoneno));

        deletebutton= (Button) findViewById(R.id.deletebutton);
        deletebutton.setText(resources.getString(R.string.Delete));

        imguploadbtn = findViewById(R.id.uploadbutton);
        imguploadbtn.setText(resources.getString(R.string.uploadimage));

        updatebutton= (Button) findViewById(R.id.updatebutton);
        updatebutton.setText(resources.getString(R.string.Update));
        imageView = (ImageView) findViewById(R.id.imageview);

        Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).into(imageView);

        edittextid.setText(Id);
        edittextstudename.setText(Name);
        edittextclass.setText(classs);
        edittextsection.setText(Section);
        edittextaddress.setText(addresss);
        edittextphone.setText(phone);
        Log.e("MyTag","Genderrr = "+gender);
        if(!(gender == null))
        {
            if (gender.equalsIgnoreCase("Male")) {
                radiogroup.check(R.id.radiobuttonmale);
            } else if (gender.equalsIgnoreCase("Female")) {
                radiogroup.check(R.id.radiobuttonfemale);
            }
        }

        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }
        imguploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                //Setting Intent of Image type and select Image from Mobile Storage
                startActivityForResult(intent, Image_requestcode);
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update();
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Student");
                Log.e("My Tag","Keyy for Delete = "+key);
                Log.e("My Tag","userid for Delete = "+userid);
                databaseReference.child(userid).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StudentupdateActivity.this,"Student Information Delete Successfully",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });



            }
        });

    }

    private void Update()
    {
        if (edittextstudename.length() == 0) {
           Toast.makeText(StudentupdateActivity.this,"Please Enter Student Name",Toast.LENGTH_SHORT).show();
        }
        else {
            Id = edittextid.getText().toString();
            Name = edittextstudename.getText().toString();
            classs = edittextclass.getText().toString();
            Section = edittextsection.getText().toString();
            addresss = edittextaddress.getText().toString();
            phone = edittextphone.getText().toString();
           // Studentinformation studentinformation = new Studentinformation(Id, Name, Class, Section, gender, addresss, phone, image);

            databaseReference = FirebaseDatabase.getInstance().getReference("Student");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("Mytag", "data==" + dataSnapshot.getValue());
                    if(dataSnapshot != null)
                    {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            Log.e("Mytag", "data1==" + dataSnapshot1.getValue());
                            if(dataSnapshot1 !=null)
                            {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                                {
                                    Log.e("Mytag", "data2==" + dataSnapshot2.getValue());
                                    Log.e("Mytag", "keyyfromdata" + dataSnapshot2.getKey());
                                    if (dataSnapshot2.getKey().equals(key)) {
                                        Map<String, Object> taskMap = new HashMap<String, Object>();
                                        taskMap.put("image",image);
                                        taskMap.put("name",Name);
                                        taskMap.put("classe", classs);
                                        taskMap.put("section",Section);
                                        taskMap.put("address",addresss);
                                        taskMap.put("phoneno",phone);
                                        taskMap.put("gender",gender);

                                        dataSnapshot2.getRef().updateChildren(taskMap);
                                        Toast.makeText(StudentupdateActivity.this,"Student Information Updated Successfully",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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
            Uri file = Uri.fromFile(new File(getRealPathFromUri(StudentupdateActivity.this,data.getData())));
            StorageReference riversRef = mStorageRef.child("images/"+System.currentTimeMillis());

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.setMessage("wait");
                            progressDialog.dismiss();
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                            image = downloadUrl.toString();
                            //Toast.makeText(StudententryActivity.this, "image path"+image, Toast.LENGTH_SHORT).show();
                            Log.e("path","path"+downloadUrl);
                            Picasso.get().load(String.valueOf(downloadUrl)).into(imageView);
                            //imageview.setBackground();
                            Toast.makeText(StudentupdateActivity.this, "upload image sucessfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(StudentupdateActivity.this, "image not upload try again", Toast.LENGTH_SHORT).show();

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
        gender=radiobuttonfemale.getText().toString();
        //Toast.makeText(this, ""+radiobuttonfemale.getText(), Toast.LENGTH_SHORT).show();
    }

    public void radiobutton2(View view)
    {
        radiobuttonmale=(RadioButton)findViewById(radiogroup.getCheckedRadioButtonId());
        gender=radiobuttonmale.getText().toString();
        //Toast.makeText(this, ""+radiobuttonfemale.getText(), Toast.LENGTH_SHORT).show();
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
                        ActivityCompat.requestPermissions(StudentupdateActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(StudentupdateActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
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

