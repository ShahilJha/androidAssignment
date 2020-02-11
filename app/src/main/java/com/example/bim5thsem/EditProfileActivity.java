package com.example.bim5thsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class EditProfileActivity extends AppCompatActivity {

    private int id;
    private Toolbar toolbar;
    private Button updateButton;
    private EditText eMail, userName, password, cPassword;
    User user;

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
        setContentView(R.layout.activity_edit_profile);

        iniToolbar();

        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            Log.d("IntentValue", String.valueOf(id));
        }
        getUser();

        findView();
        eMail.setText(user.getEmail());
        userName.setText(user.getUsername());
        password.setText(user.getPassword());
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
                transferToHome(id);
            }
        });


    }

    private void iniToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BIM 5th Semester");
    }

    private void findView(){
        updateButton = findViewById(R.id.updateButton);
        eMail = findViewById(R.id.eMail);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cPassword);
    }

    private void getUser() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user = realm.where(User.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
    }
    private void updateUser() {
        User user=new User();
        user.setId(this.user.getId());
        user.setUsername(userName.getText().toString().trim());
        user.setEmail(eMail.getText().toString().trim());
        if (password.getText().toString().equals(cPassword.getText().toString())) {
            user.setPassword(password.getText().toString());
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        Toast.makeText(this, "User data updated successfully.", Toast.LENGTH_SHORT).show();
    }
    private void transferToHome(int i){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }
}
