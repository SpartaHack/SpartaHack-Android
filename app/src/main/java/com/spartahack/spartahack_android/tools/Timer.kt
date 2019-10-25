package com.spartahack.spartahack_android.tools

import android.os.CountDownTimer
import android.widget.TextView

class Timer(endTimeMills:Long, interval:Long, val timerView:TextView):CountDownTimer(endTimeMills, interval) {
    override fun onTick(millsUntilFinished: Long) {
        val months = millsUntilFinished / 2628000000
        val days = millsUntilFinished % 2628000000 / 86400000
        val hours = millsUntilFinished % 2628000000 % 86400000 / 3600000
        val minutes = millsUntilFinished % 2628000000 % 86400000 % 3600000 / 60000
        val seconds = millsUntilFinished % 2628000000 % 86400000 % 3600000 % 60000 / 1000

        timerView.text = months.toString() + ":" + days.toString() + ":" + hours.toString() + ":" + minutes.toString() + ":" + seconds.toString()
    }

    override fun onFinish(){
        timerView.text = ""
    }
}