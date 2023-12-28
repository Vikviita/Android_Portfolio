package com.vikvita.tictactoe

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.vikvita.tictactoe.databinding.ActivityMain2Binding
import java.util.Timer
import java.util.TimerTask


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var check: Int = 0
    private lateinit var ch1: ticTac
    private var seconds = 0
    private var minutes = 0
    private var player = 0
    private var end = 0
    private var checkerWin = 0
    private var array = mapOf(
        "col1" to Array<ticTac>(3) { ticTac.EMPTY },
        "col2" to Array<ticTac>(3) { ticTac.EMPTY },
        "col3" to Array<ticTac>(3) { ticTac.EMPTY },
        "row1" to Array<ticTac>(3) { ticTac.EMPTY },
        "row2" to Array<ticTac>(3) { ticTac.EMPTY },
        "row3" to Array<ticTac>(3) { ticTac.EMPTY },
        "dig1" to Array<ticTac>(3) { ticTac.EMPTY },
        "dig2" to Array<ticTac>(3) { ticTac.EMPTY }

    )
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = resources.getColor(R.color.backe)
        MobileAds.initialize(this)
        binding.adView.loadAd(AdRequest.Builder().build())

        binding.apply {
            but11.setOnClickListener {
                if (array["col1"]?.get(0) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col1"]?.set(0, ch1)
                    array["row1"]?.set(0, ch1)
                    array["dig1"]?.set(0, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col1"]?.get(0)!!.name)

                Log.d("Mylog", array["row1"]?.get(0)!!.name)

                Log.d("Mylog", array["dig1"]?.get(0)!!.name)
            }
            but12.setOnClickListener {
                if (array["col2"]?.get(0) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col2"]?.set(0, ch1)
                    array["row1"]?.set(1, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col2"]?.get(0)!!.name)

                Log.d("Mylog", array["row1"]?.get(1)!!.name)


            }
            but13.setOnClickListener {
                if (array["col3"]?.get(0) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col3"]?.set(0, ch1)
                    array["row1"]?.set(2, ch1)
                    array["dig2"]?.set(2, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col3"]?.get(0)!!.name)
                Log.d("Mylog", array["row1"]?.get(2)!!.name)
                Log.d("Mylog", array["dig2"]?.get(2)!!.name)
            }
            but21.setOnClickListener {
                if (array["col1"]?.get(1) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col1"]?.set(1, ch1)
                    array["row2"]?.set(0, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col1"]?.get(1)!!.name)

                Log.d("Mylog", array["row2"]?.get(0)!!.name)

            }
            but22.setOnClickListener {
                if (array["col2"]?.get(1) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col2"]?.set(1, ch1)
                    array["row2"]?.set(1, ch1)
                    array["dig1"]?.set(1, ch1)
                    array["dig2"]?.set(1, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col2"]?.get(1)!!.name)
                Log.d("Mylog", array["row2"]?.get(1)!!.name)
                Log.d("Mylog", array["dig1"]?.get(1)!!.name)
                Log.d("Mylog", array["dig2"]?.get(1)!!.name)
            }

            but23.setOnClickListener {
                if (array["col3"]?.get(1) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col3"]?.set(1, ch1)
                    array["row2"]?.set(2, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col3"]?.get(1)!!.name)

                Log.d("Mylog", array["row2"]?.get(2)!!.name)


            }
            but31.setOnClickListener {
                if (array["col1"]?.get(2) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col1"]?.set(2, ch1)
                    array["row3"]?.set(0, ch1)
                    array["dig2"]?.set(0, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col1"]?.get(2)!!.name)

                Log.d("Mylog", array["row3"]?.get(0)!!.name)

                Log.d("Mylog", array["dig2"]?.get(0)!!.name)
            }
            but32.setOnClickListener {
                if (array["col2"]?.get(2) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col2"]?.set(2, ch1)
                    array["row3"]?.set(1, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col2"]?.get(2)!!.name)


                Log.d("Mylog", array["row3"]?.get(1)!!.name)


            }
            but33.setOnClickListener {
                if (array["col3"]?.get(2) == ticTac.EMPTY) {
                    start(it as ImageButton)
                    array["col3"]?.set(2, ch1)
                    array["row3"]?.set(2, ch1)
                    array["dig1"]?.set(2, ch1)
                }
                checkwin()
                Log.d("Mylog", array["col3"]?.get(2)!!.name)
                Log.d("Mylog", array["row3"]?.get(2)!!.name)

                Log.d("Mylog", array["dig1"]?.get(2)!!.name)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    fun start(view: ImageButton) {
        var ch = intent.getSerializableExtra("type") as ticTac
        if (check == 0) {
            view.setImageResource(ch.image)
            check++
            binding.textView3.text = resources.getString(R.string.player2)
            ch1 = if (ch == ticTac.TIC) ticTac.TAC else ticTac.TIC

            timer.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        seconds++
                        if (seconds == 60) {
                            seconds = 0
                            minutes++
                        }
                        if (seconds < 10) binding.timer.text = "$minutes:0$seconds "
                        else binding.timer.text = "$minutes:$seconds"
                        Log.d("Mylog", "1")
                    }
                }

            }, 0, 1000)

            return
        } else {
            if (player == 1) {
                binding.textView3.text = resources.getString(R.string.player2)
                player = 0
            } else {
                binding.textView3.text = resources.getString(R.string.player1)
                player = 1
            }
            when (ch1) {
                ticTac.TIC -> {
                    view.setImageResource(ch1.image)
                    ch1 = ticTac.TAC
                }

                ticTac.TAC -> {
                    view.setImageResource(ch1.image)
                    ch1 = ticTac.TIC
                }

                ticTac.EMPTY -> {}

            }
        }

    }

    fun win(msg: String) {
        timer.cancel()
        val intent = Intent(this, WinActivity::class.java).putExtra("player", msg)
        startActivity(intent)
        finish()
        checkerWin++


    }

    fun checkwin() {
        var TAC = 0
        var TIC = 0
        end++
        for (i in array["col1"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            } else if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {
                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }
        }
        TIC = 0
        TAC = 0
        for (i in array["col2"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {
                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }

        }
        TIC = 0
        TAC = 0
        for (i in array["col3"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {

                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }

        }
        TIC = 0
        TAC = 0
        for (i in array["row1"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {
                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }

        }
        TIC = 0
        TAC = 0
        for (i in array["row2"]!!) {

            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {

                    win("PLAYER 2 WON")
                } else {

                    win("PLAYER 1 WON")
                }
            }
        }
        TIC = 0
        TAC = 0
        for (i in array["row3"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {

                    win("PLAYER 2 WON")
                } else {

                    win("PLAYER 1 WON")
                }
            }

        }
        TIC = 0
        TAC = 0
        for (i in array["dig1"]!!) {
            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {
                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }

        }
        TIC = 0
        TAC = 0
        for (i in array["dig2"]!!) {

            if (i == ticTac.TIC) {
                TAC++
            }
            if (i == ticTac.TAC) {
                TIC++
            }
            if ((TAC == 3) or (TIC == 3)) {
                if (player == 1) {
                    win("PLAYER 2 WON")
                } else {
                    win("PLAYER 1 WON")
                }
            }
        }
        if ((end == 9) and (checkerWin != 1)) {
            val intent = Intent(this, MainActivity2::class.java).putExtra(
                "type",
                intent.getSerializableExtra("type")
            )
            startActivity(intent)
            finish()
        }
    }
}


