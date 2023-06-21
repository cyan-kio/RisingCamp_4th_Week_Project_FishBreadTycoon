package com.cookandroid.bungabbangtycoonproject.rank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.cookandroid.bungabbangtycoonproject.R
import com.cookandroid.bungabbangtycoonproject.databinding.ActivityRankBinding
import com.cookandroid.bungabbangtycoonproject.makeStatusBarTransparent
import com.cookandroid.bungabbangtycoonproject.rank.adapter.RankAdapter
import com.cookandroid.bungabbangtycoonproject.rank.room.RankDatabase

class RankActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.raw.gif_background_city_night).into(binding.imgRankBackground)

        val adapter = RankAdapter()

        Thread {
            val db = Room.databaseBuilder(
                applicationContext,
                RankDatabase::class.java,
                "rank"
            ).build()
            adapter.recordList = db.rankDao().getRevenueDesc()
            Log.d("THREADCHECK","현재 스레드 : ${Thread.currentThread()}")
        }.start()

        binding.recyclerRank.adapter = adapter
        binding.recyclerRank.layoutManager = LinearLayoutManager(this)
    }
}