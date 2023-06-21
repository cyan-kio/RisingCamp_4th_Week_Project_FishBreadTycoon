package com.cookandroid.bungabbangtycoonproject.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.cookandroid.bungabbangtycoonproject.R
import com.cookandroid.bungabbangtycoonproject.databinding.ActivityMainBinding
import com.cookandroid.bungabbangtycoonproject.game.GameActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.raw.gif_background_city_night).into(binding.imgMainBackground)

    }

    override fun onResume() {
        super.onResume()
        binding.btnStartGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        binding.btnHowToPlay.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Title")
                .setMessage("ThisistheMessage")
                .setPositiveButton("Start",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Start 버튼 선택시 수행
                    })
            builder.create()
            builder.show()
        }
    }
}