package com.spartahack.spartahack_android.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 *  AUTHOR: Morgan Sandler
 *  PURPOSE: Makes an API GET Request to api.elephant.spartahack.com/
 *  SYNTAX: String result = new APICall(<your_path_here>).sendGet();
 *  JAVA EXAMPLE: String test = new APICall("schedule").sendGet();
 *  KOTLIN EXAMPLE: var test = APICall("schedule").sendGet()
 * 
 */


public class APICall {


    private final String USER_AGENT = "Mozilla/5.0";
    private String path = "";


    public APICall(String path){
        this.path = path;
    }


    // HTTP GET request
    private String sendGet() throws Exception {

        String url = "http://api.elephant.spartahack.com/" + path;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }

    // unit test
    public static void main(String[] args) throws Exception {


        String information = new APICall("schedule").sendGet();
        System.out.println(information);

    }
}