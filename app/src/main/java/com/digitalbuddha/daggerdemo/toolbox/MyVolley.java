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
package com.digitalbuddha.daggerdemo.toolbox;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MyVolley
{
    public static final String SHINDIG = "shindig";
    public static final int softTTLDefault = 1 ;//within 5 minutes pull from cache, no refresh necessary
    public static final int hardTTLDefault = 1;//greater than softTTL and less than 1 hour pull from cache,refresh cache in background for next request
    private static final int MAX_IMAGE_CACHE_ENTIRES = 100;
    private static final String TAG = "shindig.MyVolley";
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    private static String baseUrl = "https://freezing-dusk-2759.herokuapp.com";
    private static String token;
    public MyVolley()
    {
        // no instances
    }

    public static int getMaxImageCacheEntires()
    {
        return MAX_IMAGE_CACHE_ENTIRES;
    }

    public static String getToken()
    {
        return token;
    }

    public static void setToken(String token)
    {
        MyVolley.token = token;
    }
    public static void init(Context context)
    {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(MAX_IMAGE_CACHE_ENTIRES));
        mImageLoader.setBatchedResponseDelay(0);
    }
    public static RequestQueue getRequestQueue()
    {
        if (mRequestQueue != null)
        {
            return mRequestQueue;
        } else
        {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static void setRequestQueue(RequestQueue mRequestQueue)
    {
        MyVolley.mRequestQueue = mRequestQueue;
    }
    public static ImageLoader getImageLoader()
    {
        if (mImageLoader != null)
        {
            return mImageLoader;
        } else
        {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }


    public static void  makeNetworkArrayRequestWithListener(final String url, final Map headers, Response.Listener listener) throws UnsupportedEncodingException
    {
        Log.d(TAG, "Making Future Network Call");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        String URL;
        if (!url.contains("http"))
        {
            URL = getBaseUrl() + url;
        } else
        {
            URL = url;
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, listener, future)
        {
            protected boolean cacheHit = true;
            @Override
            public void addMarker(String tag)
            {
                super.addMarker(tag);
                if (tag.equals("network-http-complete"))
                {
                    cacheHit = false;
                }
            }
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response)
            {
                String jsonString = new String(response.data);
                try
                {
                    return Response.success(new JSONArray(jsonString), customCachePolicy(response, softTTLDefault, hardTTLDefault));
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        jsonArrayRequest.setTag(SHINDIG);
        jsonArrayRequest.setShouldCache(false);
        getRequestQueue().add(jsonArrayRequest);
        Log.d(TAG, "Done Queuing Network Call");
    }


    public static Cache.Entry customCachePolicy(NetworkResponse response, int softTTLDefault, int hardTTLDefault)
    {
        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;
        headerValue = headers.get("Date");
        if (headerValue != null)
        {
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
    public static String getBaseUrl()
    {
        return baseUrl;
    }
    public static void setBaseUrl(String baseUrl)
    {
        MyVolley.baseUrl = baseUrl;
    }


    public static JSONObject request(int method, final String url, JSONObject params, final int cacheSoftTTL, final int cacheHardTTL, final Map headers) throws Exception
    {
        Log.d(TAG, "Making Future Network Call");
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        String URL;
        if (!url.contains("http")) URL = getBaseUrl() + url;
        else URL = url;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(method, URL, params, future, future)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map h = headers == null ? new HashMap() : headers;
//                h.put("Content-Type", "application/json");
                return h;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
            {
                String jsonString = new String(response.data);
                try
                {
                    return Response.success(new JSONObject(jsonString), MyVolley.customCachePolicy(response, cacheSoftTTL, cacheHardTTL));
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
        if (!(method == Request.Method.GET))
        {
            jsObjRequest.setShouldCache(false);
        }
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        jsObjRequest.setTag(SHINDIG);
        jsObjRequest.setShouldCache(false);

        getRequestQueue().add(jsObjRequest);
        Log.d(TAG, "Done Queuing Network Call");
        JSONObject response;
        response = future.get();
        return response;
    }

    public static JSONArray requestArray(final String url, final int cacheSoftTTL, final int cacheHardTTL, final Map headers) throws Exception
    {
        Log.d(TAG, "Making Future Network Call");
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        String URL;
        if (!url.contains("http")) URL = getBaseUrl() + url;
        else URL = url;
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(URL,future, future)
        {   boolean cacheHit=false;
            @Override
            public void addMarker(String tag)
            {
                super.addMarker(tag);
                if (tag.equals("network-http-complete"))
                {
                    cacheHit = false;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map h = headers == null ? new HashMap() : headers;
             //   h.put("Content-Type", "application/json");
                return h;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response)
            {
                String jsonString = new String(response.data);
                try
                {
                    return Response.success(new JSONArray(jsonString), MyVolley.customCachePolicy(response, cacheSoftTTL, cacheHardTTL));
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        jsObjRequest.setTag(SHINDIG);
        jsObjRequest.setShouldCache(false);

        getRequestQueue().add(jsObjRequest);
        Log.d(TAG, "Done Queuing Network Call");
        JSONArray response;

        response = future.get();

        Log.d(TAG, "Request Complete to url:"+URL);

        return response;
    }

    public static String encodeURL(String urlString) throws MalformedURLException, URISyntaxException
    {
        URL url = new URL(urlString);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        url = uri.toURL();
        return String.valueOf(url);
    }

}
