package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bim5thsem.home.DashboardActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userNameText;
    private EditText passwordText;
    private Button logInBtn, signUpBtn;
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
        setContentView(R.layout.activity_login);

        iniToolbar();

        userNameText = findViewById(R.id.userNameEditTxt);
        passwordText = findViewById(R.id.passwordEditTxt);
        logInBtn =  findViewById(R.id.logInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        userNameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = userNameText.getText().toString();
                Log.d("TextChanged", text);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logInBtn.setOnClickListener(this);
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
            case R.id.logInBtn:
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra("name","Shahil");
                startActivity(intent);
                finish();
                break;
            case R.id.signUpBtn:
                Intent intent1 = new Intent(this, SignupActivity.class);
                intent1.putExtra("name","Shahil");
                startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }
}
