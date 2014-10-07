package com.digitalbuddha.daggerdemo.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MikeN on 9/14/14.
 */
public class JsonObjectRequest extends JsonRequest<JSONObject> {

    private Map headers;
    private int softTTLDefault;
    private int hardTTLDefault;
    private boolean cacheHit = true;

    /**
     * Creates a new request.
     *  @param url           URL to fetch the JSON from
     * @param body
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsonObjectRequest(int method, String url, String body, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method,url, body.toString(),listener, errorListener);
    }

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
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        String jsonString = new String(response.data);
        try
        {
            return Response.success(new JSONObject(jsonString), customCachePolicy(response, softTTLDefault, hardTTLDefault));
        } catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        Map h = headers == null ? new HashMap() : headers;
//                h.put("Content-Type", "application/json");
        return h;
    }


    public void setHeaders(Map headers)
    {
        this.headers = headers;
    }

    public void setCacheTTL(int softTTLDefault, int hardTTLDefault)
    {

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

    public  Cache.Entry customCachePolicy(NetworkResponse response, int softTTLDefault, int hardTTLDefault)
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
}
