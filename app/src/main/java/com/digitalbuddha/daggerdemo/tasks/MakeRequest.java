package com.digitalbuddha.daggerdemo.tasks;

import com.android.volley.RequestQueue;
import com.digitalbuddha.daggerdemo.utils.CustomVolley;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;

/**
* Created by MikeN on 9/16/14.
*/
class MakeRequest {
    private GsonRequest request;

    public MakeRequest(GsonRequest request) {
        this.request = request;

    }

    public void invoke() {
        request.setHardTTLDefault(1000 * 60 * 60);
        request.setSoftTTLDefault(1000 * 10);
        RequestQueue requestQueue = CustomVolley.getInstance().getRequestQueue();
        requestQueue.add(request);
    }
}
