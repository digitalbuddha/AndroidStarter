package com.digitalbuddha.gdg.rest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.digitalbuddha.gdg.utils.GsonRequest;
import com.digitalbuddha.gdg.utils.MyVolley;

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
        GsonRequest request = new GsonRequest<List>(Request.Method.GET, "", List.class,success,error);
        request.setSoftTTLDefault(24*60*60*1000); //pull from cache if not invalidated for 1 week
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        MyVolley.getRequestQueue().add(request);
    }

}
