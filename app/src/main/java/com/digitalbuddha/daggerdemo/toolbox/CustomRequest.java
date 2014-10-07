package com.digitalbuddha.daggerdemo.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MikeN on 9/14/14.
 */
public abstract class CustomRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Response.Listener<T> listener;
    private Map<String, String> params = null;
    private Map headers;
    private int softTTLDefault;
    private int hardTTLDefault;
    private boolean cacheHit = true;

    public CustomRequest(String url, Map<String, String> headers,
                         Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.headers = headers;
        this.listener = listener;
    }


    public CustomRequest(int method,
                         String url,
                         Map<String, String> params,
                         Response.Listener<T> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.listener = listener;
        this.headers = null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


    @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        if (tag.equals("network-http-complete")) {
            cacheHit = false;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map h = headers == null ? new HashMap() : headers;
//                h.put("Content-Type", "application/json");
        return h;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
    }

    public void setCacheTTL(int softTTLDefault, int hardTTLDefault) {

        this.softTTLDefault = softTTLDefault;
        this.hardTTLDefault = hardTTLDefault;
    }

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

    public boolean isCacheHit() {
        return cacheHit;
    }

    public void setCacheHit(boolean cacheHit) {
        this.cacheHit = cacheHit;
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
}
