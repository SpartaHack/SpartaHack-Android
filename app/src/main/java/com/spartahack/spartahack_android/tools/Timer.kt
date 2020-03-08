package com.spartahack.spartahack_android.tools

import android.os.CountDownTimer
import android.provider.Settings.Global.getString
import android.widget.TextView
import com.spartahack.spartahack_android.R

/**
 * The Timer class converts a date in milliseconds to a string representation of the time until a
 * certain event.
 *
 * endTimeMills: [Long] the date (in milliseconds) at which the timer is to display 0.
 * interval: [Long] the time (in milliseconds) between clock ticks.
 * timerView: [TextView] the view in which to display the timer.
 */
class Timer(endTimeMills:Long, interval:Long, private val timerView:TextView):CountDownTimer(endTimeMills, interval) {
    override fun onTick(millsUntilFinished: Long) {
        var days = (millsUntilFinished % 2628000000 / 86400000).toString()
        var hours = (millsUntilFinished % 2628000000 % 86400000 / 3600000).toString()
        var minutes = (millsUntilFinished % 2628000000 % 86400000 % 3600000 / 60000).toString()
        var seconds = (millsUntilFinished % 2628000000 % 86400000 % 3600000 % 60000 / 1000).toString()

        // This block of if statements takes care of single-digit numbers by placing a 0 in front of
        // them. This ensures that the display is uniform.
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

        // Set the display to the correct time.
        timerView.text = days + ":" + hours + ":" + minutes + ":" + seconds
    }

    override fun onFinish(){
        // Set the display to the empty string when time is up.
        timerView.text = ""
    }
}