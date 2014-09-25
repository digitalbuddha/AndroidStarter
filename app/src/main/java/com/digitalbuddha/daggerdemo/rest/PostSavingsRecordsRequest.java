package com.digitalbuddha.daggerdemo.rest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;
import com.digitalbuddha.daggerdemo.utils.MyVolley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MikeN on 9/24/14.
 */
//PostRecord
public class PostSavingsRecordsRequest {
    private RequestFuture future;
    private final Response.Listener success;
    private final Response.ErrorListener error;
    private int userId;
    private int typeId;

    public PostSavingsRecordsRequest(Response.Listener success, Response.ErrorListener error, int userId, int typeId) {
        this.success = success;
        this.error = error;
        this.userId = userId;
        this.typeId = typeId;
    }

   //PostRecordsRequestInvoke
    public void invoke() {
        Map params = new HashMap<String, String>();
        params.put("typeId", String.valueOf(typeId));
        GsonRequest request = new GsonRequest<List>(Request.Method.POST, "url", List.class, params, success, error);
        request.setRetryPolicy(new DefaultRetryPolicy(15000, 3, 1));
        MyVolley.getRequestQueue().add(request);
        request.setShouldCache(false);
        //TODO: invalidate cache for getSavingsRecordRequest;
        MyVolley.getRequestQueue().getCache().remove("url" + userId);
    }
}
