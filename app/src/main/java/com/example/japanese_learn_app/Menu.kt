package com.example.japanese_learn_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.japanese_learn_app.R

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val chatbtn = findViewById<View>(R.id.chatbtn) as ImageButton

        chatbtn.setOnClickListener {
            startActivity(
                Intent(
                    this@Menu,
                    chatroom::class.java
                )
            )
        }

        val setbtn = findViewById<View>(R.id.settingbtn) as ImageButton

        setbtn.setOnClickListener {
            startActivity(
                Intent(
                    this@Menu,
                    Setting::class.java
                )
            )
        }

        val Helpbtn = findViewById<View>(R.id.helpbtn) as ImageButton

        Helpbtn.setOnClickListener {
            startActivity(
                Intent(
                    this@Menu,
                    Help::class.java
                )
            )
        }

        val Sangbtn = findViewById<View>(R.id.Sangakubtn) as ImageButton

        Sangbtn.setOnClickListener {
            startActivity(
                Intent(
                    this@Menu,
                    Sangaku::class.java
                )
            )
        }

    }
}