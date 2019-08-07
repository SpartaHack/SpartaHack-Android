package com.spartahack.spartahack_android.scripts

import java.net.URL
import kotlinx.coroutines.*

fun getQuestion(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the question. */

    var returnStr = ""

    for((i, c) in str.withIndex()){
        if (str.substring(i, i+7) == "question"){
            returnStr =  str.substring(i+12)
        }
    }

    return returnStr
} // getQuestion.


fun getAnswer(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the answer. */

    var answerEnd = 0
    // Finds the index where the answer ends.
    for((i, c) in str.withIndex()){
        if (str.substring(i, i+1) == "\","){
            answerEnd = i
            break  // Breaks after the first ", is found, signaling the end of the answer.
        }
    }

    var returnStr = ""
    // Finds the answer in the return from the API call.
    for((i, c) in str.withIndex()){
        if (str.substring(i, i+5) == "answer"){
            returnStr = str.substring(i+10, answerEnd)
        }
    }

    return returnStr
} // getAnswer.


suspend fun faqMain(): String = withContext(Dispatchers.Default){
    /** The main structure of the script. Uses getQuestion and getAnswer. */

    val apiRef = "http://api.elephant.spartahack.com/faqs"  // Holds a string for the api call.

    // Makes a call to the api to get the FAQ information as a raw string and splits the raw FAQ string into a list.
    val faqRawStr = URL(apiRef).readText()
    val faqList = faqRawStr.split("},")

    var displayStr = ""

    // Takes every entry in the FAQ list, then formats it in such a way that it is easy to display.
    for (i in faqList) {
        val question = getQuestion(i)
        val answer = getAnswer(i)

        displayStr += (question + "\n" + answer + "\n")
    }

    return@withContext displayStr

} // faqMain.