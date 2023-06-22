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
                .setTitle("게임 설명")
                .setMessage("\n20초 뒤부터 가게에 손님들이 찾아옵니다! 붕어빵을 빠르게 구워 일확천금을 노려보세요!\n\n- 붕어빵 굽는 방법\n: 붕어빵 틀을 클릭하여 붕어빵을 구워주세요!\n" +
                        "1. 반죽을 올립니다\n" +
                        "2. 팥을 넣습니다\n" +
                        "3. 반죽을 뒤집고 다시 구워줍니다\n" +
                        "4. 노릇하게 구워졌으면 붕어빵이 타기 전에 빼주세요\n\n- 판매 방법\n: 주문 말풍선을 클릭하여 붕어빵을 판매하세요!\n1. 주문한만큼 붕어빵을 준비합니다\n2. 손님이 떠나기 전에 붕어빵을 판매합니다\n\n*붕어빵은 개당 500원에 판매됩니다.\n최대한 많이 판매하여 신기록을 달성해보세요!\n")
                .setPositiveButton("Start",
                    { dialog, id ->
                        startActivity(Intent(this, GameActivity::class.java))
                        finish()
                    })
            builder.create()
            builder.show()
        }
    }
}