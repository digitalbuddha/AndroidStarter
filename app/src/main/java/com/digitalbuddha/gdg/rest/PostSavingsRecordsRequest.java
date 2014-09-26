package com.digitalbuddha.gdg.rest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.digitalbuddha.gdg.model.SavingRecord;
import com.digitalbuddha.gdg.model.SavingsType;
import com.digitalbuddha.gdg.utils.GsonRequest;
import com.digitalbuddha.gdg.utils.MyVolley;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MikeN on 9/24/14.
 */
//PostRecord
public class PostSavingsRecordsRequest {
    private final Response.Listener success;
    private final Response.ErrorListener error;
    private int userId;
    private SavingRecord record;
    private String url = "BaseURL/savingsrecord"+userId; //fake url until I can get web service up

    public PostSavingsRecordsRequest(Response.Listener success, Response.ErrorListener error, int userId, SavingRecord record) {
        this.success = success;
        this.error = error;
        this.userId = userId;
        this.record = record;
    }

   //PostRecordsRequestInvoke
    public void invoke() {
        Map params = new HashMap<String, Object>();
        params.put("record", record);
        //invalidate cache for getSavingsRecordRequest;
        MyVolley.getRequestQueue().getCache().remove(GetSavingsRecordsRequest.RECORDS_URL);
        
        GsonRequest request = new GsonRequest<List<SavingsType>>(Request.Method.POST, url, new TypeToken<List<SavingsType>>(){}.getType(), params, success, error);
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 3, 1));
        request.setShouldCache(false);
        MyVolley.getRequestQueue().add(request);
    }
}
