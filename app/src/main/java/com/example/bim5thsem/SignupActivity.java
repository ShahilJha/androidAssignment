package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, mailAddress, password, cPassword;
    private Button signUpBtn;
    private Toolbar toolbar;

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
}
