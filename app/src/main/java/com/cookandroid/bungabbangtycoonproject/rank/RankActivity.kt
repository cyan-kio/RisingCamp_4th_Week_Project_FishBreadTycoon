package com.cookandroid.bungabbangtycoonproject.rank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cookandroid.bungabbangtycoonproject.R
import com.cookandroid.bungabbangtycoonproject.databinding.ActivityRankBinding
import com.cookandroid.bungabbangtycoonproject.makeStatusBarTransparent

class RankActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.raw.gif_background_city_night).into(binding.imgRankBackground)

    }
}