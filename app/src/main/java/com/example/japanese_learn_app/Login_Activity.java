package com.example.japanese_learn_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {
    public static boolean member = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username, password_user;
        Button login_button;

        login_button = findViewById(R.id.login_btn);
        username =  findViewById(R.id.username);
        password_user =  findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( username.getText().toString().equals("abby") && password_user.getText().toString().equals("1234")){
                    Toast.makeText(Login_Activity.this,"Successful login",Toast.LENGTH_LONG).show();
                    member = true;
                    startActivity(new Intent(Login_Activity.this, Menu.class));
                }
                else {
                    Toast.makeText(Login_Activity.this,"Username or password is wrong. Please try agagin.",Toast.LENGTH_LONG).show();
                }

            }
        });




    }
}