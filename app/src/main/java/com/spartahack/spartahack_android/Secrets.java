package com.spartahack.spartahack_android;

/**
 * Class which stores all the "Secrets" of this program
 *
 * Stores API keys, paths, secrets for apis, tokens, etc. all in one centralized location
 *
 * WARNING: Make sure this file is never pushed to the github repo. This is a localized file
 */

public class Secrets {

    // TODO: Change URL for production
    final static private String SPARTAHACK_API_URL = "http://api.elephant.spartahack.com/";


    public static String getSPARTAHACK_API_URL()
    {
        return SPARTAHACK_API_URL;
    }
}
