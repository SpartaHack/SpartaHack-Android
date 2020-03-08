package com.spartahack.spartahack_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spartahack.spartahack_android.tools.Timer
import kotlinx.android.synthetic.main.home_view.*
import java.lang.System.currentTimeMillis

/**
 * The home fragment is the default page for the app. It displays a timer which counts down to the
 * start of the event, and displays the schedule beneath it.
 *
 * fragment_id: [Int] must be the LAYOUT id (R.layout.home_view) for the XML file which contains the
 * layout for the home fragment.
 */
class HomeFragment(private val fragment_id:Int) : Fragment(fragment_id) {

    private lateinit var timer: Timer
    // 3/27/20 @ 6:00 PM, the start of the event.
    private val END_DATE_MILLS = 1585346400000

    // Inflates the view for the fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(fragment_id, container, false)
    }

    override fun onStart() {
        // Initialize the timer.
        val currentDate = currentTimeMillis()
        val endTimeMills = END_DATE_MILLS - currentDate
        val timer = Timer(endTimeMills, 1000, timer_text)
        timer.start()

        super.onStart()
    }

    override fun onResume() {
        // Initialize the timer.
        val currentDate = currentTimeMillis()
        val endTimeMills = END_DATE_MILLS - currentDate
        val timer = Timer(endTimeMills, 1000, timer_text)
        timer.start()

        super.onResume()
    }

}