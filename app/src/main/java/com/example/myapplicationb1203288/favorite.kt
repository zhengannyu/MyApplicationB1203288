package com.example.myapplicationb1203288

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class favorite : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ivback = findViewById<ImageView>(R.id.iv_back)
        ivback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val tryfirst = findViewById<ToggleButton>(R.id.favoriteToggleButton)
        val trytwo = findViewById<ToggleButton>(R.id.button2)
        val trythree = findViewById<ToggleButton>(R.id.button3)
        tryfirst.setOnCheckedChangeListener{
                _, isChecked ->
            val message = if (isChecked) {
                "已取消收藏"
            } else {
                "加入收藏！❤"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        trytwo.setOnCheckedChangeListener{
                _, isChecked ->
            val message = if (isChecked) {
                "已取消收藏"
            } else {
                "加入收藏！❤"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        trythree.setOnCheckedChangeListener{
                _, isChecked ->
            val message = if (isChecked) {
                "已取消收藏"
            } else {
                "加入收藏！❤"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}