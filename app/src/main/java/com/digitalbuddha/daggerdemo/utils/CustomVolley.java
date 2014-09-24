package com.digitalbuddha.daggerdemo.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.inject.Singleton;


@Singleton
public class CustomVolley {
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private String baseUrl = "https://freezing-dusk-2759.herokuapp.com";
    private String token;
    private static final String TAG = "CustomVolley";

    private static CustomVolley ourInstance = new CustomVolley();

    public void init(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
        int maxImageCacheEntries = 100;
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(maxImageCacheEntries));
        imageLoader.setBatchedResponseDelay(0);

    }
    public static CustomVolley getInstance() {
        return ourInstance;
    }




    private CustomVolley() {

    }

    public  RequestQueue getRequestQueue()
    {
        if (requestQueue != null)
        {
            return requestQueue;
        } else
        {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
    public  void setRequestQueue(RequestQueue requestQueue)
    {
        this.requestQueue = requestQueue;
    }

    public  ImageLoader getImageLoader()
    {
        if (imageLoader != null)
        {
            return imageLoader;
        } else
        {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    public  String getBaseUrl()
    {
        return baseUrl;
    }
    public  void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public  void ArrayRequestAsync(final String url, final Map headers, Response.Listener listener, Response.ErrorListener errorListener, final int softTTLDefault, final int hardTTLDefault) throws UnsupportedEncodingException
    {
        Log.d(TAG, "Making Future Volley Call");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, listener, errorListener)
        {

        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        jsonArrayRequest.setTag(TAG);
      //  jsonArrayRequest.setShouldCache(false);
        getRequestQueue().add(jsonArrayRequest);
        Log.d(TAG, "Done Queuing Network Call");
    }

}
