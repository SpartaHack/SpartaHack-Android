package com.spartahack.spartahack_android.scripts

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView

class FAQAsyncTask(val textView: TextView) : AsyncTask<Void, String, String>(){

    override fun doInBackground(vararg p0: Void?): String {
        Log.i("FAQAsync", "doInBackground")
        val dispText = ""//faqMain()
        publishProgress(dispText)
        return ""
    }

    override fun onProgressUpdate(vararg values: String?) {
        Log.i("FAQAsync", "onProgressUpdate")
        textView.text = values[0]
        super.onProgressUpdate(*values)
    }
}