package com.example.japanese_learn_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.compose.ui.text.intl.Locale;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.Currency;

import com.google.firebase.installations.Utils;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        final RelativeLayout mode;

        mode = findViewById(R.id.setlayout);


        Button Darkbtn,LightBtn,Cnbtn,ENbtn;
        Darkbtn = findViewById(R.id.darkbtn);
        LightBtn= findViewById(R.id.whitebtn);
        ENbtn = findViewById(R.id.ENbtn);
        Cnbtn =findViewById(R.id.CNbtn);

        Darkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mode.setBackgroundResource(R.color.gray);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

        });



        LightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mode.setBackgroundResource(R.color.pink);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                //setLocale("Zh");

            }
            
        });


        Cnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Darkbtn.setText("轉換夜間模式");
                    LightBtn.setText("轉換日間模式");

            }
        });


        ENbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Darkbtn.setText("CHANGE TO DARK MODE");
                LightBtn.setText("CHANGE TO BRIGHT MODE");

            }
        });
    }


}