package com.digitalbuddha.daggerdemo.utils;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Volley adapter for JSON requests with POST method that will be parsed into Java objects by Gson.
 * Added custom cache policy and set header public method
 */
//GSON Request
public class GsonRequest<T> extends Request<T> {
    private Gson mGson = new Gson();
    private Map<String, String> headers;
    private Map<String, String> params;
    private final Type type;
    private Response.Listener<T> listener;

    public int getSoftTTLDefault() {
        return softTTLDefault;
    }
    public void setSoftTTLDefault(int softTTLDefault) {
        this.softTTLDefault = softTTLDefault;
    }
    public int getHardTTLDefault() {
        return hardTTLDefault;
    }
    public void setHardTTLDefault(int hardTTLDefault) {
        this.hardTTLDefault = hardTTLDefault;
    }
    private int softTTLDefault=0;
    private int hardTTLDefault=0;
    private boolean cacheHit;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     */
    public GsonRequest(int method,
                       String url,
                       Type type,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        mGson = new Gson();

    }

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    public GsonRequest(int method,
                       String url,
                       Type type,
                       Map<String, String> params,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        this.type = type;
        this.params = params;
        this.listener = listener;
        this.headers = null;
        mGson = new Gson();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(
                    mGson.fromJson(json, type), customCachePolicy(response,softTTLDefault,hardTTLDefault));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    public Cache.Entry customCachePolicy(NetworkResponse response, int softTTLDefault, int hardTTLDefault) {
        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;
        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }
        serverEtag = headers.get("ETag");
        final long cacheHitButRefreshed = softTTLDefault;
        final long cacheExpired = hardTTLDefault;
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;
        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;
        return entry;
    }
    @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        if (tag.equals("network-http-complete")) {
            cacheHit = false;
        }
        Log.d("Volley",tag);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}