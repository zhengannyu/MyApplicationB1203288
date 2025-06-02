package com.example.myapplicationb1203288

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navChat = findViewById<ImageView>(R.id.nav_chat)
        val navFavorite = findViewById<ImageView>(R.id.nav_favorite)
        val navDiscover = findViewById<ImageView>(R.id.nav_discover)
        val navPoints = findViewById<ImageView>(R.id.nav_points)
        val navSettings = findViewById<ImageView>(R.id.nav_settings)

        navChat.setOnClickListener {
            val intent = Intent(this, chat::class.java)
            startActivity(intent)
        }
        navFavorite.setOnClickListener {
            val intent = Intent(this, favorite::class.java)
            startActivity(intent)
        }
        //navDiscover.setOnClickListener {
        //    val intent = Intent(this, map::class.java)
        //    startActivity(intent)
        //}
        navSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}