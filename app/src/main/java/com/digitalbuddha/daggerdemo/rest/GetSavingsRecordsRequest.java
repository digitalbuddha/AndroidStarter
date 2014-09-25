package com.digitalbuddha.daggerdemo.rest;

import com.android.volley.Response;

import java.util.List;

/**
 * Created by MikeN on 9/24/14.
 */
//RecordsRequest
public class GetSavingsRecordsRequest {
    private final Response.Listener success;
    private final Response.ErrorListener error;
    public List savingsTypes;

    public GetSavingsRecordsRequest(Response.Listener success, Response.ErrorListener error) {
        this.success = success;
        this.error = error;
    }

    //GetRecordsRequestInvoke
    public void invoke() {
//        GsonRequest request = new GsonRequest<List>(Request.Method.GET, url, List.class,success,error);
//        request.setSoftTTLDefault(24*60*60*1000); //pull from cache if not invalidated for 1 week
//        will not throw network error exception untl retry is complete
//        request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
//        MyVolley.getRequestQueue().add(request);
    }

}
