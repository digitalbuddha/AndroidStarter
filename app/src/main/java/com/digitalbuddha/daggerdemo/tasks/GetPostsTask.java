package com.digitalbuddha.daggerdemo.tasks;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.utils.ErrorEvent;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;

import java.util.Map;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.TaskQueue;

/**
 * Created by MikeN on 8/3/14.
 */
public class GetPostsTask extends TaskQueue.Task implements Response.ErrorListener, Response.Listener<Map> {

    public RequestQueue requestQueue;

    @Override
    public void run(Context context) {
        String url = "http://www.reddit.com/r/aww/new.json?limit=5";

        Response.ErrorListener errorListener = this;
        Response.Listener<Map> listener = this;
        Class<Map> mapClass = Map.class;
        GsonRequest request = new RedditRequest(url, Map.class,this,this).invoke();
        new MakeRequest(request).invoke();

        RequestFuture<Map> future= RequestFuture.newFuture();
        request = new RedditRequest(url, Map.class,future,future).invoke();
        new MakeRequest(request).invoke();
    }

    @Override
    protected boolean handleError(Exception e) {
        return false;
    }

    @Override
    public void onResponse(Map response) {
        EventBusExt.getDefault().post(this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //if hard error throw exception
//        RuntimeException runtimeException = new RuntimeException();
//        runtimeException.initCause(error);
//        throw  runtimeException;
        EventBusExt.getDefault().post(new ErrorEvent("error has occured"));
    }


}
