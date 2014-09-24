package com.digitalbuddha.daggerdemo.utils;


import org.json.JSONArray;

import java.util.Map;

/**
 * Created by Nakhimovich on 7/28/14.
 */
public class ArrayVolley extends MyVolley
{


    public static JSONArray get(String url) throws Exception
    {
        return requestArray(url, 1, 1, null);
    }

    public static JSONArray getWithCacheAndHeaders(String url, int softTTL, int hardTTL, Map headers) throws Exception
    {
        return requestArray(url, softTTL, hardTTL, headers);
    }

    public static JSONArray getWithCache(String url, int softTTL, int hardTTL) throws Exception
    {
        return requestArray(url, softTTL, hardTTL, null);
    }

    public static JSONArray request(String url) throws Exception
    {
        return requestArray(url, 1, 1, null);
    }
}
