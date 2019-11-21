package com.spartahack.spartahack_android.tools

import android.os.CountDownTimer
import android.widget.TextView

class Timer(endTimeMills:Long, interval:Long, val timerView:TextView):CountDownTimer(endTimeMills, interval) {
    override fun onTick(millsUntilFinished: Long) {
        var months = (millsUntilFinished / 2628000000).toString()
        var days = (millsUntilFinished % 2628000000 / 86400000).toString()
        var hours = (millsUntilFinished % 2628000000 % 86400000 / 3600000).toString()
        var minutes = (millsUntilFinished % 2628000000 % 86400000 % 3600000 / 60000).toString()
        var seconds = (millsUntilFinished % 2628000000 % 86400000 % 3600000 % 60000 / 1000).toString()

        if(months.length != 2){
            months = "0" + months
        }
        if(days.length != 2){
            days = "0" + days
        }
        if(hours.length != 2){
            hours = "0" + hours
        }
        if(minutes.length != 2){
            minutes = "0" + minutes
        }
        if(seconds.length != 2){
            seconds = "0" + seconds
        }

        timerView.text = months + ":" + days + ":" + hours + ":" + minutes + ":" + seconds
    }

    override fun onFinish(){
        timerView.text = ""
    }
}