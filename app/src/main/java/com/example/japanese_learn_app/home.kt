package com.example.japanese_learn_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.home_page.*
import android.view.View



class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        visitbtn.setOnClickListener {
            //the color of button will be changed if the button is pressed
            visitbtn.isSelected != visitbtn.isSelected
        }

        //using kotlin to make the action via button
        //allow change to menu page
        //
        // /*
        val btn = findViewById<View>(R.id.visitbtn) as Button

        btn.setOnClickListener {
            startActivity(
                Intent(
                    this@home,
                    Menu::class.java
                )
            )
        }

        val logbtn = findViewById<View>(R.id.loginbtn) as Button

        logbtn.setOnClickListener {
            startActivity(
                Intent(
                    this@home,
                    Login_Activity::class.java
                )
            )
        }



    }


}