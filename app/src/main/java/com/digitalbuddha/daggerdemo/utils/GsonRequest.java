package com.digitalbuddha.daggerdemo.utils;

/**
 * Created by MikeN on 9/14/14.
 */
/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
import java.util.Map;


/**
 * Volley adapter for JSON requests with POST method that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
    private Gson mGson = new Gson();
    protected Class<T> clazz;
    private Map<String, String> headers;
    private Map<String, String> params;
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
     * @param clazz Relevant class object, for Gson's reflection
     */
    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
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
                       Class<T> clazz,
                       Map<String, String> params,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        this.clazz = clazz;
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
            return Response.success(
                    mGson.fromJson(json, clazz), customCachePolicy(response,softTTLDefault,hardTTLDefault));
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
}