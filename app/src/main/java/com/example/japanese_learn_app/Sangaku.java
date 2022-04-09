package com.example.japanese_learn_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class Sangaku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sangaku);

        Button viewers = (Button)findViewById(R.id.viewbtn);
        Button uploader = (Button)findViewById(R.id.uploadbtn);
        Button form = (Button)findViewById(R.id.Sangaku_form_btn);
        //Button Formularbtn=(Button)findViewById(R.id.formbtn);

        uploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sangaku.this, Sangaku_Upload.class));
            }
        });

        if(Login_Activity.member==false){
            uploader.setEnabled(false);
        }
        else {
            uploader.setEnabled(true);
        }

        viewers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sangaku.this, Sangaku_View.class));
            }
        });

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sangaku.this, Sangaku_Form.class));
            }
        });


    }
}