package com.digitalbuddha.daggerdemo.tasks;

import com.android.volley.Request;
import com.android.volley.Response;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;

/**
* Created by MikeN on 9/16/14.
*/
class RedditRequest {
    private String url;
    private Class clazz;
    private final Response.Listener listener;
    private final Response.ErrorListener errorListener;

    public RedditRequest(String url, Class clazz, Response.Listener listener, Response.ErrorListener errorListener) {
        this.url = url;
        this.clazz = clazz;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public GsonRequest invoke() {
        return new GsonRequest(Request.Method.GET, url, clazz, listener, errorListener);
    }
}
