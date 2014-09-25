package com.digitalbuddha.gdg.rest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.digitalbuddha.gdg.model.SavingsType;
import com.digitalbuddha.gdg.utils.GsonRequest;
import com.digitalbuddha.gdg.utils.MyVolley;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by MikeN on 9/24/14.
 */
//TypeRequest
public class GetSavingsTypesRequest {
    private final Response.Listener success;
    private final Response.ErrorListener error;
    private String url="https://raw.githubusercontent.com/digitalbuddha/AndroidStarter/master/types.json";

    public GetSavingsTypesRequest(Response.Listener success, Response.ErrorListener error) {
        this.success = success;
        this.error = error;
    }
    //TypeInvoke
    public void invoke() {
        GsonRequest request = new GsonRequest<List>(Request.Method.GET, url,
                new TypeToken<List<SavingsType>>(){}.getType(),
                success,
                error);
        request.setHardTTLDefault(24*60*60*1000); //pull from cache and then refresh
        request.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1));
        MyVolley.getRequestQueue().add(request);
    }
}
