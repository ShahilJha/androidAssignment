package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bim5thsem.home.DashboardActivity;

import io.realm.Realm;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private int id;
    private Toolbar toolbar;
    private Button dashboardButton, editProfileButton;

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
        setContentView(R.layout.activity_home);

        iniToolbar();
        id = getIntent().getIntExtra("id",0);

        dashboardButton = findViewById(R.id.dashboardButton);
        editProfileButton = findViewById(R.id.editProfileButton);

        dashboardButton.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);


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
            case R.id.editProfileButton:
                transferToEditProfile(id);
                break;

            case R.id.dashboardButton:
                transferToDashboard(id);
                break;

            default:
                break;
        }
    }

    private void transferToDashboard(int id){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void transferToEditProfile(int id){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
