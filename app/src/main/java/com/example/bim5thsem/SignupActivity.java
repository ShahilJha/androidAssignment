package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, mailAddress, password, cPassword;
    private Button signUpBtn, chooseImage;
    private Toolbar toolbar;
    private Dialog originalDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private ImageView imgPhoto;
    private Uri picUri;
    private String currentImagePath;
    private Bitmap bitmap;
    private String type = "";


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home){
            //onBackPressed();
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        else if (item.getItemId() == R.id.call) {
            checkCallPermission();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.toolbar_call, menu);
        return true;
    }

    private void checkCallPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //   is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this, R.style.DialogTheme)
                        .setTitle("Call Permission Needed")
                        .setMessage("This app needs the Call  permission permission , please accept to use Call functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", "com.example.bim5thsem", null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9849850605"));
            startActivity(intent);
        }
    }

    private void checkImagePermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //   is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this, R.style.DialogTheme)
                        .setTitle("Call Permission Needed")
                        .setMessage("This app needs the Camera and external storage  permission permission , please accept to use camera & gallery functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", "com.example.bim5thsem", null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            if (type == "Camera")
                openCamera();
            else
                openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9849850605"));
                        startActivity(intent);
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.example.bim5thsem", null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                break;
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        if (type == "Camera")
                            openCamera();
                        else
                            openGallery();
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.example.bim5thsem", null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                break;
        }


    }

    private void openGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 200);
        } else {
            checkImagePermission();
        }
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create a file to store the image
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if (photoFile != null) {
                    String path = getPackageName() + ".fileprovider";
                    Log.d("Path", path);
                    picUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            picUri);
                    startActivityForResult(pictureIntent,
                            101);
                }

            }
        } else {
            checkImagePermission();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                File imgFile = new File(currentImagePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(imgFile), null, options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imgPhoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 700, 1400, false));
            }
        } else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    picUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                        imgPhoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "JPEG_" + timeStamp,
                ".jpg",
                storageDir
        );

        currentImagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        iniToolbar();

        userName = findViewById(R.id.userName);
        mailAddress = findViewById(R.id.eMail);
        password = findViewById(R.id.passwordText);
        cPassword = findViewById(R.id.confirmPasswordText);

        imgPhoto = findViewById(R.id.imgPhoto);

        chooseImage = findViewById(R.id.chooseImage);
        chooseImage.setOnClickListener(this);

        signUpBtn = findViewById(R.id.signupId);
        signUpBtn.setOnClickListener(this);
    }

    private void iniToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign-Up");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.chooseImage:
                selectImage();
                break;

            case R.id.signupId:
                if (TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(mailAddress.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(cPassword.getText())){
                    Toast.makeText(getApplicationContext(), "Please fill up all spaces", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.getText().toString().equals(cPassword.getText().toString())) {
                        String email = mailAddress.getText().toString().trim();
                        String username = userName.getText().toString().trim();
                        String password = cPassword.getText().toString().trim();

                        User user= new User();
                        user.setEmail(email);
                        user.setUsername(username);
                        user.setPassword(password);
                        saveSignupDb(user);

                        transferToLogin();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            default:
                break;
        }
    }

    private void saveSignupDb(final User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(User.class).max("id");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                user.setId(nextId);
                //...
                realm.insertOrUpdate(user);
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void transferToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void selectImage() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.upload_photo, null);
        TextView txtTakePhoto = dialogView.findViewById(R.id.txtTakePhoto);
        TextView txtChooseGallery = dialogView.findViewById(R.id.txtChooseGallery);
        TextView txtCancel = dialogView.findViewById(R.id.txtCancel);
        alertDialogBuilder.setView(dialogView);
        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Camera";
                originalDialog.dismiss();
                openCamera();
            }
        });
        txtChooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Gallery";
                originalDialog.dismiss();
                openGallery();
            }
        });
        originalDialog = alertDialogBuilder.create();
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalDialog.dismiss();
            }
        });
        originalDialog.getWindow().setDimAmount(0.7f);
        originalDialog.setCanceledOnTouchOutside(true);
        originalDialog.show();
    }
}
