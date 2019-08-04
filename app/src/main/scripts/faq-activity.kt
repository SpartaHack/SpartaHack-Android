package drawable

fun getQuestion(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the question. */

    for((i, c) in str.withIndex()){
        if (str.substring(i, i+7) == "question"){
            return str.substring(i+12)
        }
    }
} // getQuestion.


fun getAnswer(str: String): String {
    /** Takes a string formatted as a JSON and extracts the string representing the question. */

    for((i, c) in str.withIndex()){
        if (str.substring(i, i+5) == "answer"){
            return str.substring(i+10)
        }
    }
} // getAnswer.


fun main(){
    val api_ref = "api.elephant.spartahack.com/faqs"  // Holds a string for the api call.

    val faq_raw_str = URL(api_ref).readText()  // Makes a call to the api to get the FAQ information as a raw string.

    var faq_list = faq_raw_str.split("},")  // Splits the raw FAQ string into a list.


    var disp_str = ""

    // Takes every entry in the FAQ list, then... (displays it or formats it in such a way that it is easy to display).
    for (i in faq_list) {
        var question = getQuestion(i)
        var answer = getAnswer(i)

        disp_str += question + "\n" + answer
    }
} // main.