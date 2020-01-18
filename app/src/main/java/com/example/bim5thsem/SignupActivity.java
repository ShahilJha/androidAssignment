package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
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

        chooseImage = findViewById(R.id.chooseImage);
        chooseImage.setOnClickListener(this);

        signUpBtn = findViewById(R.id.signupId);
        signUpBtn.setOnClickListener(this);
    }

    private void iniToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BIM 5th Semester");
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
                        Intent intent = new Intent(this, LoginActivity.class);
//                        intent.putExtra("userName", "Shahil");
//                        intent.putExtra("password", "Shahil");
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            default:
                break;
        }
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
