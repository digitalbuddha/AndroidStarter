package com.digitalbuddha.daggerdemo.toolbox;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Nakhimovich on 7/28/14.
 */
public class ObjectVolley extends MyVolley
{
    public static JSONObject request(int method, String url, JSONObject params) throws Exception
    {
        return request(method, url, params, 1, 1, null);
    }

    public static JSONObject requestWithCache(int method, String url, JSONObject params, int softTTL, int hardTTL) throws Exception
    {
        return request(method, url, new JSONObject(), softTTL, hardTTL, null);
    }

    public static JSONObject requestWithCacheAndHeaders(int method, String url, JSONObject params, int softTTL, int hardTTL, Map headers) throws Exception
    {
        return request(method, url, new JSONObject(), softTTL, hardTTL, headers);
    }

    public static JSONObject getWithCacheAndHeaders(String url, int softTTL, int hardTTL, Map headers) throws Exception
    {
        return requestWithCacheAndHeaders(Request.Method.GET, url, new JSONObject(), softTTL, hardTTL, headers);
    }

    public static JSONObject getWithCache(String url, int softTTL, int hardTTL) throws Exception
    {
        return requestWithCache(Request.Method.GET, url, new JSONObject(), softTTL, hardTTL);
    }

    public static JSONObject get(String url) throws Exception
    {
        return request(Request.Method.GET, url, null);
    }

    public static JSONObject postWithCacheAndHeaders(String url, JSONObject params, int softTTL, int hardTTL, Map headers) throws Exception
    {
        return requestWithCacheAndHeaders(Request.Method.POST, url, params, softTTL, hardTTL, headers);
    }

    public static JSONObject postWithCache(String url, JSONObject params, int softTTL, int hardTTL) throws Exception
    {
        return requestWithCache(Request.Method.POST, url, params, softTTL, hardTTL);
    }

    public static JSONObject post(String url, JSONObject params) throws Exception
    {
        return request(Request.Method.POST, url, params);
    }
}
