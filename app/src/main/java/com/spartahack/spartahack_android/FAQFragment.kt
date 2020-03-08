package com.spartahack.spartahack_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.spartahack.spartahack_android.scripts.faqMainSuspend
import kotlinx.android.synthetic.main.faq_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class FAQFragment(private val fragment_id:Int) : Fragment(fragment_id) {
    private lateinit var faqDisplay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // Makes the API call and sends the data to the activity.
        val displayString = GlobalScope.async { faqMainSuspend() }

        // Store the string so that the API call will not have to be made again.
        runBlocking { faqDisplay = displayString.await() }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(fragment_id, container, false)
    }

    override fun onStart() {
        faqTextView.text = HtmlCompat.fromHtml(faqDisplay, 0)
        super.onStart()
    }

    override fun onResume() {
        faqTextView.text = HtmlCompat.fromHtml(faqDisplay, 0)
        super.onResume()
    }

}