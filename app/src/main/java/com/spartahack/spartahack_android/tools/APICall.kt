package com.spartahack.spartahack_android.tools

import android.os.AsyncTask

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 *
 * AUTHOR: Morgan Sandler
 * PURPOSE: Makes an API GET Request to api.elephant.spartahack.com/
 * SYNTAX: String result = new APICall(<your_path_here>).sendGet();
 * JAVA EXAMPLE: String test = new APICall("schedule").sendGet();
 * KOTLIN EXAMPLE: var test = APICall("schedule").sendGet()
 *
</your_path_here> */


class APICall(path: String) {


    private val USER_AGENT = "Mozilla/5.0"
    private val path = ""


    init {
        this.path = path
    }


    // HTTP GET request
    @Throws(Exception::class)
    fun sendGet(): String {

        val url = "http://api.elephant.spartahack.com/$path"

        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection

        con.requestMethod = "GET"

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT)

        val responseCode = con.responseCode
        println("\nSending 'GET' request to URL : $url")
        println("Response Code : $responseCode")

        val `in` = BufferedReader(
            InputStreamReader(con.inputStream)
        )
        var inputLine: String
        val response = StringBuffer()

        while ((inputLine = `in`.readLine()) != null) {
            response.append(inputLine)
        }
        `in`.close()

        //print result
        return response.toString()
    }

    companion object {

        // unit test
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {


            val information = APICall("schedule").sendGet()
            println(information)

        }
    }

    // Functionality added by Lukas Richters to incorporate AsyncTask dependencies.
}