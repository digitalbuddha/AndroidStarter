package com.digitalbuddha.daggerdemo.job;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.digitalbuddha.daggerdemo.dagger.ForActivity;
import com.digitalbuddha.daggerdemo.utils.CustomVolley;
import com.digitalbuddha.daggerdemo.utils.GsonRequest;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by MikeN on 9/8/14.
 */
public class GetRedditJob extends Job {
    @Inject
    @ForActivity
    Context context;
    @Inject
    CustomVolley volley;

    public Map map;

    public GetRedditJob() {
        super(new Params(2)
                .requireNetwork()
                .groupBy("Reddit"));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        String url = "http://www.reddit.com/r/aww.json?limit=5";
        RequestFuture<Map> future = RequestFuture.newFuture();

        GsonRequest request = new GsonRequest<Map>(Request.Method.GET, url, Map.class, future, future);
        volley.getRequestQueue().add(request);
        map = future.get();
        EventBus.getDefault().post(this);
    }

    //
    @Override
    protected void onCancel() {
        throw new RuntimeException();
    }

    //don't retry on error
    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }


}
