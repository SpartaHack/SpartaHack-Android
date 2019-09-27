package com.spartahack.spartahack_android.scripts

import android.util.Log
import com.spartahack.spartahack_android.tools.*

fun getQuestion(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the question. */

    var returnStr = ""

    val splitStr = str.split("\",")

    for(i in splitStr){
        val header = i.split(":")
        if (header[0] == "\"question\""){
            returnStr =  header[1]
        }
    }

    Log.i("getQuestion", "question:" + returnStr)

    return returnStr
} // getQuestion.


fun getAnswer(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the answer. */

    var returnStr = ""

    val splitStr = str.split("\",")

    for(i in splitStr){
        val header = i.split(":")
        if (header[0] == "\"answer\""){
            returnStr =  header[1]
        }
    }

    Log.i("getAnswer", "answer:" + returnStr)

    return returnStr
} // getAnswer.


fun faqMain(): String {
    /** The main structure of the script. Uses getQuestion and getAnswer. */

    // Makes a call to the api to get the FAQ information as a raw string and splits the raw FAQ
    // string into a list.
    val faqRawStr = APICall("faqs").sendGet()
    val faqList = faqRawStr.split("},")

    var displayStr = faqRawStr

    // Takes every entry in the FAQ list, then formats it in such a way that it is easy to display.
    for (i in faqList) {
        val question = getQuestion(i)
        val answer = getAnswer(i)

        displayStr += (question + "\n" + answer + "\n")
    }

    Log.i("FAQMain","faqMain. Hopefully something happened.")
    return displayStr

} // faqMain.