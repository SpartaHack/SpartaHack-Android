package com.spartahack.spartahack_android.scripts

import kotlinx.coroutines.*
import com.spartahack.spartahack_android.tools.*

fun getQuestion(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the question. */

    var returnStr = ""

    val splitStr = str.split(",\"")

    for(i in splitStr){
        val header = i.split("\":")
        if (header[0] == "question"){
            returnStr =  header[1].removeSuffix("\"").removePrefix("\"")
        }
    }

    return returnStr
} // getQuestion.


fun getAnswer(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the answer. */

    var returnStr = ""

    val splitStr = str.split("{\"")

    for(i in splitStr){
        val header = i.split("\":")
        if (header[0] == "answer"){
            returnStr =  header[1].removeSuffix("\",\"display").removePrefix("\"")
        }
    }

    return returnStr
} // getAnswer.


suspend fun faqMain(): String = withContext(Dispatchers.Default){
    /** The main structure of the script. Uses getQuestion and getAnswer. */

    // Makes a call to the api to get the FAQ information as a raw string and splits the raw FAQ
    // string into a list.
    var faqRawStr = APICall("faqs").sendGet()
    faqRawStr = faqRawStr.removeRange(0, 1)
    val faqList = faqRawStr.split("},")

    var displayStr = ""

    // Takes every entry in the FAQ list, then formats it in such a way that it is easy to display.
    for (i in faqList) {
        val question = getQuestion(i)
        val answer = getAnswer(i)

        displayStr += ("<h2>" + question + "</h2>\n" + "<p>" + answer + "</p>\n")
    }

    return@withContext displayStr

} // faqMain.