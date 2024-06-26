package com.cookandroid.bungabbangtycoonproject.game

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.room.Room
import com.bumptech.glide.Glide
import com.cookandroid.bungabbangtycoonproject.R
import com.cookandroid.bungabbangtycoonproject.databinding.ActivityGameBinding
import com.cookandroid.bungabbangtycoonproject.rank.room.Rank
import com.cookandroid.bungabbangtycoonproject.rank.RankActivity
import com.cookandroid.bungabbangtycoonproject.rank.room.RankDatabase
import java.text.DecimalFormat

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var count_redbean = 0       // 현재 구워진 붕어빵 수
    var orderState = 0        // 주문 상태
    var orderBaked = 0          // 주문 붕어빵 수
    var revenue = 0
    val range = (1..10)
    val customerRange = (0..5)
    var customer = 0
    val stateList = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val randomCustomer = listOf(R.drawable.img_boy_one, R.drawable.img_boy_two, R.drawable.img_boy_three, R.drawable.img_girl_one, R.drawable.img_girl_two, R.drawable.img_girl_three)
    private lateinit var frames: Array<AppCompatImageView>
    private lateinit var handler: Handler

    val orderTimerThread = OrderTimerThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("ORDERSTATE", orderState.toString())
        Glide.with(this).load(R.raw.gif_background_city_sunset).into(binding.imgGameBackground)

        handler = Handler(Looper.getMainLooper())
        val gameTimerThread = GameTimerThread()
        gameTimerThread.start()

        frames = arrayOf(
            binding.imgviewFrameDoughOne,
            binding.imgviewFrameDoughTwo,
            binding.imgviewFrameDoughThree,
            binding.imgviewFrameDoughFour,
            binding.imgviewFrameDoughFive,
            binding.imgviewFrameDoughSix,
            binding.imgviewFrameDoughSeven,
            binding.imgviewFrameDoughEight,
            binding.imgviewFrameDoughNine,
            binding.imgviewFrameDoughTen,
            binding.imgviewFrameDoughEleven,
            binding.imgviewFrameDoughTwelve
        )
    }

    override fun onResume() {
        super.onResume()
        val orderTimerThread = OrderTimerThread()

        frames.forEachIndexed { index, frame ->
            val well = WellTimerThread(frame, index)
            val rare = RareTimerThread(frame, index, well)
            frame.setOnClickListener {
                when(stateList[index]) {
                    0 -> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough,
                                theme
                            )
                        )
                        stateList[index]++
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                    }
                    1 -> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough_redbean,
                                theme
                            )
                        )
                        stateList[index]++
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                    }
                    2-> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough_flip,
                                theme
                            )
                        )
                        stateList[index]++
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                        rare.start()
                    }
                    3 -> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough_frame,
                                theme
                            )
                        )
                        stateList[index] = 0
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                        rare.cancel()
                    }
                    4 -> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough_frame,
                                theme
                            )
                        )
                        count_redbean++
                        binding.tvCountBaked.text = "구워진 붕어빵: " + count_redbean + "개"
                        stateList[index] = 0
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                        well.cancel()
                    }
                    5 -> {
                        frame.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.img_dough_frame,
                                theme
                            )
                        )
                        stateList[index] = 0
                        Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
                    }
                }

            }

        }

        binding.tvOrder.setOnClickListener {
            if(orderState == 3 && orderBaked <= count_redbean) {
                orderState = 2
                hideOrder()
                orderTimerThread.cancel()
                count_redbean -= orderBaked
                binding.tvCountBaked.text = "구워진 붕어빵: " + count_redbean + "개"
                revenue += orderBaked * 500
                binding.tvRevenue.text = DecimalFormat("#,###").format(revenue) + "원"
            }
        }
    }

    inner class GameTimerThread : CountDownTimer(120000, 1000) {
        private var min = 0
        private var sec = 0
        override fun onTick(millisUntilFinished: Long) {
            min = millisUntilFinished.toInt() / 60000
            sec = millisUntilFinished.toInt() / 1000 % 60
            val timer = getString(R.string.string_timer, min, sec)
            binding.tvGameTimer.text = timer
            Log.d("ORDERSTATE_MILLIS", millisUntilFinished.toInt().toString())

            if(millisUntilFinished.toInt()/1000 == 100) {
                orderState = 1
                Log.d("ORDERSTATE", orderState.toString())
            }
            when(orderState) {
                1 -> {
                    orderState = 3
                    order()
                    orderTimerThread.start()
                }
                2 -> {
                    orderState = 0
                    OrderBreakThread().start()
                }
            }
        }

        override fun onFinish() {
            val inputName = EditText(this@GameActivity)
            inputName.gravity = Gravity.CENTER
            var userName: String
            val builder = AlertDialog.Builder(this@GameActivity)
            builder.setTitle("게임 종료")
            builder.setMessage("\n닉네임을 입력해주세요\n")
            builder.setView(inputName)
            builder.setPositiveButton("확인",
                { dialog, which ->
                    if(inputName.text == null || inputName.text.toString().trim().isEmpty()) {
                        userName = "NON"
                    } else {
                        userName = inputName.text.toString()
                    }
                    val newRecord = Rank(userName, revenue)
                    Thread {
                        val db = Room.databaseBuilder(
                            applicationContext,
                            RankDatabase::class.java,
                            "rank"
                        ).build()
                        db.rankDao().insert(newRecord)
                        Log.d("THREADCHECK","현재 스레드 : ${Thread.currentThread()}")
                    }.start()

                    startActivity(Intent(this@GameActivity, RankActivity::class.java))
                    finish()
                })
            .setCancelable(false)
            builder.create()
            builder.show()
        }
    }



    fun order() {
        orderBaked = range.random()
        customer = customerRange.random()
        binding.tvOrder.text = "붕어빵 $orderBaked" + "개 주세요."
        Glide.with(this@GameActivity).load(randomCustomer[customer]).into(binding.ivCustomer)
        binding.tvOrder.visibility = View.VISIBLE
        binding.ivCustomer.visibility = View.VISIBLE
    }

    fun hideOrder() {
        binding.tvOrder.visibility = View.GONE
        binding.ivCustomer.visibility = View.GONE
    }

    inner class OrderTimerThread : CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            orderState = 2
            hideOrder()
        }
    }

    inner class OrderBreakThread : CountDownTimer(7000, 1000) {

        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            orderState = 1
        }
    }

    inner class RareTimerThread(val view: AppCompatImageView, val index: Int, val well: WellTimerThread) : CountDownTimer(3000, 3000) {
        override fun onTick(millisUntilFinished: Long) {

        }
        override fun onFinish() {
            view.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.img_dough_well,
                    theme
                )
            )
            stateList[index] = 4
            Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
            well.start()
        }
    }

    inner class WellTimerThread(val view: AppCompatImageView, val index: Int) : CountDownTimer(3000, 3000) {
        override fun onTick(millisUntilFinished: Long) {

        }
        override fun onFinish() {
            view.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.img_dough_burn,
                    theme
                )
            )
            stateList[index] = 5
            Log.d("STATETEST", "stateList [$index] : " + stateList[index].toString())
        }
    }
}