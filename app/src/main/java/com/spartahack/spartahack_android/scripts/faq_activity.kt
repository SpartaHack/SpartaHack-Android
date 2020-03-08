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

    // Takes out the test strings in the FAQ response.
    if (returnStr == "What are exfdsact nufgddmbers?"){
        returnStr = "skip"
    }

    return returnStr
} // getAnswer.


suspend fun faqMainSuspend(): String = withContext(Dispatchers.Default){
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

        // Does not make an addition to the display string if the current answer is
        // "What are exfdsact nufgddmbers?"
        if (answer != "skip"){
            displayStr += ("<h2>" + question + "</h2>\n" + "<p>" + answer + "</p>\n")
        }
    }

    return@withContext displayStr

} // faqMainSuspend.

fun faqMain(): String{
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

        // Does not make an addition to the display string if the current answer is
        // "What are exfdsact nufgddmbers?"
        if (answer != "skip"){
            displayStr += ("<h2>" + question + "</h2>\n" + "<p>" + answer + "</p>\n")
        }
    }

    return displayStr
} // faqMain